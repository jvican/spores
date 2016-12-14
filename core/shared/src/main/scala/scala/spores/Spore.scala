/*                     __                                               *\
**     ________ ___   / /  ___     Scala API                            **
**    / __/ __// _ | / /  / _ |    (c) 2002-2013, LAMP/EPFL             **
**  __\ \/ /__/ __ |/ /__/ __ |    http://scala-lang.org/               **
** /____/\___/_/ |_/____/_/ | |                                         **
**                          |/                                          **
\*                                                                      */

package scala.spores

sealed trait SporeBase extends Serializable {

  /** Represent the type of the captured variables.
    *
    * For more than one captured variable, the type is a tuple of types.
    */
  type Captured

  /** Represent the type of the excluded types.
    *
    * For more than one captured variable, the type is a tuple of types.
    */
  type Excluded

  /** Enable the creation of spores via reflection. */
  def className: String = _className
  protected[this] var _className: String = null

  /** The abstract member prevents conversion to Scala SAM types in 2.12.0.
    *
    * The existence of this member allows to kick in the implicit conversion
    * from a function to a spore instead of converting it to a Scala SAM.
    */
  def skipScalaSamConversion: Nothing
}

trait NullarySpore[+R] extends Function0[R] with SporeBase

trait NullarySporeWithEnv[+R] extends NullarySpore[R] {

  /** Store the environment of the Spore. */
  val captured: Captured
}

trait Spore[-T, +R] extends Function1[T, R] with SporeBase

trait SporeWithEnv[-T, +R] extends Spore[T, R] {

  /** Store the environment of the Spore. */
  val captured: Captured
}

trait Spore2[-T1, -T2, +R] extends Function2[T1, T2, R] with SporeBase

trait Spore2WithEnv[-T1, -T2, +R] extends Spore2[T1, T2, R] {

  /** Store the environment of the Spore. */
  val captured: Captured
}

trait Spore3[-T1, -T2, -T3, +R] extends Function3[T1, T2, T3, R] with SporeBase

trait Spore3WithEnv[-T1, -T2, -T3, +R] extends Spore3[T1, T2, T3, R] {

  /** Store the environment of the Spore. */
  val captured: Captured
}

trait Spore4[-T1, -T2, -T3, -T4, +R]
    extends Function4[T1, T2, T3, T4, R]
    with SporeBase

trait Spore4WithEnv[-T1, -T2, -T3, -T4, +R] extends Spore4[T1, T2, T3, T4, R] {

  /** Store the environment of the Spore. */
  val captured: Captured
}

trait Spore5[-T1, -T2, -T3, -T4, -T5, +R]
    extends Function5[T1, T2, T3, T4, T5, R]
    with SporeBase

trait Spore5WithEnv[-T1, -T2, -T3, -T4, -T5, +R]
    extends Spore5[T1, T2, T3, T4, T5, R] {

  /** Store the environment of the Spore. */
  val captured: Captured
}

trait Spore6[-T1, -T2, -T3, -T4, -T5, -T6, +R]
    extends Function6[T1, T2, T3, T4, T5, T6, R]
    with SporeBase

trait Spore6WithEnv[-T1, -T2, -T3, -T4, -T5, -T6, +R]
    extends Spore6[T1, T2, T3, T4, T5, T6, R] {

  /** Store the environment of the Spore. */
  val captured: Captured
}

trait Spore7[-T1, -T2, -T3, -T4, -T5, -T6, -T7, +R]
    extends Function7[T1, T2, T3, T4, T5, T6, T7, R]
    with SporeBase

trait Spore7WithEnv[-T1, -T2, -T3, -T4, -T5, -T6, -T7, +R]
    extends Spore7[T1, T2, T3, T4, T5, T6, T7, R] {

  /** Store the environment of the Spore. */
  val captured: Captured
}

trait Spore8[-T1, -T2, -T3, -T4, -T5, -T6, -T7, -T8, +R]
    extends Function8[T1, T2, T3, T4, T5, T6, T7, T8, R]
    with SporeBase

trait Spore8WithEnv[-T1, -T2, -T3, -T4, -T5, -T6, -T7, -T8, +R]
    extends Spore8[T1, T2, T3, T4, T5, T6, T7, T8, R] {

  /** Store the environment of the Spore. */
  val captured: Captured
}

trait Spore9[-T1, -T2, -T3, -T4, -T5, -T6, -T7, -T8, -T9, +R]
    extends Function9[T1, T2, T3, T4, T5, T6, T7, T8, T9, R]
    with SporeBase

trait Spore9WithEnv[-T1, -T2, -T3, -T4, -T5, -T6, -T7, -T8, -T9, +R]
    extends Spore9[T1, T2, T3, T4, T5, T6, T7, T8, T9, R] {

  /** Store the environment of the Spore. */
  val captured: Captured
}

