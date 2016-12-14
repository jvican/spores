package scala.spores.run

import scala.spores._

import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(classOf[JUnit4])
class NullaryUnitSporeComparison {

  /**
    * Similar to pTTCapture(), but spores are nested.
    */
  @Test
  def nestedPTTCapture(): Unit = {
    val s = spore { (_: Unit) =>
      {
        spore { (_: Unit) =>
          ()
        }
        ()
      }
    }
  }
  /**
    * same as nestedPTTCapture, but (_: Unit) => ... replaced with Nullary spore
    */
//  @Test
//  def nestedPTTCaptureNullary(): Unit = {
//    val s = spore{
//      delayed {
//        spore {
//          (_: Unit) => ()
//        }
//        ()
//      }
//    }
//  }
}
