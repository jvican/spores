package scala.spores.neg

import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

import scala.spores.TestUtil._
import scala.spores.util.Feedback

@RunWith(classOf[JUnit4])
class ExcludedNegSpec {
  @Test
  def `nullary spores respect Excluded`(): Unit = {
    expectError(Feedback.InvalidReferenceToExcludedType("String")) {
      """
        import scala.spores._
        val s: NullarySpore[Unit] {type Excluded = String} = spore {
          val s = "hej"
          delayed {
            ()
          }
        }
      """
    }
  }

  @Test
  def `nullary spores respect types inside composed Excluded`(): Unit = {
    expectError(Feedback.InvalidReferenceToExcludedType("String")) {
      """
        import scala.spores._
        val s: NullarySpore[Unit] {type Excluded = (String, Int)} = spore {
          val s = "hej"
          delayed {
            ()
          }
        }
      """
    }
  }

  @Test
  def `capture syntax respects the Excluded type`(): Unit = {
    expectError(Feedback.InvalidReferenceToExcludedType("String")) {
      """
        import scala.spores._
        val msg: String = "Hello World"
        val s: NullarySpore[Unit] {type Excluded = String} = spore {
          delayed {
            println(capture(msg))
          }
        }
      """
    }
  }

  @Test
  def `capture syntax respects complex Excluded type`(): Unit = {
    expectError(Feedback.InvalidReferenceToExcludedType("Int")) {
      s"""
        import scala.spores._
        val s1: Spore[Int, String] = (i: Int) => i.toString
        val i = 2
        val s2: NullarySpore[Unit] {type Excluded = Int} = spore {
          delayed {
            println(capture(s1)(capture(i)))
          }
        }
      """
    }
  }

  @Test
  def `capture syntax respects complex Excluded type II`(): Unit = {
    val sporeType = "scala.spores.Spore[Int,String]"
    expectError(Feedback.InvalidReferenceToExcludedType(sporeType)) {
      s"""
        import scala.spores._
        val s1: Spore[Int, String] = (i: Int) => i.toString
        val i = 2
        val s2: NullarySpore[Unit] {type Excluded = Spore[Int, String]} = spore {
          delayed {
            println(capture(s1)(capture(i)))
          }
        }
      """
    }
  }
}
