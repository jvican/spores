package scala.spores

/** A witness that ensures to the spores spark macro that a value
  * is serializable without changing the signature of a type.
  *
  * NOTE: defined in the spores package because compiler
  * plugin needs to access its symbol via the universe.
  */
trait CanSerialize[T]

trait SerializationWitnesses {
  implicit object ByteWitness extends CanSerialize[Byte]
  implicit object CharWitness extends CanSerialize[Char]
  implicit object ShortWitness extends CanSerialize[Short]
  implicit object IntWitness extends CanSerialize[Int]
  implicit object LongWitness extends CanSerialize[Long]
  implicit object DoubleWitness extends CanSerialize[Double]
  implicit object FloatWitness extends CanSerialize[Float]
}

object PrimitiveSerializationWitnesses extends SerializationWitnesses {
  implicit object StringWitness extends CanSerialize[String]
}