trait Spore10[-T1, -T2, -T3, -T4, -T5, -T6, -T7, -T8, -T9, -T10, +R]
    extends Function10[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, R]
    with SporeBase

trait Spore10WithEnv[-T1, -T2, -T3, -T4, -T5, -T6, -T7, -T8, -T9, -T10, +R]
    extends Spore10[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, R] {

  /** Store the environment of the Spore. */
  val captured: Captured
}

trait Spore11[-T1, -T2, -T3, -T4, -T5, -T6, -T7, -T8, -T9, -T10, -T11, +R]
    extends Function11[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, R]
    with SporeBase

trait Spore11WithEnv[
    -T1, -T2, -T3, -T4, -T5, -T6, -T7, -T8, -T9, -T10, -T11, +R]
    extends Spore11[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, R] {

  /** Store the environment of the Spore. */
  val captured: Captured
}

trait Spore12[
    -T1, -T2, -T3, -T4, -T5, -T6, -T7, -T8, -T9, -T10, -T11, -T12, +R]
    extends Function12[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, R]
    with SporeBase

trait Spore12WithEnv[
    -T1, -T2, -T3, -T4, -T5, -T6, -T7, -T8, -T9, -T10, -T11, -T12, +R]
    extends Spore12[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, R] {

  /** Store the environment of the Spore. */
  val captured: Captured
}

trait Spore13[
    -T1, -T2, -T3, -T4, -T5, -T6, -T7, -T8, -T9, -T10, -T11, -T12, -T13, +R]
    extends Function13[T1,
                       T2,
                       T3,
                       T4,
                       T5,
                       T6,
                       T7,
                       T8,
                       T9,
                       T10,
                       T11,
                       T12,
                       T13,
                       R]
    with SporeBase

trait Spore13WithEnv[
    -T1, -T2, -T3, -T4, -T5, -T6, -T7, -T8, -T9, -T10, -T11, -T12, -T13, +R]
    extends Spore13[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, R] {

  /** Store the environment of the Spore. */
  val captured: Captured
}

trait Spore14[-T1,
              -T2,
              -T3,
              -T4,
              -T5,
              -T6,
              -T7,
              -T8,
              -T9,
              -T10,
              -T11,
              -T12,
              -T13,
              -T14,
              +R]
    extends Function14[T1,
                       T2,
                       T3,
                       T4,
                       T5,
                       T6,
                       T7,
                       T8,
                       T9,
                       T10,
                       T11,
                       T12,
                       T13,
                       T14,
                       R]
    with SporeBase

trait Spore14WithEnv[-T1,
                     -T2,
                     -T3,
                     -T4,
                     -T5,
                     -T6,
                     -T7,
                     -T8,
                     -T9,
                     -T10,
                     -T11,
                     -T12,
                     -T13,
                     -T14,
                     +R]
    extends Spore14[T1,
                    T2,
                    T3,
                    T4,
                    T5,
                    T6,
                    T7,
                    T8,
                    T9,
                    T10,
                    T11,
                    T12,
                    T13,
                    T14,
                    R] {

  /** Store the environment of the Spore. */
  val captured: Captured
}

trait Spore15[-T1,
              -T2,
              -T3,
              -T4,
              -T5,
              -T6,
              -T7,
              -T8,
              -T9,
              -T10,
              -T11,
              -T12,
              -T13,
              -T14,
              -T15,
              +R]
    extends Function15[T1,
                       T2,
                       T3,
                       T4,
                       T5,
                       T6,
                       T7,
                       T8,
                       T9,
                       T10,
                       T11,
                       T12,
                       T13,
                       T14,
                       T15,
                       R]
    with SporeBase

trait Spore15WithEnv[-T1,
                     -T2,
                     -T3,
                     -T4,
                     -T5,
                     -T6,
                     -T7,
                     -T8,
                     -T9,
                     -T10,
                     -T11,
                     -T12,
                     -T13,
                     -T14,
                     -T15,
                     +R]
    extends Spore15[T1,
                    T2,
                    T3,
                    T4,
                    T5,
                    T6,
                    T7,
                    T8,
                    T9,
                    T10,
                    T11,
                    T12,
                    T13,
                    T14,
                    T15,
                    R] {

  /** Store the environment of the Spore. */
  val captured: Captured
}

trait Spore16[-T1,
              -T2,
              -T3,
              -T4,
              -T5,
              -T6,
              -T7,
              -T8,
              -T9,
              -T10,
              -T11,
              -T12,
              -T13,
              -T14,
              -T15,
              -T16,
              +R]
    extends Function16[T1,
                       T2,
                       T3,
                       T4,
                       T5,
                       T6,
                       T7,
                       T8,
                       T9,
                       T10,
                       T11,
                       T12,
                       T13,
                       T14,
                       T15,
                       T16,
                       R]
    with SporeBase

