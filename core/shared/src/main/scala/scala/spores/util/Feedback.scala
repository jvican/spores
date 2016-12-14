package scala.spores.util

private[spores] object Feedback {
  val MissingSporeClassDef =
    "Expected spore class definition. The generated format of a spore has changed."

  val UnsupportedAritySpore =
    "The arity of a spore cannot be lower than 0 nor greater than 22."

  val IncorrectSporeHeader =
    "Incorrect spore header: Only val defs allowed at this position."

  val IncorrectSporeBody =
    "Incorrect spore body: expected function literal or `delayed`."

  val TupleFormatError =
    "You cannot construct a tuple of zero or more than 22 elements."

  def InvalidOuterReference(captured: String) =
    s"Only identifiers can be captured inside a spore with `capture`. Found: `$captured`."

  def InvalidLazyMember(captured: String) =
    s"The path of a captured variable inside a spore cannot contain lazy members. Found: `$captured`."

  def NonStaticInvocation(method: String) =
    s"Spore contains invocation of a non-static method: '$method'."

  def InvalidReferenceTo(symbol: String) =
    s"Spore contains references to an invalid symbol: $symbol."

  def InvalidReferenceToExcludedType(tpe: String) =
    s"Unexpected expression with type '$tpe', but type '$tpe' is Excluded."

  def CapturedTypeMismatch(found: String, required: String) =
    s"type mismatch in `Captured` type member;\n  found   : $found\n  required: $required"
}
