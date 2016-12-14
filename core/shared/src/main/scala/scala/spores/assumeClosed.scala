package scala.spores

/** `AssumeClosed` is an annotation for those that depend on
  * compiled binaries. For third-party software, class hierarchy's
  * information is not enough for a full transitive check. By using
  * this annotation, the users *decides to trust* that the third-party
  * does not define non-serializable classes in any form or shape.
  *
  * NOTE: defined in the spores package because compiler
  * plugin needs to access its symbol via the universe.
  */
class assumeClosed extends scala.annotation.StaticAnnotation

