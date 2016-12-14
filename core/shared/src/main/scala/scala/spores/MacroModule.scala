/*                     __                                               *\
**     ________ ___   / /  ___     Scala API                            **
**    / __/ __// _ | / /  / _ |    (c) 2002-2013, LAMP/EPFL             **
**  __\ \/ /__/ __ |/ /__/ __ |    http://scala-lang.org/               **
** /____/\___/_/ |_/____/_/ | |                                         **
**                          |/                                          **
\*                                                                      */

package scala.spores

import scala.reflect.macros.whitebox

private[spores] class MacroModule[C <: whitebox.Context](val c: C) {
  implicit val c0 = c
  import c.universe._

  /* Don't change this name since it's used
   * to check if a class is indeed a spore */
  val anonSporeName = TypeName("anonspore")

  type Env = List[(Symbol, Tree)]
  def conforms(funTree: c.Tree): (List[Symbol], Type, Tree, Env) = {
    val analysis = new SporeAnalysis[c.type](c)
    val (explicitSporeEnv, sporeFunDef) = analysis.stripSporeStructure(funTree)
    explicitSporeEnv foreach (s => debug(s"Explicitly captured symbol: $s"))

    val (funOpt, vparams, sporeBody) = analysis.readSporeFunDef(sporeFunDef)
    val functionSymbol = funOpt.map(_.symbol)
    val nonExplicitEnv = analysis.collectCaptured(sporeBody)
    val declared = analysis.collectDeclared(sporeBody)
    val env = explicitSporeEnv.map(_.symbol) ++ nonExplicitEnv.map(_._1)
    val checker = new SporeChecker[c.type](c)(env, functionSymbol, declared)

    debug(s"Checking conformance of ${showCode(sporeBody)}...")
    checker.checkReferencesInBody(sporeBody)
    val explicitEnv = explicitSporeEnv.map(vd => vd.symbol -> vd.rhs)
    val fullSporeEnv = nonExplicitEnv ++ explicitEnv
    (vparams.map(_.symbol), sporeBody.tpe, sporeBody, fullSporeEnv)
  }

  private class NameRewriter(oldName: Name, newName: Name)
      extends Transformer {
    // It's fine to introduce untyped trees as leafs of typed trees
    override def transform(tree: Tree): Tree = tree match {
      case i: Ident => if (i.name == oldName) Ident(newName) else i
      case t: This => if (t.qual == oldName) q"this" else t
      case _ => super.transform(tree)
    }
  }

  val sporeInfoSym = symbolOf[scala.spores.sporeInfo[Any, Any]]
  def convertSpore(sporeTree: c.Tree,
                   expectedCaptured: c.Type,
                   expectedExcluded: c.Type): c.Tree = {
    val analysis = new SporeAnalysis[c.type](c)
    val Block(List(sporeDefinition), instantiation) = sporeTree
    val sporeSym = sporeDefinition.symbol
    val List(sporeInfo) = sporeDefinition.symbol.annotations
    val List(captured, excluded) = sporeInfo.tree.tpe.typeArgs
    analysis.checkExcludedTypesInBody(expectedExcluded, captured)

    // TODO(jvican): Don't suppose code-generated spore
    val ClassDef(mods, _, tparams, tmpl) = sporeDefinition
    val newName = c.freshName(anonSporeName)
    val rewriter = new NameRewriter(sporeSym.name, newName)
    debug(s"Converting $sporeSym with type ${sporeSym.info.finalResultType}")
    val newSporeDef = c.untypecheck({
      val body = tmpl.body.map {
        case TypeDef(_, typeName, _, _)
            if typeName.decodedName.toString == "Excluded" =>
          q"type Excluded = $expectedExcluded"
        case t => t
      }

      val newSporeInfo = q"new scala.spores.sporeInfo[$captured, $expectedExcluded]()"
      val newMods = mods.mapAnnotations(anns => newSporeInfo :: anns)
      val newBody = body.map(t => rewriter.transform(t))
      val newImpl = treeCopy.Template(tmpl, tmpl.parents, tmpl.self, newBody)
      treeCopy.ClassDef(sporeDefinition, newMods, newName, tparams, newImpl)
    })

    val q"new ${_}(...$init)" = instantiation
    val conversion = q"""{
      $newSporeDef
      new $newName(...$init)
    }"""
    debug(s"Generated conversion:\n$conversion")
    conversion
  }

  def createSpore(funTree: c.Tree,
                  targs: List[c.Type],
                  expectedCapturedType: c.Type,
                  expectedExcludedType: c.Type,
                  explicitTypeArgs: Boolean = false): c.Tree = {
    val (paramSyms, retTpe, funBody, fullSporeEnv) = conforms(funTree)
    val (symbolsEnv, explicitRhsEnv) = fullSporeEnv.unzip
    val generator = new SporeGenerator[c.type](c)
    val sporeName = c.freshName(anonSporeName)

    val arity = paramSyms.size
    val sporeType =
      generator.generateSporeType(arity, targs, symbolsEnv.nonEmpty)
    if (symbolsEnv.isEmpty) {
      val sporeBody = generator.createNewDefDef(paramSyms, funBody, retTpe)
      generator.generateSpore(sporeName,
                              sporeType,
                              expectedCapturedType,
                              expectedExcludedType,
                              sporeBody,
                              Nil,
                              true,
                              expectedCapturedType)
    } else {
      val capturedTypes = symbolsEnv.map(_.typeSignature).toArray
      debug(s"Captured types: ${capturedTypes.mkString(",")}")
      val newRefs = generator.generateCapturedReferences(symbolsEnv)
      val sporeBody = generator.createNewDefDef(paramSyms,
                                                funBody,
                                                retTpe,
                                                environment = symbolsEnv,
                                                capturedRefs = newRefs)
      val envRefs = explicitRhsEnv.toArray
      val constructorParams = List(generator.toTuple(envRefs))
      val capturedType = generator.toTypeTuple(capturedTypes)
      val analysis = new SporeAnalysis[c.type](c)
      analysis.checkExcludedTypesInBody(expectedExcludedType, capturedType)
      debug(s"Forcing $expectedCapturedType? $explicitTypeArgs")
      val generated = generator.generateSpore(sporeName,
                                              sporeType,
                                              capturedType,
                                              expectedExcludedType,
                                              sporeBody,
                                              constructorParams,
                                              explicitTypeArgs,
                                              expectedCapturedType)

      // Whitebox macros allow overriding `Nothing`, prevent it
      if (explicitTypeArgs &&
          expectedCapturedType =:= definitions.NothingTpe &&
          !(capturedType =:= expectedCapturedType)) {
        c.abort(
          c.enclosingPosition,
          util.Feedback.CapturedTypeMismatch(expectedCapturedType.toString,
                                             capturedType.toString))
      }

      generated
    }
  }
}
