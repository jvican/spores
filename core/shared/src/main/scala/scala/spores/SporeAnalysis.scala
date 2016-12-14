package scala.spores

import scala.reflect.macros.whitebox
import scala.spores.util.Feedback

protected class SporeAnalysis[C <: whitebox.Context with Singleton](val ctx: C) {
  implicit val c0 = ctx
  import ctx.universe.Flag._
  import ctx.universe._

  /** Strip the header and the body of a spore iff they are valid. */
  def stripSporeStructure(tree: Tree): (List[ValDef], Tree) = {
    def isCorrectHeader(valDef: ValDef) = !valDef.mods.hasFlag(MUTABLE)

    tree match {
      case Block(stmts, expr) =>
        (stmts flatMap {
          case vd: ValDef if isCorrectHeader(vd) => List(vd)
          case stmt => ctx.abort(stmt.pos, Feedback.IncorrectSporeHeader)
        }) -> expr
      case expr => (List.empty, expr)
    }
  }

  private val SporesDefinition = typeOf[scala.spores.`package`.type]
  private val delayedSym = SporesDefinition.member(TermName("delayed"))
  private val captureSym = SporesDefinition.member(TermName("capture"))

  /** Identify spores (nullary and non-nullary) and return their params and body. */
  def readSporeFunDef(tree: Tree): (Option[Function], List[Tree], Tree) = {
    tree match {
      case f @ Function(params, body) =>
        (Some(f), params, body) // Non-nullary
      case Apply(f, List(arg)) if f.symbol == delayedSym =>
        (None, List(), arg) // Nullary spore
      case _ => ctx.abort(tree.pos, Feedback.IncorrectSporeBody)
    }
  }

  /** Check predicate is satisfied for a concrete path. */
  private def isIdentifier(t: Tree)(pred: TermSymbol => Boolean): Boolean = {
    t match {
      case id: Ident => pred(id.symbol.asTerm)
      case _ =>
        debug(s"isPathWith fails with $t")
        false
    }
  }

  private class SymbolCollector extends Traverser {
    var capturedSymbols = List.empty[(Symbol, Tree)]
    override def traverse(tree: Tree): Unit = {
      tree match {
        case app @ Apply(fun, List(captured)) if fun.symbol == captureSym =>
          debug(s"Found capture: $app")
          val culprit = captured.toString
          if (!isIdentifier(captured)(_.isStable))
            ctx.abort(captured.pos, Feedback.InvalidOuterReference(culprit))
          else if (!isIdentifier(captured)(!_.isLazy))
            ctx.abort(captured.pos, Feedback.InvalidLazyMember(culprit))
          else capturedSymbols ::= captured.symbol -> captured

        case _ => super.traverse(tree)
      }
    }
  }

  def collectCaptured(sporeBody: Tree): List[(Symbol, Tree)] = {
    debug("Collecting captured symbols...")
    val collector = new SymbolCollector
    collector.traverse(sporeBody)
    collector.capturedSymbols
  }

  private class VariableDeclarationCollector extends Traverser {
    var declaredSymbols = List.empty[Symbol]
    override def traverse(tree: Tree): Unit = {
      tree match {
        case vd: ValDef =>
          super.traverse(tree)
          declaredSymbols ::= vd.symbol
        case _ => super.traverse(tree)
      }
    }
  }

  def collectDeclared(sporeBody: Tree): List[Symbol] = {
    debug("Collecting declared symbols...")
    val collector = new VariableDeclarationCollector
    collector.traverse(sporeBody)
    collector.declaredSymbols
  }

  private def isBottomType(btm: Type, tpe: Type) =
    btm =:= definitions.NothingTpe && !(tpe =:= btm)

  private val tuples = definitions.TupleClass.seq
  def isTuple(tp: Type): Boolean =
    tuples.exists(t => tp.typeSymbol.typeSignature =:= t.typeSignature)