trait Spore16WithEnv[-T1,
                     -T2,
                     -T3,
                     -T4,
                     -T5,
                     -T6,
                     -T7,
                     -T8,
                     -T9,
                     -T10,
                     -T11,
                     -T12,
                     -T13,
                     -T14,
                     -T15,
                     -T16,
                     +R]
    extends Spore16[T1,
                    T2,
                    T3,
                    T4,
                    T5,
                    T6,
                    T7,
                    T8,
                    T9,
                    T10,
                    T11,
                    T12,
                    T13,
                    T14,
                    T15,
                    T16,
                    R] {

  /** Store the environment of the Spore. */
  val captured: Captured
}

trait Spore17[-T1,
              -T2,
              -T3,
              -T4,
              -T5,
              -T6,
              -T7,
              -T8,
              -T9,
              -T10,
              -T11,
              -T12,
              -T13,
              -T14,
              -T15,
              -T16,
              -T17,
              +R]
    extends Function17[T1,
                       T2,
                       T3,
                       T4,
                       T5,
                       T6,
                       T7,
                       T8,
                       T9,
                       T10,
                       T11,
                       T12,
                       T13,
                       T14,
                       T15,
                       T16,
                       T17,
                       R]
    with SporeBase

trait Spore17WithEnv[-T1,
                     -T2,
                     -T3,
                     -T4,
                     -T5,
                     -T6,
                     -T7,
                     -T8,
                     -T9,
                     -T10,
                     -T11,
                     -T12,
                     -T13,
                     -T14,
                     -T15,
                     -T16,
                     -T17,
                     +R]
    extends Spore17[T1,
                    T2,
                    T3,
                    T4,
                    T5,
                    T6,
                    T7,
                    T8,
                    T9,
                    T10,
                    T11,
                    T12,
                    T13,
                    T14,
                    T15,
                    T16,
                    T17,
                    R] {

  /** Store the environment of the Spore. */
  val captured: Captured
}

trait Spore18[-T1,
              -T2,
              -T3,
              -T4,
              -T5,
              -T6,
              -T7,
              -T8,
              -T9,
              -T10,
              -T11,
              -T12,
              -T13,
              -T14,
              -T15,
              -T16,
              -T17,
              -T18,
              +R]
    extends Function18[T1,
                       T2,
                       T3,
                       T4,
                       T5,
                       T6,
                       T7,
                       T8,
                       T9,
                       T10,
                       T11,
                       T12,
                       T13,
                       T14,
                       T15,
                       T16,
                       T17,
                       T18,
                       R]
    with SporeBase

trait Spore18WithEnv[-T1,
                     -T2,
                     -T3,
                     -T4,
                     -T5,
                     -T6,
                     -T7,
                     -T8,
                     -T9,
                     -T10,
                     -T11,
                     -T12,
                     -T13,
                     -T14,
                     -T15,
                     -T16,
                     -T17,
                     -T18,
                     +R]
    extends Spore18[T1,
                    T2,
                    T3,
                    T4,
                    T5,
                    T6,
                    T7,
                    T8,
                    T9,
                    T10,
                    T11,
                    T12,
                    T13,
                    T14,
                    T15,
                    T16,
                    T17,
                    T18,
                    R] {

  /** Store the environment of the Spore. */
  val captured: Captured
}

trait Spore19[-T1,
              -T2,
              -T3,
              -T4,
              -T5,
              -T6,
              -T7,
              -T8,
              -T9,
              -T10,
              -T11,
              -T12,
              -T13,
              -T14,
              -T15,
              -T16,
              -T17,
              -T18,
              -T19,
              +R]
    extends Function19[T1,
                       T2,
                       T3,
                       T4,
                       T5,
                       T6,
                       T7,
                       T8,
                       T9,
                       T10,
                       T11,
                       T12,
                       T13,
                       T14,
                       T15,
                       T16,
                       T17,
                       T18,
                       T19,
                       R]
    with SporeBase

trait Spore19WithEnv[-T1,
                     -T2,
                     -T3,
                     -T4,
                     -T5,
                     -T6,
                     -T7,
                     -T8,
                     -T9,
                     -T10,
                     -T11,
                     -T12,
                     -T13,
                     -T14,
                     -T15,
                     -T16,
                     -T17,
                     -T18,
                     -T19,
                     +R]
    extends Spore19[T1,
                    T2,
                    T3,
                    T4,
                    T5,
                    T6,
                    T7,
                    T8,
                    T9,
                    T10,
                    T11,
                    T12,
                    T13,
                    T14,
                    T15,
                    T16,
                    T17,
                    T18,
                    T19,
                    R] {

  /** Store the environment of the Spore. */
  val captured: Captured
}

