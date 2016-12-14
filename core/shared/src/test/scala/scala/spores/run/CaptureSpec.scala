package scala.spores.run

import scala.spores._

import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(classOf[JUnit4])
class CaptureSpec {
  @Test
  def `detect captured variables in nullary spore with capture`(): Unit = {
    val helloWorld = "Hello World"
    val s: NullarySpore[String] { type Captured = String } = spore { () =>
      capture(helloWorld)
    }
    assert(s() == helloWorld)
  }

  @Test
  def `detect captured variables in spore with capture`(): Unit = {
    val outsider = 2
    val s: Spore[Int, Int] { type Captured = Int } = spore { (i: Int) =>
      capture(outsider) + i
    }
    assert(s(10) == 12)
  }

  @Test
  def `detect captured variables in spore2 with capture`(): Unit = {
    val outsider = 2
    val s: Spore2[Int, Int, Int] { type Captured = Int } = spore {
      (i: Int, i2: Int) =>
        capture(outsider) + i + i2
    }
    assert(s(1, 2) == 5)
  }

  @Test
  def `detect captured variables in spore3 with capture`(): Unit = {
    val outsider = 2
    val s: Spore3[Int, Int, Int, Int] { type Captured = Int } = spore {
      (i: Int, i2: Int, i3: Int) =>
        capture(outsider) + i + i2 + i3
    }
    assert(s(1, 2, 3) == 8)
  }

  @Test
  def `detect captured variables in spore4 with capture`(): Unit = {
    val outsider = 2
    val s: Spore4[Int, Int, Int, Int, Int] { type Captured = Int } = spore {
      (i: Int, i2: Int, i3: Int, i4: Int) =>
        capture(outsider) + i + i2 + i3 + i4
    }
    assert(s(1, 2, 3, 4) == 12)
  }

  @Test
  def `detect captured variables in spore5 with capture`(): Unit = {
    val outsider = 2
    val s: Spore5[Int, Int, Int, Int, Int, Int] { type Captured = Int } =
      spore { (i: Int, i2: Int, i3: Int, i4: Int, i5: Int) =>
        capture(outsider) + i + i2 + i3 + i4 + i5
      }
    assert(s(1, 2, 3, 4, 5) == 17)
  }

  @Test
  def `detect captured variables in spore6 with capture`(): Unit = {
    val outsider = 2
    val s: Spore6[Int, Int, Int, Int, Int, Int, Int] { type Captured = Int } =
      spore { (i: Int, i2: Int, i3: Int, i4: Int, i5: Int, i6: Int) =>
        capture(outsider) + i + i2 + i3 + i4 + i5 + i6
      }
    assert(s(1, 2, 3, 4, 5, 6) == 23)
  }
}
