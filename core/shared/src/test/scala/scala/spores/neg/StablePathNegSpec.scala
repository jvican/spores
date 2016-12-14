package scala.spores.neg

import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

import scala.spores.TestUtil._
import scala.spores.util.Feedback

@RunWith(classOf[JUnit4])
class StablePathNegSpec {
  @Test
  def `blocks aren't stable`() {
    val predef =
      if (scala.util.Properties.versionString.contains("2.12"))
        "scala.Predef"
      else "scala.this.Predef"
    val errorMsg =
      s"""{
        |  def x: Nothing = $predef.???;
        |  a
        |}""".stripMargin
    expectError(Feedback.InvalidOuterReference(errorMsg)) {
      """
        import scala.spores._
        val a = 12
        val s: Spore[Int, Unit] = spore {
          (x: Int) => capture({def x = ??? ; a })
        }
      """
    }
  }

  @Test
  def `only allowed to capture paths 1`() {
    expectError(Feedback.InvalidOuterReference("compute(2)")) {
      """
        import scala.spores._
        def compute(x: Int): Int = x * 5
        val s: Spore[Int, String] = spore { (x: Int) =>
          val cc1 = capture(compute(2))
          s"arg: $x, cc1: $cc1"
        }
      """
    }
  }

  @Test
  def `only allowed to capture paths 2`() {
    expectError(Feedback.InvalidOuterReference("v1")) {
      """
        import scala.spores._
        // this is a var:
        var v1: Int = 10
        val s: Spore[Int, String] = spore { (x: Int) =>
          val cc1 = capture(v1)
          s"arg: $x, cc1: $cc1"
        }
      """
    }
  }

  @Test
  // TODO(jvican): Fix in order to allow capturing literals
  def `1 isn't a stable path`() {
    expectError(Feedback.InvalidOuterReference("1")) {
      """
        import scala.spores._
        val s: Spore[Int, String] = spore { (x: Int) =>
          val capt = capture(1)
          s"$capt"
        }
      """
    }
  }

  @Test
  def `can't ascribe types in a stable path`() {
    expectError(Feedback.InvalidOuterReference("(v: Any)")) {
      """
        import scala.spores._
        val v = 10
        val s: Spore[Int, String] = spore { (x: Int) =>
          val capt = capture(v: Any)
          s"$capt"
        }
      """
    }
  }
}