trait Spore20[-T1,
              -T2,
              -T3,
              -T4,
              -T5,
              -T6,
              -T7,
              -T8,
              -T9,
              -T10,
              -T11,
              -T12,
              -T13,
              -T14,
              -T15,
              -T16,
              -T17,
              -T18,
              -T19,
              -T20,
              +R]
    extends Function20[T1,
                       T2,
                       T3,
                       T4,
                       T5,
                       T6,
                       T7,
                       T8,
                       T9,
                       T10,
                       T11,
                       T12,
                       T13,
                       T14,
                       T15,
                       T16,
                       T17,
                       T18,
                       T19,
                       T20,
                       R]
    with SporeBase

trait Spore20WithEnv[-T1,
                     -T2,
                     -T3,
                     -T4,
                     -T5,
                     -T6,
                     -T7,
                     -T8,
                     -T9,
                     -T10,
                     -T11,
                     -T12,
                     -T13,
                     -T14,
                     -T15,
                     -T16,
                     -T17,
                     -T18,
                     -T19,
                     -T20,
                     +R]
    extends Spore20[T1,
                    T2,
                    T3,
                    T4,
                    T5,
                    T6,
                    T7,
                    T8,
                    T9,
                    T10,
                    T11,
                    T12,
                    T13,
                    T14,
                    T15,
                    T16,
                    T17,
                    T18,
                    T19,
                    T20,
                    R] {

  /** Store the environment of the Spore. */
  val captured: Captured
}

trait Spore21[-T1,
              -T2,
              -T3,
              -T4,
              -T5,
              -T6,
              -T7,
              -T8,
              -T9,
              -T10,
              -T11,
              -T12,
              -T13,
              -T14,
              -T15,
              -T16,
              -T17,
              -T18,
              -T19,
              -T20,
              -T21,
              +R]
    extends Function21[T1,
                       T2,
                       T3,
                       T4,
                       T5,
                       T6,
                       T7,
                       T8,
                       T9,
                       T10,
                       T11,
                       T12,
                       T13,
                       T14,
                       T15,
                       T16,
                       T17,
                       T18,
                       T19,
                       T20,
                       T21,
                       R]
    with SporeBase

trait Spore21WithEnv[-T1,
                     -T2,
                     -T3,
                     -T4,
                     -T5,
                     -T6,
                     -T7,
                     -T8,
                     -T9,
                     -T10,
                     -T11,
                     -T12,
                     -T13,
                     -T14,
                     -T15,
                     -T16,
                     -T17,
                     -T18,
                     -T19,
                     -T20,
                     -T21,
                     +R]
    extends Spore21[T1,
                    T2,
                    T3,
                    T4,
                    T5,
                    T6,
                    T7,
                    T8,
                    T9,
                    T10,
                    T11,
                    T12,
                    T13,
                    T14,
                    T15,
                    T16,
                    T17,
                    T18,
                    T19,
                    T20,
                    T21,
                    R] {

  /** Store the environment of the Spore. */
  val captured: Captured
}

trait Spore22[-T1,
              -T2,
              -T3,
              -T4,
              -T5,
              -T6,
              -T7,
              -T8,
              -T9,
              -T10,
              -T11,
              -T12,
              -T13,
              -T14,
              -T15,
              -T16,
              -T17,
              -T18,
              -T19,
              -T20,
              -T21,
              -T22,
              +R]
    extends Function22[T1,
                       T2,
                       T3,
                       T4,
                       T5,
                       T6,
                       T7,
                       T8,
                       T9,
                       T10,
                       T11,
                       T12,
                       T13,
                       T14,
                       T15,
                       T16,
                       T17,
                       T18,
                       T19,
                       T20,
                       T21,
                       T22,
                       R]
    with SporeBase

trait Spore22WithEnv[-T1,
                     -T2,
                     -T3,
                     -T4,
                     -T5,
                     -T6,
                     -T7,
                     -T8,
                     -T9,
                     -T10,
                     -T11,
                     -T12,
                     -T13,
                     -T14,
                     -T15,
                     -T16,
                     -T17,
                     -T18,
                     -T19,
                     -T20,
                     -T21,
                     -T22,
                     +R]
    extends Spore22[T1,
                    T2,
                    T3,
                    T4,
                    T5,
                    T6,
                    T7,
                    T8,
                    T9,
                    T10,
                    T11,
                    T12,
                    T13,
                    T14,
                    T15,
                    T16,
                    T17,
                    T18,
                    T19,
                    T20,
                    T21,
                    T22,
                    R] {

  /** Store the environment of the Spore. */
  val captured: Captured
}