  /** Check that expected excluded type holds for a concrete spore body.
    *
    * @param excluded Expected user-defined excluded type member.
    * @param captured Captured type to check for `Excluded` types.
    */
  def checkExcludedTypesInBody(excluded: Type, captured: Type) = {
    if (!(excluded =:= definitions.NothingTpe)) {
      val blacklist =
        if (isTuple(excluded)) excluded.typeArgs
        else List(excluded)
      debug(s"Excluded types are $blacklist")
      val usedTypes =
        if (isTuple(captured)) captured.typeArgs
        else List(captured)
      usedTypes.foreach { tpe =>
        blacklist.foreach { blacklisted =>
          if (tpe <:< blacklisted && !isBottomType(tpe, blacklisted)) {
            ctx.abort(ctx.enclosingPosition,
                      Feedback.InvalidReferenceToExcludedType(tpe.toString))
          }
        }
      }
    }
  }
}

/** Check that several spore properties hold in user-defined code.
  *
  * @param ctx Compiler context.
  * @param sporeEnvironment Symbols defined in header or `capture`d.
  * @param funSymbol Symbol of function to be converted to spore.
  * @param declaredSymbols Symbols declared in the spore body.
  */
protected class SporeChecker[C <: whitebox.Context with Singleton](val ctx: C)(
    val sporeEnvironment: List[C#Symbol],
    val funSymbol: Option[C#Symbol],
    var declaredSymbols: List[C#Symbol]) {
  implicit val c0 = ctx
  import ctx.universe._

  /** Check whether the owner chain of `sym` contains `owner`.
    *
    * @param sym   the symbol to be checked
    * @param owner the owner symbol that we try to find
    * @return      whether `owner` is a direct or indirect owner of `sym`
    */
  @inline private def isOwner(sym: Symbol, owner: Symbol): Boolean = {
    sym != null && (sym.owner == owner || {
      sym.owner != NoSymbol && isOwner(sym.owner, owner)
    })
  }

  @inline private def isSymbolChildOfSpore(childSym: Symbol) =
    funSymbol.exists(sym => isOwner(childSym, sym.asInstanceOf[Symbol]))

  /** Check that a symbol is an object or a package statically accessible
    * (note that scalac hoists up objects defined into functions).
    *
    * @param s Symbol of a given tree inside a spore.
    */
  @inline def isStaticPath(s: Symbol): Boolean = {
    // Disclaimer: Static does not work as the reflect docs say
    s != NoSymbol && {
      (s.isMethod && isStaticPath(s.owner)) || {
        (s.isModule || s.isModuleClass || s.isPackage || s.isPackageClass) &&
        (s.isStatic || isStaticPath(s.owner))
      }
    }
  }

  /** Check the validity of symbols. Spores allow refs to symbols if:
    *
    *   1. A symbol `s` is declared in the spore header or using `capture`.
    *   2. A symbol `s` is owned by a spore.
    *   3. A symbol `s` is declared inside the spore body.
    *   4. A symbol `s` is __static__.
    *
    * @param s Symbol of a given tree inside a spore.
    * @return Whether the symbol is valid or not.
    */
  @inline private def isSymbolValid(s: Symbol): Boolean = {
    sporeEnvironment.contains(s) ||
    isSymbolChildOfSpore(s) ||
    declaredSymbols.contains(s) ||
    isStaticPath(s)
  }

  /** Inspect a tree and check that all the trees in the body suit the
    * spores contract, that is, there are no invalid references to
    * non-captured symbols or external expressions like lazy vals.
    */
  private object ReferenceInspector extends Traverser {
    override def traverse(tree: Tree): Unit = {
      tree match {
        case New(_) =>
        case Ident(_) | This(_) | Super(_) =>
          debug(s"Checking ident | this | super: $tree")
          val sym = tree.symbol
          if (sym != NoSymbol && !isSymbolValid(tree.symbol))
            ctx.abort(tree.pos,
                      Feedback.InvalidReferenceTo(tree.symbol.toString))
        case _ => super.traverse(tree)
      }
    }
  }

  def checkReferencesInBody(sporeBody: Tree) =
    ReferenceInspector.traverse(sporeBody)
}
