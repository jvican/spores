/*                     __                                               *\
**     ________ ___   / /  ___     Scala API                            **
**    / __/ __// _ | / /  / _ |    (c) 2002-2013, LAMP/EPFL             **
**  __\ \/ /__/ __ |/ /__/ __ |    http://scala-lang.org/               **
** /____/\___/_/ |_/____/_/ | |                                         **
**                          |/                                          **
\*                                                                      */

package scala

import scala.language.experimental.macros
import scala.language.{implicitConversions, postfixOps}
import scala.reflect.macros.{blackbox, whitebox}
import scala.spores.util.Versioning
import scala.util.Try

package object spores extends Versioning {

  ///////////////////////////////////////////////////////////////////////////////
  //////////////////////////////// SPORE UTILITIES //////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////

  object logger {
    def elem[T](es: sourcecode.Text[T]*)(implicit line: sourcecode.Line,
                                         file: sourcecode.File): Unit = {
      es.foreach { e =>
        val filename = file.value.replaceAll(".*/", "")
        val header = Console.GREEN + s"$filename:${line.value}"
        val source = Console.MAGENTA + s"[${e.source}]"
        println(s"$header $source ${Console.RESET}${e.value}")
      }
    }
  }

  /* Check that the debug or print:spores flags are passed to output logs. */
  @inline private[spores] def isDebugEnabled(ctx: blackbox.Context) = {
    ctx.settings.contains("debug-spores") ||
    ctx.compilerSettings.contains("-Ydebug")
  }

  private[spores] def debug(s: => String)(implicit line: sourcecode.Line,
                                          file: sourcecode.File,
                                          ctx: blackbox.Context): Unit = {
    if (isDebugEnabled(ctx)) logger.elem(s)
  }

  /** Transform a term into a the body of a function. */
  def delayed[T](body: T): () => T = () => body

  /** Capture a variable and return it. */
  def capture[T](x: T): T = x

  ///////////////////////////////////////////////////////////////////////////////
  ////////////////////////////// SPORE DEFINITIONS //////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////

  def spore[R, C, E](
    fun: () => R)
  : NullarySpore[R] =
    macro nullarySporeImpl[R, C, E]

    def spore[T1, R, C, E](
    fun: (T1) => R)
  : Spore[T1, R] =
    macro sporeImpl[T1, R, C, E]
       
  def spore[T1, T2, R, C, E](
    fun: (T1, T2) => R)
  : Spore2[T1, T2, R] =
    macro spore2Impl[T1, T2, R, C, E]
       
  def spore[T1, T2, T3, R, C, E](
    fun: (T1, T2, T3) => R)
  : Spore3[T1, T2, T3, R] =
    macro spore3Impl[T1, T2, T3, R, C, E]
       
  def spore[T1, T2, T3, T4, R, C, E](
    fun: (T1, T2, T3, T4) => R)
  : Spore4[T1, T2, T3, T4, R] =
    macro spore4Impl[T1, T2, T3, T4, R, C, E]
       
  def spore[T1, T2, T3, T4, T5, R, C, E](
    fun: (T1, T2, T3, T4, T5) => R)
  : Spore5[T1, T2, T3, T4, T5, R] =
    macro spore5Impl[T1, T2, T3, T4, T5, R, C, E]
       
  def spore[T1, T2, T3, T4, T5, T6, R, C, E](
    fun: (T1, T2, T3, T4, T5, T6) => R)
  : Spore6[T1, T2, T3, T4, T5, T6, R] =
    macro spore6Impl[T1, T2, T3, T4, T5, T6, R, C, E]
       
  def spore[T1, T2, T3, T4, T5, T6, T7, R, C, E](
    fun: (T1, T2, T3, T4, T5, T6, T7) => R)
  : Spore7[T1, T2, T3, T4, T5, T6, T7, R] =
    macro spore7Impl[T1, T2, T3, T4, T5, T6, T7, R, C, E]
       
  def spore[T1, T2, T3, T4, T5, T6, T7, T8, R, C, E](
    fun: (T1, T2, T3, T4, T5, T6, T7, T8) => R)
  : Spore8[T1, T2, T3, T4, T5, T6, T7, T8, R] =
    macro spore8Impl[T1, T2, T3, T4, T5, T6, T7, T8, R, C, E]
       
  def spore[T1, T2, T3, T4, T5, T6, T7, T8, T9, R, C, E](
    fun: (T1, T2, T3, T4, T5, T6, T7, T8, T9) => R)
  : Spore9[T1, T2, T3, T4, T5, T6, T7, T8, T9, R] =
    macro spore9Impl[T1, T2, T3, T4, T5, T6, T7, T8, T9, R, C, E]
       
  def spore[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, R, C, E](
    fun: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10) => R)
  : Spore10[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, R] =
    macro spore10Impl[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, R, C, E]
       
  def spore[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, R, C, E](
    fun: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11) => R)
  : Spore11[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, R] =
    macro spore11Impl[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, R, C, E]
       
  def spore[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, R, C, E](
    fun: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12) => R)
  : Spore12[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, R] =
    macro spore12Impl[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, R, C, E]
       
  def spore[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, R, C, E](
    fun: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13) => R)
  : Spore13[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, R] =
    macro spore13Impl[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, R, C, E]
       
  def spore[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, R, C, E](
    fun: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14) => R)
  : Spore14[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, R] =
    macro spore14Impl[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, R, C, E]
       
  def spore[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, R, C, E](
    fun: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15) => R)
  : Spore15[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, R] =
    macro spore15Impl[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, R, C, E]
       
  def spore[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, R, C, E](
    fun: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16) => R)
  : Spore16[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, R] =
    macro spore16Impl[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, R, C, E]
       
  def spore[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, R, C, E](
    fun: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17) => R)
  : Spore17[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, R] =
    macro spore17Impl[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, R, C, E]
       
  def spore[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, R, C, E](
    fun: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18) => R)
  : Spore18[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, R] =
    macro spore18Impl[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, R, C, E]
       
  def spore[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, R, C, E](
    fun: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19) => R)
  : Spore19[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, R] =
    macro spore19Impl[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, R, C, E]
       
  def spore[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, R, C, E](
    fun: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20) => R)
  : Spore20[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, R] =
    macro spore20Impl[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, R, C, E]
       
  def spore[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, R, C, E](
    fun: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21) => R)
  : Spore21[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, R] =
    macro spore21Impl[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, R, C, E]
       
  def spore[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, R, C, E](
    fun: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22) => R)
  : Spore22[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, R] =
    macro spore22Impl[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, R, C, E]

  ///////////////////////////////////////////////////////////////////////////////
  ////////////////////////////// SPORE CONVERSIONS //////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////

  implicit def function2Spore[T, R, C, E](fun: T => R): Spore[T, R] =
    macro sporeImpl[T, R, C, E]

  implicit def function2Spore[T1, T2, R, C, E](
    fun: (T1, T2) => R)
  : Spore2[T1, T2, R] =
    macro spore2Impl[T1, T2, R, C, E]
      
  implicit def function2Spore[T1, T2, T3, R, C, E](
    fun: (T1, T2, T3) => R)
  : Spore3[T1, T2, T3, R] =
    macro spore3Impl[T1, T2, T3, R, C, E]
      
  implicit def function2Spore[T1, T2, T3, T4, R, C, E](
    fun: (T1, T2, T3, T4) => R)
  : Spore4[T1, T2, T3, T4, R] =
    macro spore4Impl[T1, T2, T3, T4, R, C, E]
      
  implicit def function2Spore[T1, T2, T3, T4, T5, R, C, E](
    fun: (T1, T2, T3, T4, T5) => R)
  : Spore5[T1, T2, T3, T4, T5, R] =
    macro spore5Impl[T1, T2, T3, T4, T5, R, C, E]
      
  implicit def function2Spore[T1, T2, T3, T4, T5, T6, R, C, E](
    fun: (T1, T2, T3, T4, T5, T6) => R)
  : Spore6[T1, T2, T3, T4, T5, T6, R] =
    macro spore6Impl[T1, T2, T3, T4, T5, T6, R, C, E]
      
  implicit def function2Spore[T1, T2, T3, T4, T5, T6, T7, R, C, E](
    fun: (T1, T2, T3, T4, T5, T6, T7) => R)
  : Spore7[T1, T2, T3, T4, T5, T6, T7, R] =
    macro spore7Impl[T1, T2, T3, T4, T5, T6, T7, R, C, E]
      
  implicit def function2Spore[T1, T2, T3, T4, T5, T6, T7, T8, R, C, E](
    fun: (T1, T2, T3, T4, T5, T6, T7, T8) => R)
  : Spore8[T1, T2, T3, T4, T5, T6, T7, T8, R] =
    macro spore8Impl[T1, T2, T3, T4, T5, T6, T7, T8, R, C, E]
      
  implicit def function2Spore[T1, T2, T3, T4, T5, T6, T7, T8, T9, R, C, E](
    fun: (T1, T2, T3, T4, T5, T6, T7, T8, T9) => R)
  : Spore9[T1, T2, T3, T4, T5, T6, T7, T8, T9, R] =
    macro spore9Impl[T1, T2, T3, T4, T5, T6, T7, T8, T9, R, C, E]
      
  implicit def function2Spore[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, R, C, E](
    fun: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10) => R)
  : Spore10[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, R] =
    macro spore10Impl[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, R, C, E]
      
  implicit def function2Spore[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, R, C, E](
    fun: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11) => R)
  : Spore11[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, R] =
    macro spore11Impl[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, R, C, E]
      
  implicit def function2Spore[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, R, C, E](
    fun: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12) => R)
  : Spore12[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, R] =
    macro spore12Impl[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, R, C, E]
      
  implicit def function2Spore[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, R, C, E](
    fun: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13) => R)
  : Spore13[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, R] =
    macro spore13Impl[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, R, C, E]
      
  implicit def function2Spore[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, R, C, E](
    fun: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14) => R)
  : Spore14[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, R] =
    macro spore14Impl[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, R, C, E]
      
  implicit def function2Spore[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, R, C, E](
    fun: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15) => R)
  : Spore15[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, R] =
    macro spore15Impl[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, R, C, E]
      
  implicit def function2Spore[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, R, C, E](
    fun: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16) => R)
  : Spore16[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, R] =
    macro spore16Impl[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, R, C, E]
      
  implicit def function2Spore[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, R, C, E](
    fun: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17) => R)
  : Spore17[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, R] =
    macro spore17Impl[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, R, C, E]
      
  implicit def function2Spore[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, R, C, E](
    fun: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18) => R)
  : Spore18[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, R] =
    macro spore18Impl[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, R, C, E]
      
  implicit def function2Spore[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, R, C, E](
    fun: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19) => R)
  : Spore19[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, R] =
    macro spore19Impl[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, R, C, E]
      
  implicit def function2Spore[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, R, C, E](
    fun: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20) => R)
  : Spore20[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, R] =
    macro spore20Impl[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, R, C, E]
      
  implicit def function2Spore[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, R, C, E](
    fun: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21) => R)
  : Spore21[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, R] =
    macro spore21Impl[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, R, C, E]
      
  implicit def function2Spore[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, R, C, E](
    fun: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22) => R)
  : Spore22[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, R] =
    macro spore22Impl[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, R, C, E]

  ///////////////////////////////////////////////////////////////////////////////
  //////////////////////////// SPORE MACRO PROXIES //////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////

  /** Materialize type by transforming it to Nothing if the type is the
   * same as the defined type parameter in the spore definitions.
   * We check that they are the same by fiddling with the full name of sym.
   *
   * Scala reflect performs this transformation automatically for implicit
   * macro defs but unluckily this is not the case for our `spore` functions.
   *
   * __NOTE__: Assumes that NO TYPES are declared in this package. */
  val sporePackage = "scala.spores.package"
  def materializeType[C <: whitebox.Context, T: ctx.WeakTypeTag](ctx: C) = {
    import ctx.universe._
    val weakType = weakTypeOf[T]
    if (!weakType.typeSymbol.fullName.contains(sporePackage)) weakType
    else definitions.NothingTpe
  }

  /** Figure out whether the user explicitly set the captured type
    * member of a spore to [[Nothing]] or not.
    *
    * We check the original tree before typer to figure out if
    * the user used type args for `scala.spores.spore` or there is a
    * type ascription surrounding the spore tree.
    *
    * This is necessary because whitebox macros override [[Nothing]]
    * types because the type inferencer usually tries with it when
    * type arguments are not explicit in the call site. We want to
    * prevent any user from capturing [[Nothing]] and returning a
    * capturing spore , so we whitelist these situations from the tree.
    */
  def isUserCapturingNothing(ctx: whitebox.Context) = {
    val ctxFields = ctx.getClass.getDeclaredFields

    // Hijack macro implementation to get scala compiler
    val universe = ctxFields.find(_.getName == "universe").get
    universe.setAccessible(true)
    val g = universe.get(ctx).asInstanceOf[scala.tools.nsc.Global]
    val ts = g.analyzer.asInstanceOf[scala.tools.nsc.typechecker.Typers]

    // Access typer created by the macros framework
    val tper = ctxFields.find(_.getName == "callsiteTyper").get
    tper.setAccessible(true)
    val typer = tper.get(ctx).asInstanceOf[ts.Typer]

    // Get typed tree and untyped tree that went to typer
    val expandedSpore = ctx.macroApplication
    val sporePos = expandedSpore.pos
    val originalTree = typer.context.tree.asInstanceOf[g.Tree]

    // Find the original spore untyped tree
    var found: g.Tree = null
    originalTree.foreach { t =>
      if (found == null && t.pos == sporePos)
        found = t
    }

    def isCapturedNothing(tree: g.Tree) = tree match {
      case g.Ident(name0) =>
        val name = name0.decodedName.toString
        name0.isTypeName &&
        name == "Nothing" || name == "scala.Nothing"
      case _ => false
    }

    def capturesNothing(sporeType: g.Tree) = {
      var expectsCaptured = false
      sporeType.foreach {
        case g.TypeDef(_, name, _, rhs: g.Tree)
          if name.isTypeName &&
             name.decodedName.toString == "Captured" &&
             isCapturedNothing(rhs) =>
          expectsCaptured = true
        case _ =>
      }
      expectsCaptured
    }

    def isSporeFunTree(funTree: g.Tree) = funTree match {
      case g.Ident(name) =>
        val funName = name.decodedName.toString
        funName == "spore" || funName == "scala.spores.spore"
      case _ => false
    }

    // Check if user invokes spore with a concrete `Nothing`
    var hasCapturedNothing = found match {
      case g.Apply(ta @ g.TypeApply(funTree: g.Tree, _), _) =>
        isSporeFunTree(funTree) &&
        isCapturedNothing(ta.args.init.last.asInstanceOf[g.Tree])
      case _ => false
    }

    // Check if user explicitly captures `Nothing`
    if (!hasCapturedNothing) {
      originalTree foreach {
        case g.Typed(t, sporeType: g.Tree) if t == found =>
          if (!hasCapturedNothing)
            hasCapturedNothing = capturesNothing(sporeType)
        case g.ValDef(_, _, sporeType: g.Tree, rhs) if rhs == found =>
          val expectsCaptured = capturesNothing(sporeType)
          if (!hasCapturedNothing && expectsCaptured)
            hasCapturedNothing = expectsCaptured
        case _ =>
      }
    }

    hasCapturedNothing
  }

  def nullarySporeImpl[R: ctx.WeakTypeTag,
                       C: ctx.WeakTypeTag,
                       E: ctx.WeakTypeTag](ctx: whitebox.Context)(
      fun: ctx.Tree): ctx.Tree = {
    import ctx.universe._
    val impl = new MacroModule[ctx.type](ctx)
    val targs = List(weakTypeOf[R])
    val captured = materializeType[ctx.type, C](ctx)
    val forceCapturedType = isUserCapturingNothing(ctx)
    val excluded = materializeType[ctx.type, E](ctx)
    impl.createSpore(fun, targs, captured, excluded, forceCapturedType)
  }

  def sporeImpl[T: ctx.WeakTypeTag,
                R: ctx.WeakTypeTag,
                C: ctx.WeakTypeTag,
                E: ctx.WeakTypeTag](ctx: whitebox.Context)(
      fun: ctx.Tree): ctx.Tree = {
    import ctx.universe._
    val impl = new MacroModule[ctx.type](ctx)
    val targs = List(weakTypeOf[T], weakTypeOf[R])
    val captured = materializeType[ctx.type, C](ctx)
    val forceCapturedType = isUserCapturingNothing(ctx)
    val excluded = materializeType[ctx.type, E](ctx)
    impl.createSpore(fun, targs, captured, excluded, forceCapturedType)
  }

  def spore2Impl[T1: ctx.WeakTypeTag,
                 T2: ctx.WeakTypeTag,
                 R: ctx.WeakTypeTag,
                 C: ctx.WeakTypeTag,
                 E: ctx.WeakTypeTag](ctx: whitebox.Context)(
      fun: ctx.Tree): ctx.Tree = {
    import ctx.universe._
    val impl = new MacroModule[ctx.type](ctx)
    val targs = List(weakTypeOf[T1],
                     weakTypeOf[T2],
                     weakTypeOf[R])
    val captured = materializeType[ctx.type, C](ctx)
    val forceCapturedType = isUserCapturingNothing(ctx)
    val excluded = materializeType[ctx.type, E](ctx)
    impl.createSpore(fun, targs, captured, excluded, forceCapturedType)
  }

  def spore3Impl[T1: ctx.WeakTypeTag,
                 T2: ctx.WeakTypeTag,
                 T3: ctx.WeakTypeTag,
                 R: ctx.WeakTypeTag,
                 C: ctx.WeakTypeTag,
                 E: ctx.WeakTypeTag](ctx: whitebox.Context)(
      fun: ctx.Tree): ctx.Tree = {
    import ctx.universe._
    val impl = new MacroModule[ctx.type](ctx)
    val targs = List(weakTypeOf[T1],
                     weakTypeOf[T2],
                     weakTypeOf[T3],
                     weakTypeOf[R])
    val captured = materializeType[ctx.type, C](ctx)
    val forceCapturedType = isUserCapturingNothing(ctx)
    val excluded = materializeType[ctx.type, E](ctx)
    impl.createSpore(fun, targs, captured, excluded, forceCapturedType)
  }

  def spore4Impl[T1: ctx.WeakTypeTag,
                 T2: ctx.WeakTypeTag,
                 T3: ctx.WeakTypeTag,
                 T4: ctx.WeakTypeTag,
                 R: ctx.WeakTypeTag,
                 C: ctx.WeakTypeTag,
                 E: ctx.WeakTypeTag](ctx: whitebox.Context)(
      fun: ctx.Tree): ctx.Tree = {
    import ctx.universe._
    val impl = new MacroModule[ctx.type](ctx)
    val targs = List(weakTypeOf[T1],
                     weakTypeOf[T2],
                     weakTypeOf[T3],
                     weakTypeOf[T4],
                     weakTypeOf[R])
    val captured = materializeType[ctx.type, C](ctx)
    val forceCapturedType = isUserCapturingNothing(ctx)
    val excluded = materializeType[ctx.type, E](ctx)
    impl.createSpore(fun, targs, captured, excluded, forceCapturedType)
  }

  def spore5Impl[T1: ctx.WeakTypeTag,
                 T2: ctx.WeakTypeTag,
                 T3: ctx.WeakTypeTag,
                 T4: ctx.WeakTypeTag,
                 T5: ctx.WeakTypeTag,
                 R: ctx.WeakTypeTag,
                 C: ctx.WeakTypeTag,
                 E: ctx.WeakTypeTag](ctx: whitebox.Context)(
      fun: ctx.Tree): ctx.Tree = {
    import ctx.universe._
    val impl = new MacroModule[ctx.type](ctx)
    val targs = List(weakTypeOf[T1],
                     weakTypeOf[T2],
                     weakTypeOf[T3],
                     weakTypeOf[T4],
                     weakTypeOf[T5],
                     weakTypeOf[R])
    val captured = materializeType[ctx.type, C](ctx)
    val forceCapturedType = isUserCapturingNothing(ctx)
    val excluded = materializeType[ctx.type, E](ctx)
    impl.createSpore(fun, targs, captured, excluded, forceCapturedType)
  }

  def spore6Impl[T1: ctx.WeakTypeTag,
                 T2: ctx.WeakTypeTag,
                 T3: ctx.WeakTypeTag,
                 T4: ctx.WeakTypeTag,
                 T5: ctx.WeakTypeTag,
                 T6: ctx.WeakTypeTag,
                 R: ctx.WeakTypeTag,
                 C: ctx.WeakTypeTag,
                 E: ctx.WeakTypeTag](ctx: whitebox.Context)(
      fun: ctx.Tree): ctx.Tree = {
    import ctx.universe._
    val impl = new MacroModule[ctx.type](ctx)
    val targs = List(weakTypeOf[T1],
                     weakTypeOf[T2],
                     weakTypeOf[T3],
                     weakTypeOf[T4],
                     weakTypeOf[T5],
                     weakTypeOf[T6],
                     weakTypeOf[R])
    val captured = materializeType[ctx.type, C](ctx)
    val forceCapturedType = isUserCapturingNothing(ctx)
    val excluded = materializeType[ctx.type, E](ctx)
    impl.createSpore(fun, targs, captured, excluded, forceCapturedType)
  }

  def spore7Impl[T1: ctx.WeakTypeTag,
                 T2: ctx.WeakTypeTag,
                 T3: ctx.WeakTypeTag,
                 T4: ctx.WeakTypeTag,
                 T5: ctx.WeakTypeTag,
                 T6: ctx.WeakTypeTag,
                 T7: ctx.WeakTypeTag,
                 R: ctx.WeakTypeTag,
                 C: ctx.WeakTypeTag,
                 E: ctx.WeakTypeTag](ctx: whitebox.Context)(
      fun: ctx.Tree): ctx.Tree = {
    import ctx.universe._
    val impl = new MacroModule[ctx.type](ctx)
    val targs = List(weakTypeOf[T1],
                     weakTypeOf[T2],
                     weakTypeOf[T3],
                     weakTypeOf[T4],
                     weakTypeOf[T5],
                     weakTypeOf[T6],
                     weakTypeOf[T7],
                     weakTypeOf[R])
    val captured = materializeType[ctx.type, C](ctx)
    val forceCapturedType = isUserCapturingNothing(ctx)
    val excluded = materializeType[ctx.type, E](ctx)
    impl.createSpore(fun, targs, captured, excluded, forceCapturedType)
  }

  def spore8Impl[T1: ctx.WeakTypeTag,
                 T2: ctx.WeakTypeTag,
                 T3: ctx.WeakTypeTag,
                 T4: ctx.WeakTypeTag,
                 T5: ctx.WeakTypeTag,
                 T6: ctx.WeakTypeTag,
                 T7: ctx.WeakTypeTag,
                 T8: ctx.WeakTypeTag,
                 R: ctx.WeakTypeTag,
                 C: ctx.WeakTypeTag,
                 E: ctx.WeakTypeTag](ctx: whitebox.Context)(
      fun: ctx.Tree): ctx.Tree = {
    import ctx.universe._
    val impl = new MacroModule[ctx.type](ctx)
    val targs = List(weakTypeOf[T1],
                     weakTypeOf[T2],
                     weakTypeOf[T3],
                     weakTypeOf[T4],
                     weakTypeOf[T5],
                     weakTypeOf[T6],
                     weakTypeOf[T7],
                     weakTypeOf[T8],
                     weakTypeOf[R])
    val captured = materializeType[ctx.type, C](ctx)
    val forceCapturedType = isUserCapturingNothing(ctx)
    val excluded = materializeType[ctx.type, E](ctx)
    impl.createSpore(fun, targs, captured, excluded, forceCapturedType)
  }

  def spore9Impl[T1: ctx.WeakTypeTag,
                 T2: ctx.WeakTypeTag,
                 T3: ctx.WeakTypeTag,
                 T4: ctx.WeakTypeTag,
                 T5: ctx.WeakTypeTag,
                 T6: ctx.WeakTypeTag,
                 T7: ctx.WeakTypeTag,
                 T8: ctx.WeakTypeTag,
                 T9: ctx.WeakTypeTag,
                 R: ctx.WeakTypeTag,
                 C: ctx.WeakTypeTag,
                 E: ctx.WeakTypeTag](ctx: whitebox.Context)(
      fun: ctx.Tree): ctx.Tree = {
    import ctx.universe._
    val impl = new MacroModule[ctx.type](ctx)
    val targs = List(weakTypeOf[T1],
                     weakTypeOf[T2],
                     weakTypeOf[T3],
                     weakTypeOf[T4],
                     weakTypeOf[T5],
                     weakTypeOf[T6],
                     weakTypeOf[T7],
                     weakTypeOf[T8],
                     weakTypeOf[T9],
                     weakTypeOf[R])
    val captured = materializeType[ctx.type, C](ctx)
    val forceCapturedType = isUserCapturingNothing(ctx)
    val excluded = materializeType[ctx.type, E](ctx)
    impl.createSpore(fun, targs, captured, excluded, forceCapturedType)
  }

  def spore10Impl[T1: ctx.WeakTypeTag,
                  T2: ctx.WeakTypeTag,
                  T3: ctx.WeakTypeTag,
                  T4: ctx.WeakTypeTag,
                  T5: ctx.WeakTypeTag,
                  T6: ctx.WeakTypeTag,
                  T7: ctx.WeakTypeTag,
                  T8: ctx.WeakTypeTag,
                  T9: ctx.WeakTypeTag,
                  T10: ctx.WeakTypeTag,
                 R: ctx.WeakTypeTag,
                 C: ctx.WeakTypeTag,
                 E: ctx.WeakTypeTag](ctx: whitebox.Context)(
      fun: ctx.Tree): ctx.Tree = {
    import ctx.universe._
    val impl = new MacroModule[ctx.type](ctx)
    val targs = List(weakTypeOf[T1],
                     weakTypeOf[T2],
                     weakTypeOf[T3],
                     weakTypeOf[T4],
                     weakTypeOf[T5],
                     weakTypeOf[T6],
                     weakTypeOf[T7],
                     weakTypeOf[T8],
                     weakTypeOf[T9],
                     weakTypeOf[T10],
                     weakTypeOf[R])
    val captured = materializeType[ctx.type, C](ctx)
    val forceCapturedType = isUserCapturingNothing(ctx)
    val excluded = materializeType[ctx.type, E](ctx)
    impl.createSpore(fun, targs, captured, excluded, forceCapturedType)
  }

  def spore11Impl[T1: ctx.WeakTypeTag,
                  T2: ctx.WeakTypeTag,
                  T3: ctx.WeakTypeTag,
                  T4: ctx.WeakTypeTag,
                  T5: ctx.WeakTypeTag,
                  T6: ctx.WeakTypeTag,
                  T7: ctx.WeakTypeTag,
                  T8: ctx.WeakTypeTag,
                  T9: ctx.WeakTypeTag,
                  T10: ctx.WeakTypeTag,
                  T11: ctx.WeakTypeTag,
                 R: ctx.WeakTypeTag,
                 C: ctx.WeakTypeTag,
                 E: ctx.WeakTypeTag](ctx: whitebox.Context)(
      fun: ctx.Tree): ctx.Tree = {
    import ctx.universe._
    val impl = new MacroModule[ctx.type](ctx)
    val targs = List(weakTypeOf[T1],
                     weakTypeOf[T2],
                     weakTypeOf[T3],
                     weakTypeOf[T4],
                     weakTypeOf[T5],
                     weakTypeOf[T6],
                     weakTypeOf[T7],
                     weakTypeOf[T8],
                     weakTypeOf[T9],
                     weakTypeOf[T10],
                     weakTypeOf[T11],
                     weakTypeOf[R])
    val captured = materializeType[ctx.type, C](ctx)
    val forceCapturedType = isUserCapturingNothing(ctx)
    val excluded = materializeType[ctx.type, E](ctx)
    impl.createSpore(fun, targs, captured, excluded, forceCapturedType)
  }

  def spore12Impl[T1: ctx.WeakTypeTag,
                  T2: ctx.WeakTypeTag,
                  T3: ctx.WeakTypeTag,
                  T4: ctx.WeakTypeTag,
                  T5: ctx.WeakTypeTag,
                  T6: ctx.WeakTypeTag,
                  T7: ctx.WeakTypeTag,
                  T8: ctx.WeakTypeTag,
                  T9: ctx.WeakTypeTag,
                  T10: ctx.WeakTypeTag,
                  T11: ctx.WeakTypeTag,
                  T12: ctx.WeakTypeTag,
                 R: ctx.WeakTypeTag,
                 C: ctx.WeakTypeTag,
                 E: ctx.WeakTypeTag](ctx: whitebox.Context)(
      fun: ctx.Tree): ctx.Tree = {
    import ctx.universe._
    val impl = new MacroModule[ctx.type](ctx)
    val targs = List(weakTypeOf[T1],
                     weakTypeOf[T2],
                     weakTypeOf[T3],
                     weakTypeOf[T4],
                     weakTypeOf[T5],
                     weakTypeOf[T6],
                     weakTypeOf[T7],
                     weakTypeOf[T8],
                     weakTypeOf[T9],
                     weakTypeOf[T10],
                     weakTypeOf[T11],
                     weakTypeOf[T12],
                     weakTypeOf[R])
    val captured = materializeType[ctx.type, C](ctx)
    val forceCapturedType = isUserCapturingNothing(ctx)
    val excluded = materializeType[ctx.type, E](ctx)
    impl.createSpore(fun, targs, captured, excluded, forceCapturedType)
  }

  def spore13Impl[T1: ctx.WeakTypeTag,
                  T2: ctx.WeakTypeTag,
                  T3: ctx.WeakTypeTag,
                  T4: ctx.WeakTypeTag,
                  T5: ctx.WeakTypeTag,
                  T6: ctx.WeakTypeTag,
                  T7: ctx.WeakTypeTag,
                  T8: ctx.WeakTypeTag,
                  T9: ctx.WeakTypeTag,
                  T10: ctx.WeakTypeTag,
                  T11: ctx.WeakTypeTag,
                  T12: ctx.WeakTypeTag,
                  T13: ctx.WeakTypeTag,
                 R: ctx.WeakTypeTag,
                 C: ctx.WeakTypeTag,
                 E: ctx.WeakTypeTag](ctx: whitebox.Context)(
      fun: ctx.Tree): ctx.Tree = {
    import ctx.universe._
    val impl = new MacroModule[ctx.type](ctx)
    val targs = List(weakTypeOf[T1],
                     weakTypeOf[T2],
                     weakTypeOf[T3],
                     weakTypeOf[T4],
                     weakTypeOf[T5],
                     weakTypeOf[T6],
                     weakTypeOf[T7],
                     weakTypeOf[T8],
                     weakTypeOf[T9],
                     weakTypeOf[T10],
                     weakTypeOf[T11],
                     weakTypeOf[T12],
                     weakTypeOf[T13],
                     weakTypeOf[R])
    val captured = materializeType[ctx.type, C](ctx)
    val forceCapturedType = isUserCapturingNothing(ctx)
    val excluded = materializeType[ctx.type, E](ctx)
    impl.createSpore(fun, targs, captured, excluded, forceCapturedType)
  }

  def spore14Impl[T1: ctx.WeakTypeTag,
                  T2: ctx.WeakTypeTag,
                  T3: ctx.WeakTypeTag,
                  T4: ctx.WeakTypeTag,
                  T5: ctx.WeakTypeTag,
                  T6: ctx.WeakTypeTag,
                  T7: ctx.WeakTypeTag,
                  T8: ctx.WeakTypeTag,
                  T9: ctx.WeakTypeTag,
                  T10: ctx.WeakTypeTag,
                  T11: ctx.WeakTypeTag,
                  T12: ctx.WeakTypeTag,
                  T13: ctx.WeakTypeTag,
                  T14: ctx.WeakTypeTag,
                 R: ctx.WeakTypeTag,
                 C: ctx.WeakTypeTag,
                 E: ctx.WeakTypeTag](ctx: whitebox.Context)(
      fun: ctx.Tree): ctx.Tree = {
    import ctx.universe._
    val impl = new MacroModule[ctx.type](ctx)
    val targs = List(weakTypeOf[T1],
                     weakTypeOf[T2],
                     weakTypeOf[T3],
                     weakTypeOf[T4],
                     weakTypeOf[T5],
                     weakTypeOf[T6],
                     weakTypeOf[T7],
                     weakTypeOf[T8],
                     weakTypeOf[T9],
                     weakTypeOf[T10],
                     weakTypeOf[T11],
                     weakTypeOf[T12],
                     weakTypeOf[T13],
                     weakTypeOf[T14],
                     weakTypeOf[R])
    val captured = materializeType[ctx.type, C](ctx)
    val forceCapturedType = isUserCapturingNothing(ctx)
    val excluded = materializeType[ctx.type, E](ctx)
    impl.createSpore(fun, targs, captured, excluded, forceCapturedType)
  }

  def spore15Impl[T1: ctx.WeakTypeTag,
                  T2: ctx.WeakTypeTag,
                  T3: ctx.WeakTypeTag,
                  T4: ctx.WeakTypeTag,
                  T5: ctx.WeakTypeTag,
                  T6: ctx.WeakTypeTag,
                  T7: ctx.WeakTypeTag,
                  T8: ctx.WeakTypeTag,
                  T9: ctx.WeakTypeTag,
                  T10: ctx.WeakTypeTag,
                  T11: ctx.WeakTypeTag,
                  T12: ctx.WeakTypeTag,
                  T13: ctx.WeakTypeTag,
                  T14: ctx.WeakTypeTag,
                  T15: ctx.WeakTypeTag,
                 R: ctx.WeakTypeTag,
                 C: ctx.WeakTypeTag,
                 E: ctx.WeakTypeTag](ctx: whitebox.Context)(
      fun: ctx.Tree): ctx.Tree = {
    import ctx.universe._
    val impl = new MacroModule[ctx.type](ctx)
    val targs = List(weakTypeOf[T1],
                     weakTypeOf[T2],
                     weakTypeOf[T3],
                     weakTypeOf[T4],
                     weakTypeOf[T5],
                     weakTypeOf[T6],
                     weakTypeOf[T7],
                     weakTypeOf[T8],
                     weakTypeOf[T9],
                     weakTypeOf[T10],
                     weakTypeOf[T11],
                     weakTypeOf[T12],
                     weakTypeOf[T13],
                     weakTypeOf[T14],
                     weakTypeOf[T15],
                     weakTypeOf[R])
    val captured = materializeType[ctx.type, C](ctx)
    val forceCapturedType = isUserCapturingNothing(ctx)
    val excluded = materializeType[ctx.type, E](ctx)
    impl.createSpore(fun, targs, captured, excluded, forceCapturedType)
  }

  def spore16Impl[T1: ctx.WeakTypeTag,
                  T2: ctx.WeakTypeTag,
                  T3: ctx.WeakTypeTag,
                  T4: ctx.WeakTypeTag,
                  T5: ctx.WeakTypeTag,
                  T6: ctx.WeakTypeTag,
                  T7: ctx.WeakTypeTag,
                  T8: ctx.WeakTypeTag,
                  T9: ctx.WeakTypeTag,
                  T10: ctx.WeakTypeTag,
                  T11: ctx.WeakTypeTag,
                  T12: ctx.WeakTypeTag,
                  T13: ctx.WeakTypeTag,
                  T14: ctx.WeakTypeTag,
                  T15: ctx.WeakTypeTag,
                  T16: ctx.WeakTypeTag,
                 R: ctx.WeakTypeTag,
                 C: ctx.WeakTypeTag,
                 E: ctx.WeakTypeTag](ctx: whitebox.Context)(
      fun: ctx.Tree): ctx.Tree = {
    import ctx.universe._
    val impl = new MacroModule[ctx.type](ctx)
    val targs = List(weakTypeOf[T1],
                     weakTypeOf[T2],
                     weakTypeOf[T3],
                     weakTypeOf[T4],
                     weakTypeOf[T5],
                     weakTypeOf[T6],
                     weakTypeOf[T7],
                     weakTypeOf[T8],
                     weakTypeOf[T9],
                     weakTypeOf[T10],
                     weakTypeOf[T11],
                     weakTypeOf[T12],
                     weakTypeOf[T13],
                     weakTypeOf[T14],
                     weakTypeOf[T15],
                     weakTypeOf[T16],
                     weakTypeOf[R])
    val captured = materializeType[ctx.type, C](ctx)
    val forceCapturedType = isUserCapturingNothing(ctx)
    val excluded = materializeType[ctx.type, E](ctx)
    impl.createSpore(fun, targs, captured, excluded, forceCapturedType)
  }

  def spore17Impl[T1: ctx.WeakTypeTag,
                  T2: ctx.WeakTypeTag,
                  T3: ctx.WeakTypeTag,
                  T4: ctx.WeakTypeTag,
                  T5: ctx.WeakTypeTag,
                  T6: ctx.WeakTypeTag,
                  T7: ctx.WeakTypeTag,
                  T8: ctx.WeakTypeTag,
                  T9: ctx.WeakTypeTag,
                  T10: ctx.WeakTypeTag,
                  T11: ctx.WeakTypeTag,
                  T12: ctx.WeakTypeTag,
                  T13: ctx.WeakTypeTag,
                  T14: ctx.WeakTypeTag,
                  T15: ctx.WeakTypeTag,
                  T16: ctx.WeakTypeTag,
                  T17: ctx.WeakTypeTag,
                 R: ctx.WeakTypeTag,
                 C: ctx.WeakTypeTag,
                 E: ctx.WeakTypeTag](ctx: whitebox.Context)(
      fun: ctx.Tree): ctx.Tree = {
    import ctx.universe._
    val impl = new MacroModule[ctx.type](ctx)
    val targs = List(weakTypeOf[T1],
                     weakTypeOf[T2],
                     weakTypeOf[T3],
                     weakTypeOf[T4],
                     weakTypeOf[T5],
                     weakTypeOf[T6],
                     weakTypeOf[T7],
                     weakTypeOf[T8],
                     weakTypeOf[T9],
                     weakTypeOf[T10],
                     weakTypeOf[T11],
                     weakTypeOf[T12],
                     weakTypeOf[T13],
                     weakTypeOf[T14],
                     weakTypeOf[T15],
                     weakTypeOf[T16],
                     weakTypeOf[T17],
                     weakTypeOf[R])
    val captured = materializeType[ctx.type, C](ctx)
    val forceCapturedType = isUserCapturingNothing(ctx)
    val excluded = materializeType[ctx.type, E](ctx)
    impl.createSpore(fun, targs, captured, excluded, forceCapturedType)
  }

  def spore18Impl[T1: ctx.WeakTypeTag,
                  T2: ctx.WeakTypeTag,
                  T3: ctx.WeakTypeTag,
                  T4: ctx.WeakTypeTag,
                  T5: ctx.WeakTypeTag,
                  T6: ctx.WeakTypeTag,
                  T7: ctx.WeakTypeTag,
                  T8: ctx.WeakTypeTag,
                  T9: ctx.WeakTypeTag,
                  T10: ctx.WeakTypeTag,
                  T11: ctx.WeakTypeTag,
                  T12: ctx.WeakTypeTag,
                  T13: ctx.WeakTypeTag,
                  T14: ctx.WeakTypeTag,
                  T15: ctx.WeakTypeTag,
                  T16: ctx.WeakTypeTag,
                  T17: ctx.WeakTypeTag,
                  T18: ctx.WeakTypeTag,
                 R: ctx.WeakTypeTag,
                 C: ctx.WeakTypeTag,
                 E: ctx.WeakTypeTag](ctx: whitebox.Context)(
      fun: ctx.Tree): ctx.Tree = {
    import ctx.universe._
    val impl = new MacroModule[ctx.type](ctx)
    val targs = List(weakTypeOf[T1],
                     weakTypeOf[T2],
                     weakTypeOf[T3],
                     weakTypeOf[T4],
                     weakTypeOf[T5],
                     weakTypeOf[T6],
                     weakTypeOf[T7],
                     weakTypeOf[T8],
                     weakTypeOf[T9],
                     weakTypeOf[T10],
                     weakTypeOf[T11],
                     weakTypeOf[T12],
                     weakTypeOf[T13],
                     weakTypeOf[T14],
                     weakTypeOf[T15],
                     weakTypeOf[T16],
                     weakTypeOf[T17],
                     weakTypeOf[T18],
                     weakTypeOf[R])
    val captured = materializeType[ctx.type, C](ctx)
    val forceCapturedType = isUserCapturingNothing(ctx)
    val excluded = materializeType[ctx.type, E](ctx)
    impl.createSpore(fun, targs, captured, excluded, forceCapturedType)
  }

  def spore19Impl[T1: ctx.WeakTypeTag,
                  T2: ctx.WeakTypeTag,
                  T3: ctx.WeakTypeTag,
                  T4: ctx.WeakTypeTag,
                  T5: ctx.WeakTypeTag,
                  T6: ctx.WeakTypeTag,
                  T7: ctx.WeakTypeTag,
                  T8: ctx.WeakTypeTag,
                  T9: ctx.WeakTypeTag,
                  T10: ctx.WeakTypeTag,
                  T11: ctx.WeakTypeTag,
                  T12: ctx.WeakTypeTag,
                  T13: ctx.WeakTypeTag,
                  T14: ctx.WeakTypeTag,
                  T15: ctx.WeakTypeTag,
                  T16: ctx.WeakTypeTag,
                  T17: ctx.WeakTypeTag,
                  T18: ctx.WeakTypeTag,
                  T19: ctx.WeakTypeTag,
                 R: ctx.WeakTypeTag,
                 C: ctx.WeakTypeTag,
                 E: ctx.WeakTypeTag](ctx: whitebox.Context)(
      fun: ctx.Tree): ctx.Tree = {
    import ctx.universe._
    val impl = new MacroModule[ctx.type](ctx)
    val targs = List(weakTypeOf[T1],
                     weakTypeOf[T2],
                     weakTypeOf[T3],
                     weakTypeOf[T4],
                     weakTypeOf[T5],
                     weakTypeOf[T6],
                     weakTypeOf[T7],
                     weakTypeOf[T8],
                     weakTypeOf[T9],
                     weakTypeOf[T10],
                     weakTypeOf[T11],
                     weakTypeOf[T12],
                     weakTypeOf[T13],
                     weakTypeOf[T14],
                     weakTypeOf[T15],
                     weakTypeOf[T16],
                     weakTypeOf[T17],
                     weakTypeOf[T18],
                     weakTypeOf[T19],
                     weakTypeOf[R])
    val captured = materializeType[ctx.type, C](ctx)
    val forceCapturedType = isUserCapturingNothing(ctx)
    val excluded = materializeType[ctx.type, E](ctx)
    impl.createSpore(fun, targs, captured, excluded, forceCapturedType)
  }

  def spore20Impl[T1: ctx.WeakTypeTag,
                  T2: ctx.WeakTypeTag,
                  T3: ctx.WeakTypeTag,
                  T4: ctx.WeakTypeTag,
                  T5: ctx.WeakTypeTag,
                  T6: ctx.WeakTypeTag,
                  T7: ctx.WeakTypeTag,
                  T8: ctx.WeakTypeTag,
                  T9: ctx.WeakTypeTag,
                  T10: ctx.WeakTypeTag,
                  T11: ctx.WeakTypeTag,
                  T12: ctx.WeakTypeTag,
                  T13: ctx.WeakTypeTag,
                  T14: ctx.WeakTypeTag,
                  T15: ctx.WeakTypeTag,
                  T16: ctx.WeakTypeTag,
                  T17: ctx.WeakTypeTag,
                  T18: ctx.WeakTypeTag,
                  T19: ctx.WeakTypeTag,
                  T20: ctx.WeakTypeTag,
                 R: ctx.WeakTypeTag,
                 C: ctx.WeakTypeTag,
                 E: ctx.WeakTypeTag](ctx: whitebox.Context)(
      fun: ctx.Tree): ctx.Tree = {
    import ctx.universe._
    val impl = new MacroModule[ctx.type](ctx)
    val targs = List(weakTypeOf[T1],
                     weakTypeOf[T2],
                     weakTypeOf[T3],
                     weakTypeOf[T4],
                     weakTypeOf[T5],
                     weakTypeOf[T6],
                     weakTypeOf[T7],
                     weakTypeOf[T8],
                     weakTypeOf[T9],
                     weakTypeOf[T10],
                     weakTypeOf[T11],
                     weakTypeOf[T12],
                     weakTypeOf[T13],
                     weakTypeOf[T14],
                     weakTypeOf[T15],
                     weakTypeOf[T16],
                     weakTypeOf[T17],
                     weakTypeOf[T18],
                     weakTypeOf[T19],
                     weakTypeOf[T20],
                     weakTypeOf[R])
    val captured = materializeType[ctx.type, C](ctx)
    val forceCapturedType = isUserCapturingNothing(ctx)
    val excluded = materializeType[ctx.type, E](ctx)
    impl.createSpore(fun, targs, captured, excluded, forceCapturedType)
  }

  def spore21Impl[T1: ctx.WeakTypeTag,
                  T2: ctx.WeakTypeTag,
                  T3: ctx.WeakTypeTag,
                  T4: ctx.WeakTypeTag,
                  T5: ctx.WeakTypeTag,
                  T6: ctx.WeakTypeTag,
                  T7: ctx.WeakTypeTag,
                  T8: ctx.WeakTypeTag,
                  T9: ctx.WeakTypeTag,
                  T10: ctx.WeakTypeTag,
                  T11: ctx.WeakTypeTag,
                  T12: ctx.WeakTypeTag,
                  T13: ctx.WeakTypeTag,
                  T14: ctx.WeakTypeTag,
                  T15: ctx.WeakTypeTag,
                  T16: ctx.WeakTypeTag,
                  T17: ctx.WeakTypeTag,
                  T18: ctx.WeakTypeTag,
                  T19: ctx.WeakTypeTag,
                  T20: ctx.WeakTypeTag,
                  T21: ctx.WeakTypeTag,
                 R: ctx.WeakTypeTag,
                 C: ctx.WeakTypeTag,
                 E: ctx.WeakTypeTag](ctx: whitebox.Context)(
      fun: ctx.Tree): ctx.Tree = {
    import ctx.universe._
    val impl = new MacroModule[ctx.type](ctx)
    val targs = List(weakTypeOf[T1],
                     weakTypeOf[T2],
                     weakTypeOf[T3],
                     weakTypeOf[T4],
                     weakTypeOf[T5],
                     weakTypeOf[T6],
                     weakTypeOf[T7],
                     weakTypeOf[T8],
                     weakTypeOf[T9],
                     weakTypeOf[T10],
                     weakTypeOf[T11],
                     weakTypeOf[T12],
                     weakTypeOf[T13],
                     weakTypeOf[T14],
                     weakTypeOf[T15],
                     weakTypeOf[T16],
                     weakTypeOf[T17],
                     weakTypeOf[T18],
                     weakTypeOf[T19],
                     weakTypeOf[T20],
                     weakTypeOf[T21],
                     weakTypeOf[R])
    val captured = materializeType[ctx.type, C](ctx)
    val forceCapturedType = isUserCapturingNothing(ctx)
    val excluded = materializeType[ctx.type, E](ctx)
    impl.createSpore(fun, targs, captured, excluded, forceCapturedType)
  }

  def spore22Impl[T1: ctx.WeakTypeTag,
                  T2: ctx.WeakTypeTag,
                  T3: ctx.WeakTypeTag,
                  T4: ctx.WeakTypeTag,
                  T5: ctx.WeakTypeTag,
                  T6: ctx.WeakTypeTag,
                  T7: ctx.WeakTypeTag,
                  T8: ctx.WeakTypeTag,
                  T9: ctx.WeakTypeTag,
                  T10: ctx.WeakTypeTag,
                  T11: ctx.WeakTypeTag,
                  T12: ctx.WeakTypeTag,
                  T13: ctx.WeakTypeTag,
                  T14: ctx.WeakTypeTag,
                  T15: ctx.WeakTypeTag,
                  T16: ctx.WeakTypeTag,
                  T17: ctx.WeakTypeTag,
                  T18: ctx.WeakTypeTag,
                  T19: ctx.WeakTypeTag,
                  T20: ctx.WeakTypeTag,
                  T21: ctx.WeakTypeTag,
                  T22: ctx.WeakTypeTag,
                 R: ctx.WeakTypeTag,
                 C: ctx.WeakTypeTag,
                 E: ctx.WeakTypeTag](ctx: whitebox.Context)(
      fun: ctx.Tree): ctx.Tree = {
    import ctx.universe._
    val impl = new MacroModule[ctx.type](ctx)
    val targs = List(weakTypeOf[T1],
                     weakTypeOf[T2],
                     weakTypeOf[T3],
                     weakTypeOf[T4],
                     weakTypeOf[T5],
                     weakTypeOf[T6],
                     weakTypeOf[T7],
                     weakTypeOf[T8],
                     weakTypeOf[T9],
                     weakTypeOf[T10],
                     weakTypeOf[T11],
                     weakTypeOf[T12],
                     weakTypeOf[T13],
                     weakTypeOf[T14],
                     weakTypeOf[T15],
                     weakTypeOf[T16],
                     weakTypeOf[T17],
                     weakTypeOf[T18],
                     weakTypeOf[T19],
                     weakTypeOf[T20],
                     weakTypeOf[T21],
                     weakTypeOf[T22],
                     weakTypeOf[R])
    val captured = materializeType[ctx.type, C](ctx)
    val forceCapturedType = isUserCapturingNothing(ctx)
    val excluded = materializeType[ctx.type, E](ctx)
    impl.createSpore(fun, targs, captured, excluded, forceCapturedType)
  }

  ///////////////////////////////////////////////////////////////////////////////
  /////////////////////////// SPORE SPECIFIC CONVERSIONS ////////////////////////
  ///////////////////////////////////////////////////////////////////////////////

  implicit def concreteSpore[T, R, C, E](
      spore: NullarySpore[R]
  ): NullarySpore[R] {
    type Excluded = E
  } = macro convertNullarySporeTo[R, C, E]

  implicit def concreteSpore[T, R, C, E](
      spore: Spore[T, R]
  ): Spore[T, R] {
    type Excluded = E
  } = macro convertSporeTo[T, R, C, E]

  implicit def concreteSpore[T1, T2, R, C, E](
      spore: Spore2[T1, T2, R]
  ): Spore2[T1, T2, R] {
    type Excluded = E
  } = macro convertSpore2To[T1, T2, R, C, E]
      
  implicit def concreteSpore[T1, T2, T3, R, C, E](
      spore: Spore3[T1, T2, T3, R]
  ): Spore3[T1, T2, T3, R] {
    type Excluded = E
  } = macro convertSpore3To[T1, T2, T3, R, C, E]
      
  implicit def concreteSpore[T1, T2, T3, T4, R, C, E](
      spore: Spore4[T1, T2, T3, T4, R]
  ): Spore4[T1, T2, T3, T4, R] {
    type Captured = C
    type Excluded = E
  } = macro convertSpore4To[T1, T2, T3, T4, R, C, E]
      
  implicit def concreteSpore[T1, T2, T3, T4, T5, R, C, E](
      spore: Spore5[T1, T2, T3, T4, T5, R]
  ): Spore5[T1, T2, T3, T4, T5, R] {
    type Captured = C
    type Excluded = E
  } = macro convertSpore5To[T1, T2, T3, T4, T5, R, C, E]
      
  implicit def concreteSpore[T1, T2, T3, T4, T5, T6, R, C, E](
      spore: Spore6[T1, T2, T3, T4, T5, T6, R]
  ): Spore6[T1, T2, T3, T4, T5, T6, R] {
    type Captured = C
    type Excluded = E
  } = macro convertSpore6To[T1, T2, T3, T4, T5, T6, R, C, E]
      
  implicit def concreteSpore[T1, T2, T3, T4, T5, T6, T7, R, C, E](
      spore: Spore7[T1, T2, T3, T4, T5, T6, T7, R]
  ): Spore7[T1, T2, T3, T4, T5, T6, T7, R] {
    type Captured = C
    type Excluded = E
  } = macro convertSpore7To[T1, T2, T3, T4, T5, T6, T7, R, C, E]
      
  implicit def concreteSpore[T1, T2, T3, T4, T5, T6, T7, T8, R, C, E](
      spore: Spore8[T1, T2, T3, T4, T5, T6, T7, T8, R]
  ): Spore8[T1, T2, T3, T4, T5, T6, T7, T8, R] {
    type Captured = C
    type Excluded = E
  } = macro convertSpore8To[T1, T2, T3, T4, T5, T6, T7, T8, R, C, E]
      
  implicit def concreteSpore[T1, T2, T3, T4, T5, T6, T7, T8, T9, R, C, E](
      spore: Spore9[T1, T2, T3, T4, T5, T6, T7, T8, T9, R]
  ): Spore9[T1, T2, T3, T4, T5, T6, T7, T8, T9, R] {
    type Captured = C
    type Excluded = E
  } = macro convertSpore9To[T1, T2, T3, T4, T5, T6, T7, T8, T9, R, C, E]
      
  implicit def concreteSpore[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, R, C, E](
      spore: Spore10[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, R]
  ): Spore10[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, R] {
    type Captured = C
    type Excluded = E
  } = macro convertSpore10To[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, R, C, E]
      
  implicit def concreteSpore[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, R, C, E](
      spore: Spore11[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, R]
  ): Spore11[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, R] {
    type Captured = C
    type Excluded = E
  } = macro convertSpore11To[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, R, C, E]
      
  implicit def concreteSpore[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, R, C, E](
      spore: Spore12[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, R]
  ): Spore12[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, R] {
    type Captured = C
    type Excluded = E
  } = macro convertSpore12To[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, R, C, E]
      
  implicit def concreteSpore[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, R, C, E](
      spore: Spore13[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, R]
  ): Spore13[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, R] {
    type Captured = C
    type Excluded = E
  } = macro convertSpore13To[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, R, C, E]
      
  implicit def concreteSpore[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, R, C, E](
      spore: Spore14[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, R]
  ): Spore14[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, R] {
    type Captured = C
    type Excluded = E
  } = macro convertSpore14To[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, R, C, E]
      
  implicit def concreteSpore[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, R, C, E](
      spore: Spore15[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, R]
  ): Spore15[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, R] {
    type Captured = C
    type Excluded = E
  } = macro convertSpore15To[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, R, C, E]
      
  implicit def concreteSpore[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, R, C, E](
      spore: Spore16[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, R]
  ): Spore16[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, R] {
    type Captured = C
    type Excluded = E
  } = macro convertSpore16To[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, R, C, E]
      
  implicit def concreteSpore[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, R, C, E](
      spore: Spore17[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, R]
  ): Spore17[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, R] {
    type Captured = C
    type Excluded = E
  } = macro convertSpore17To[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, R, C, E]
      
  implicit def concreteSpore[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, R, C, E](
      spore: Spore18[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, R]
  ): Spore18[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, R] {
    type Captured = C
    type Excluded = E
  } = macro convertSpore18To[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, R, C, E]
      
  implicit def concreteSpore[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, R, C, E](
      spore: Spore19[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, R]
  ): Spore19[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, R] {
    type Captured = C
    type Excluded = E
  } = macro convertSpore19To[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, R, C, E]
      
  implicit def concreteSpore[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, R, C, E](
      spore: Spore20[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, R]
  ): Spore20[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, R] {
    type Captured = C
    type Excluded = E
  } = macro convertSpore20To[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, R, C, E]
      
  implicit def concreteSpore[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, R, C, E](
      spore: Spore21[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, R]
  ): Spore21[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, R] {
    type Captured = C
    type Excluded = E
  } = macro convertSpore21To[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, R, C, E]
      
  implicit def concreteSpore[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, R, C, E](
      spore: Spore22[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, R]
  ): Spore22[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, R] {
    type Captured = C
    type Excluded = E
  } = macro convertSpore22To[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, R, C, E]

  ///////////////////////////////////////////////////////////////////////////////
  /////////////////////////// SPORE SPECIFIC PROXIES ////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////

  def convertNullarySporeTo[R: ctx.WeakTypeTag,
                            C: ctx.WeakTypeTag,
                            E: ctx.WeakTypeTag](ctx: whitebox.Context)(
      spore: ctx.Tree): ctx.Tree = {
    val impl = new MacroModule[ctx.type](ctx)
    val captured = materializeType[ctx.type, C](ctx)
    val excluded = materializeType[ctx.type, E](ctx)
    impl.convertSpore(spore, captured, excluded)
  }

  def convertSporeTo[T: ctx.WeakTypeTag,
                     R: ctx.WeakTypeTag,
                     C: ctx.WeakTypeTag,
                     E: ctx.WeakTypeTag](ctx: whitebox.Context)(
      spore: ctx.Tree): ctx.Tree = {
    val impl = new MacroModule[ctx.type](ctx)
    val captured = materializeType[ctx.type, C](ctx)
    val excluded = materializeType[ctx.type, E](ctx)
    impl.convertSpore(spore, captured, excluded)
  }
          
  def convertSpore2To[T1: ctx.WeakTypeTag,
                      T2: ctx.WeakTypeTag,
                      R: ctx.WeakTypeTag,
                      C: ctx.WeakTypeTag,
                      E: ctx.WeakTypeTag](ctx: whitebox.Context)(
      spore: ctx.Tree): ctx.Tree = {
    val impl = new MacroModule[ctx.type](ctx)
    val captured = materializeType[ctx.type, C](ctx)
    val excluded = materializeType[ctx.type, E](ctx)
    impl.convertSpore(spore, captured, excluded)
  }
          
  def convertSpore3To[T1: ctx.WeakTypeTag,
                      T2: ctx.WeakTypeTag,
                      T3: ctx.WeakTypeTag,
                      R: ctx.WeakTypeTag,
                      C: ctx.WeakTypeTag,
                      E: ctx.WeakTypeTag](ctx: whitebox.Context)(
      spore: ctx.Tree): ctx.Tree = {
    val impl = new MacroModule[ctx.type](ctx)
    val captured = materializeType[ctx.type, C](ctx)
    val excluded = materializeType[ctx.type, E](ctx)
    impl.convertSpore(spore, captured, excluded)
  }
          
  def convertSpore4To[T1: ctx.WeakTypeTag,
                      T2: ctx.WeakTypeTag,
                      T3: ctx.WeakTypeTag,
                      T4: ctx.WeakTypeTag,
                      R: ctx.WeakTypeTag,
                      C: ctx.WeakTypeTag,
                      E: ctx.WeakTypeTag](ctx: whitebox.Context)(
      spore: ctx.Tree): ctx.Tree = {
    val impl = new MacroModule[ctx.type](ctx)
    val captured = materializeType[ctx.type, C](ctx)
    val excluded = materializeType[ctx.type, E](ctx)
    impl.convertSpore(spore, captured, excluded)
  }
          
  def convertSpore5To[T1: ctx.WeakTypeTag,
                      T2: ctx.WeakTypeTag,
                      T3: ctx.WeakTypeTag,
                      T4: ctx.WeakTypeTag,
                      T5: ctx.WeakTypeTag,
                      R: ctx.WeakTypeTag,
                      C: ctx.WeakTypeTag,
                      E: ctx.WeakTypeTag](ctx: whitebox.Context)(
      spore: ctx.Tree): ctx.Tree = {
    val impl = new MacroModule[ctx.type](ctx)
    val captured = materializeType[ctx.type, C](ctx)
    val excluded = materializeType[ctx.type, E](ctx)
    impl.convertSpore(spore, captured, excluded)
  }
          
  def convertSpore6To[T1: ctx.WeakTypeTag,
                      T2: ctx.WeakTypeTag,
                      T3: ctx.WeakTypeTag,
                      T4: ctx.WeakTypeTag,
                      T5: ctx.WeakTypeTag,
                      T6: ctx.WeakTypeTag,
                      R: ctx.WeakTypeTag,
                      C: ctx.WeakTypeTag,
                      E: ctx.WeakTypeTag](ctx: whitebox.Context)(
      spore: ctx.Tree): ctx.Tree = {
    val impl = new MacroModule[ctx.type](ctx)
    val captured = materializeType[ctx.type, C](ctx)
    val excluded = materializeType[ctx.type, E](ctx)
    impl.convertSpore(spore, captured, excluded)
  }
          
  def convertSpore7To[T1: ctx.WeakTypeTag,
                      T2: ctx.WeakTypeTag,
                      T3: ctx.WeakTypeTag,
                      T4: ctx.WeakTypeTag,
                      T5: ctx.WeakTypeTag,
                      T6: ctx.WeakTypeTag,
                      T7: ctx.WeakTypeTag,
                      R: ctx.WeakTypeTag,
                      C: ctx.WeakTypeTag,
                      E: ctx.WeakTypeTag](ctx: whitebox.Context)(
      spore: ctx.Tree): ctx.Tree = {
    val impl = new MacroModule[ctx.type](ctx)
    val captured = materializeType[ctx.type, C](ctx)
    val excluded = materializeType[ctx.type, E](ctx)
    impl.convertSpore(spore, captured, excluded)
  }
          
  def convertSpore8To[T1: ctx.WeakTypeTag,
                      T2: ctx.WeakTypeTag,
                      T3: ctx.WeakTypeTag,
                      T4: ctx.WeakTypeTag,
                      T5: ctx.WeakTypeTag,
                      T6: ctx.WeakTypeTag,
                      T7: ctx.WeakTypeTag,
                      T8: ctx.WeakTypeTag,
                      R: ctx.WeakTypeTag,
                      C: ctx.WeakTypeTag,
                      E: ctx.WeakTypeTag](ctx: whitebox.Context)(
      spore: ctx.Tree): ctx.Tree = {
    val impl = new MacroModule[ctx.type](ctx)
    val captured = materializeType[ctx.type, C](ctx)
    val excluded = materializeType[ctx.type, E](ctx)
    impl.convertSpore(spore, captured, excluded)
  }
          
  def convertSpore9To[T1: ctx.WeakTypeTag,
                      T2: ctx.WeakTypeTag,
                      T3: ctx.WeakTypeTag,
                      T4: ctx.WeakTypeTag,
                      T5: ctx.WeakTypeTag,
                      T6: ctx.WeakTypeTag,
                      T7: ctx.WeakTypeTag,
                      T8: ctx.WeakTypeTag,
                      T9: ctx.WeakTypeTag,
                      R: ctx.WeakTypeTag,
                      C: ctx.WeakTypeTag,
                      E: ctx.WeakTypeTag](ctx: whitebox.Context)(
      spore: ctx.Tree): ctx.Tree = {
    val impl = new MacroModule[ctx.type](ctx)
    val captured = materializeType[ctx.type, C](ctx)
    val excluded = materializeType[ctx.type, E](ctx)
    impl.convertSpore(spore, captured, excluded)
  }
          
  def convertSpore10To[T1: ctx.WeakTypeTag,
                       T2: ctx.WeakTypeTag,
                       T3: ctx.WeakTypeTag,
                       T4: ctx.WeakTypeTag,
                       T5: ctx.WeakTypeTag,
                       T6: ctx.WeakTypeTag,
                       T7: ctx.WeakTypeTag,
                       T8: ctx.WeakTypeTag,
                       T9: ctx.WeakTypeTag,
                       T10: ctx.WeakTypeTag,
                       R: ctx.WeakTypeTag,
                       C: ctx.WeakTypeTag,
                       E: ctx.WeakTypeTag](ctx: whitebox.Context)(
      spore: ctx.Tree): ctx.Tree = {
    val impl = new MacroModule[ctx.type](ctx)
    val captured = materializeType[ctx.type, C](ctx)
    val excluded = materializeType[ctx.type, E](ctx)
    impl.convertSpore(spore, captured, excluded)
  }
          
  def convertSpore11To[T1: ctx.WeakTypeTag,
                       T2: ctx.WeakTypeTag,
                       T3: ctx.WeakTypeTag,
                       T4: ctx.WeakTypeTag,
                       T5: ctx.WeakTypeTag,
                       T6: ctx.WeakTypeTag,
                       T7: ctx.WeakTypeTag,
                       T8: ctx.WeakTypeTag,
                       T9: ctx.WeakTypeTag,
                       T10: ctx.WeakTypeTag,
                       T11: ctx.WeakTypeTag,
                       R: ctx.WeakTypeTag,
                       C: ctx.WeakTypeTag,
                       E: ctx.WeakTypeTag](ctx: whitebox.Context)(
      spore: ctx.Tree): ctx.Tree = {
    val impl = new MacroModule[ctx.type](ctx)
    val captured = materializeType[ctx.type, C](ctx)
    val excluded = materializeType[ctx.type, E](ctx)
    impl.convertSpore(spore, captured, excluded)
  }
          
  def convertSpore12To[T1: ctx.WeakTypeTag,
                       T2: ctx.WeakTypeTag,
                       T3: ctx.WeakTypeTag,
                       T4: ctx.WeakTypeTag,
                       T5: ctx.WeakTypeTag,
                       T6: ctx.WeakTypeTag,
                       T7: ctx.WeakTypeTag,
                       T8: ctx.WeakTypeTag,
                       T9: ctx.WeakTypeTag,
                       T10: ctx.WeakTypeTag,
                       T11: ctx.WeakTypeTag,
                       T12: ctx.WeakTypeTag,
                       R: ctx.WeakTypeTag,
                       C: ctx.WeakTypeTag,
                       E: ctx.WeakTypeTag](ctx: whitebox.Context)(
      spore: ctx.Tree): ctx.Tree = {
    val impl = new MacroModule[ctx.type](ctx)
    val captured = materializeType[ctx.type, C](ctx)
    val excluded = materializeType[ctx.type, E](ctx)
    impl.convertSpore(spore, captured, excluded)
  }
          
  def convertSpore13To[T1: ctx.WeakTypeTag,
                       T2: ctx.WeakTypeTag,
                       T3: ctx.WeakTypeTag,
                       T4: ctx.WeakTypeTag,
                       T5: ctx.WeakTypeTag,
                       T6: ctx.WeakTypeTag,
                       T7: ctx.WeakTypeTag,
                       T8: ctx.WeakTypeTag,
                       T9: ctx.WeakTypeTag,
                       T10: ctx.WeakTypeTag,
                       T11: ctx.WeakTypeTag,
                       T12: ctx.WeakTypeTag,
                       T13: ctx.WeakTypeTag,
                       R: ctx.WeakTypeTag,
                       C: ctx.WeakTypeTag,
                       E: ctx.WeakTypeTag](ctx: whitebox.Context)(
      spore: ctx.Tree): ctx.Tree = {
    val impl = new MacroModule[ctx.type](ctx)
    val captured = materializeType[ctx.type, C](ctx)
    val excluded = materializeType[ctx.type, E](ctx)
    impl.convertSpore(spore, captured, excluded)
  }
          
  def convertSpore14To[T1: ctx.WeakTypeTag,
                       T2: ctx.WeakTypeTag,
                       T3: ctx.WeakTypeTag,
                       T4: ctx.WeakTypeTag,
                       T5: ctx.WeakTypeTag,
                       T6: ctx.WeakTypeTag,
                       T7: ctx.WeakTypeTag,
                       T8: ctx.WeakTypeTag,
                       T9: ctx.WeakTypeTag,
                       T10: ctx.WeakTypeTag,
                       T11: ctx.WeakTypeTag,
                       T12: ctx.WeakTypeTag,
                       T13: ctx.WeakTypeTag,
                       T14: ctx.WeakTypeTag,
                       R: ctx.WeakTypeTag,
                       C: ctx.WeakTypeTag,
                       E: ctx.WeakTypeTag](ctx: whitebox.Context)(
      spore: ctx.Tree): ctx.Tree = {
    val impl = new MacroModule[ctx.type](ctx)
    val captured = materializeType[ctx.type, C](ctx)
    val excluded = materializeType[ctx.type, E](ctx)
    impl.convertSpore(spore, captured, excluded)
  }
          
  def convertSpore15To[T1: ctx.WeakTypeTag,
                       T2: ctx.WeakTypeTag,
                       T3: ctx.WeakTypeTag,
                       T4: ctx.WeakTypeTag,
                       T5: ctx.WeakTypeTag,
                       T6: ctx.WeakTypeTag,
                       T7: ctx.WeakTypeTag,
                       T8: ctx.WeakTypeTag,
                       T9: ctx.WeakTypeTag,
                       T10: ctx.WeakTypeTag,
                       T11: ctx.WeakTypeTag,
                       T12: ctx.WeakTypeTag,
                       T13: ctx.WeakTypeTag,
                       T14: ctx.WeakTypeTag,
                       T15: ctx.WeakTypeTag,
                       R: ctx.WeakTypeTag,
                       C: ctx.WeakTypeTag,
                       E: ctx.WeakTypeTag](ctx: whitebox.Context)(
      spore: ctx.Tree): ctx.Tree = {
    val impl = new MacroModule[ctx.type](ctx)
    val captured = materializeType[ctx.type, C](ctx)
    val excluded = materializeType[ctx.type, E](ctx)
    impl.convertSpore(spore, captured, excluded)
  }
          
  def convertSpore16To[T1: ctx.WeakTypeTag,
                       T2: ctx.WeakTypeTag,
                       T3: ctx.WeakTypeTag,
                       T4: ctx.WeakTypeTag,
                       T5: ctx.WeakTypeTag,
                       T6: ctx.WeakTypeTag,
                       T7: ctx.WeakTypeTag,
                       T8: ctx.WeakTypeTag,
                       T9: ctx.WeakTypeTag,
                       T10: ctx.WeakTypeTag,
                       T11: ctx.WeakTypeTag,
                       T12: ctx.WeakTypeTag,
                       T13: ctx.WeakTypeTag,
                       T14: ctx.WeakTypeTag,
                       T15: ctx.WeakTypeTag,
                       T16: ctx.WeakTypeTag,
                       R: ctx.WeakTypeTag,
                       C: ctx.WeakTypeTag,
                       E: ctx.WeakTypeTag](ctx: whitebox.Context)(
      spore: ctx.Tree): ctx.Tree = {
    val impl = new MacroModule[ctx.type](ctx)
    val captured = materializeType[ctx.type, C](ctx)
    val excluded = materializeType[ctx.type, E](ctx)
    impl.convertSpore(spore, captured, excluded)
  }
          
  def convertSpore17To[T1: ctx.WeakTypeTag,
                       T2: ctx.WeakTypeTag,
                       T3: ctx.WeakTypeTag,
                       T4: ctx.WeakTypeTag,
                       T5: ctx.WeakTypeTag,
                       T6: ctx.WeakTypeTag,
                       T7: ctx.WeakTypeTag,
                       T8: ctx.WeakTypeTag,
                       T9: ctx.WeakTypeTag,
                       T10: ctx.WeakTypeTag,
                       T11: ctx.WeakTypeTag,
                       T12: ctx.WeakTypeTag,
                       T13: ctx.WeakTypeTag,
                       T14: ctx.WeakTypeTag,
                       T15: ctx.WeakTypeTag,
                       T16: ctx.WeakTypeTag,
                       T17: ctx.WeakTypeTag,
                       R: ctx.WeakTypeTag,
                       C: ctx.WeakTypeTag,
                       E: ctx.WeakTypeTag](ctx: whitebox.Context)(
      spore: ctx.Tree): ctx.Tree = {
    val impl = new MacroModule[ctx.type](ctx)
    val captured = materializeType[ctx.type, C](ctx)
    val excluded = materializeType[ctx.type, E](ctx)
    impl.convertSpore(spore, captured, excluded)
  }
          
  def convertSpore18To[T1: ctx.WeakTypeTag,
                       T2: ctx.WeakTypeTag,
                       T3: ctx.WeakTypeTag,
                       T4: ctx.WeakTypeTag,
                       T5: ctx.WeakTypeTag,
                       T6: ctx.WeakTypeTag,
                       T7: ctx.WeakTypeTag,
                       T8: ctx.WeakTypeTag,
                       T9: ctx.WeakTypeTag,
                       T10: ctx.WeakTypeTag,
                       T11: ctx.WeakTypeTag,
                       T12: ctx.WeakTypeTag,
                       T13: ctx.WeakTypeTag,
                       T14: ctx.WeakTypeTag,
                       T15: ctx.WeakTypeTag,
                       T16: ctx.WeakTypeTag,
                       T17: ctx.WeakTypeTag,
                       T18: ctx.WeakTypeTag,
                       R: ctx.WeakTypeTag,
                       C: ctx.WeakTypeTag,
                       E: ctx.WeakTypeTag](ctx: whitebox.Context)(
      spore: ctx.Tree): ctx.Tree = {
    val impl = new MacroModule[ctx.type](ctx)
    val captured = materializeType[ctx.type, C](ctx)
    val excluded = materializeType[ctx.type, E](ctx)
    impl.convertSpore(spore, captured, excluded)
  }
          
  def convertSpore19To[T1: ctx.WeakTypeTag,
                       T2: ctx.WeakTypeTag,
                       T3: ctx.WeakTypeTag,
                       T4: ctx.WeakTypeTag,
                       T5: ctx.WeakTypeTag,
                       T6: ctx.WeakTypeTag,
                       T7: ctx.WeakTypeTag,
                       T8: ctx.WeakTypeTag,
                       T9: ctx.WeakTypeTag,
                       T10: ctx.WeakTypeTag,
                       T11: ctx.WeakTypeTag,
                       T12: ctx.WeakTypeTag,
                       T13: ctx.WeakTypeTag,
                       T14: ctx.WeakTypeTag,
                       T15: ctx.WeakTypeTag,
                       T16: ctx.WeakTypeTag,
                       T17: ctx.WeakTypeTag,
                       T18: ctx.WeakTypeTag,
                       T19: ctx.WeakTypeTag,
                       R: ctx.WeakTypeTag,
                       C: ctx.WeakTypeTag,
                       E: ctx.WeakTypeTag](ctx: whitebox.Context)(
      spore: ctx.Tree): ctx.Tree = {
    val impl = new MacroModule[ctx.type](ctx)
    val captured = materializeType[ctx.type, C](ctx)
    val excluded = materializeType[ctx.type, E](ctx)
    impl.convertSpore(spore, captured, excluded)
  }
          
  def convertSpore20To[T1: ctx.WeakTypeTag,
                       T2: ctx.WeakTypeTag,
                       T3: ctx.WeakTypeTag,
                       T4: ctx.WeakTypeTag,
                       T5: ctx.WeakTypeTag,
                       T6: ctx.WeakTypeTag,
                       T7: ctx.WeakTypeTag,
                       T8: ctx.WeakTypeTag,
                       T9: ctx.WeakTypeTag,
                       T10: ctx.WeakTypeTag,
                       T11: ctx.WeakTypeTag,
                       T12: ctx.WeakTypeTag,
                       T13: ctx.WeakTypeTag,
                       T14: ctx.WeakTypeTag,
                       T15: ctx.WeakTypeTag,
                       T16: ctx.WeakTypeTag,
                       T17: ctx.WeakTypeTag,
                       T18: ctx.WeakTypeTag,
                       T19: ctx.WeakTypeTag,
                       T20: ctx.WeakTypeTag,
                       R: ctx.WeakTypeTag,
                       C: ctx.WeakTypeTag,
                       E: ctx.WeakTypeTag](ctx: whitebox.Context)(
      spore: ctx.Tree): ctx.Tree = {
    val impl = new MacroModule[ctx.type](ctx)
    val captured = materializeType[ctx.type, C](ctx)
    val excluded = materializeType[ctx.type, E](ctx)
    impl.convertSpore(spore, captured, excluded)
  }
          
  def convertSpore21To[T1: ctx.WeakTypeTag,
                       T2: ctx.WeakTypeTag,
                       T3: ctx.WeakTypeTag,
                       T4: ctx.WeakTypeTag,
                       T5: ctx.WeakTypeTag,
                       T6: ctx.WeakTypeTag,
                       T7: ctx.WeakTypeTag,
                       T8: ctx.WeakTypeTag,
                       T9: ctx.WeakTypeTag,
                       T10: ctx.WeakTypeTag,
                       T11: ctx.WeakTypeTag,
                       T12: ctx.WeakTypeTag,
                       T13: ctx.WeakTypeTag,
                       T14: ctx.WeakTypeTag,
                       T15: ctx.WeakTypeTag,
                       T16: ctx.WeakTypeTag,
                       T17: ctx.WeakTypeTag,
                       T18: ctx.WeakTypeTag,
                       T19: ctx.WeakTypeTag,
                       T20: ctx.WeakTypeTag,
                       T21: ctx.WeakTypeTag,
                       R: ctx.WeakTypeTag,
                       C: ctx.WeakTypeTag,
                       E: ctx.WeakTypeTag](ctx: whitebox.Context)(
      spore: ctx.Tree): ctx.Tree = {
    val impl = new MacroModule[ctx.type](ctx)
    val captured = materializeType[ctx.type, C](ctx)
    val excluded = materializeType[ctx.type, E](ctx)
    impl.convertSpore(spore, captured, excluded)
  }
          
  def convertSpore22To[T1: ctx.WeakTypeTag,
                       T2: ctx.WeakTypeTag,
                       T3: ctx.WeakTypeTag,
                       T4: ctx.WeakTypeTag,
                       T5: ctx.WeakTypeTag,
                       T6: ctx.WeakTypeTag,
                       T7: ctx.WeakTypeTag,
                       T8: ctx.WeakTypeTag,
                       T9: ctx.WeakTypeTag,
                       T10: ctx.WeakTypeTag,
                       T11: ctx.WeakTypeTag,
                       T12: ctx.WeakTypeTag,
                       T13: ctx.WeakTypeTag,
                       T14: ctx.WeakTypeTag,
                       T15: ctx.WeakTypeTag,
                       T16: ctx.WeakTypeTag,
                       T17: ctx.WeakTypeTag,
                       T18: ctx.WeakTypeTag,
                       T19: ctx.WeakTypeTag,
                       T20: ctx.WeakTypeTag,
                       T21: ctx.WeakTypeTag,
                       T22: ctx.WeakTypeTag,
                       R: ctx.WeakTypeTag,
                       C: ctx.WeakTypeTag,
                       E: ctx.WeakTypeTag](ctx: whitebox.Context)(
      spore: ctx.Tree): ctx.Tree = {
    val impl = new MacroModule[ctx.type](ctx)
    val captured = materializeType[ctx.type, C](ctx)
    val excluded = materializeType[ctx.type, E](ctx)
    impl.convertSpore(spore, captured, excluded)
  }
}
