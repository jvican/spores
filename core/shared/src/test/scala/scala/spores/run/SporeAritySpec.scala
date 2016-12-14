package scala.spores.run

import scala.spores._

import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(classOf[JUnit4])
class SporeAritySpec {
  @Test
  def nullarySpore(): Unit = {
    val s: NullarySpore[Int] = spore {
      delayed {
        42
      }
    }
    assert(s() == 42)
  }

  @Test
  def spore1(): Unit = {
    val s = spore { (i: Int) =>
      i + 42
    }
    assert(s(1) == 43)
  }

  @Test
  def spore2(): Unit = {
    val s = spore { (i: Int, i2: Int) =>
      42 + i + i2
    }
    assert(s(1, 1) == 44)
  }

  @Test
  def spore3(): Unit = {
    val s = spore { (i: Int, i2: Int, i3: Int) =>
      42 + i + i2 + i3
    }
    assert(s(1, 1, 1) == 45)
  }

  @Test
  def spore4(): Unit = {
    val s = spore { (i: Int, i2: Int, i3: Int, i4: Int) =>
      42 + i + i2 + i3 + i4
    }
    assert(s(1, 1, 1, 1) == 46)
  }

  @Test
  def spore5(): Unit = {
    val s = spore { (i: Int, i2: Int, i3: Int, i4: Int, i5: Int) =>
      42 + i + i2 + i3 + i4 + i5
    }
    assert(s(1, 1, 1, 1, 1) == 47)
  }

  @Test
  def spore6(): Unit = {
    val s = spore { (i: Int, i2: Int, i3: Int, i4: Int, i5: Int, i6: Int) =>
      42 + i + i2 + i3 + i4 + i5 + i6
    }
    assert(s(1, 1, 1, 1, 1, 1) == 48)
  }

  @Test
  def spore7(): Unit = {
    val s = spore {
      (i: Int, i2: Int, i3: Int, i4: Int, i5: Int, i6: Int, i7: Int) =>
        42 + i + i2 + i3 + i4 + i5 + i6 + i7
    }
    assert(s(1, 1, 1, 1, 1, 1, 1) == 49)
  }

  @Test
  def spore8(): Unit = {
    val s = spore {
      (i: Int, i2: Int, i3: Int, i4: Int, i5: Int, i6: Int, i7: Int, i8: Int) =>
        42 + i + i2 + i3 + i4 + i5 + i6 + i7 + i8
    }
    assert(s(1, 1, 1, 1, 1, 1, 1, 1) == 50)
  }

  @Test
  def spore9(): Unit = {
    val s = spore {
      (i: Int, i2: Int, i3: Int, i4: Int, i5: Int, i6: Int, i7: Int, i8: Int, i9: Int) =>
        42 + i + i2 + i3 + i4 + i5 + i6 + i7 + i8 + i9
    }
    assert(s(1, 1, 1, 1, 1, 1, 1, 1, 1) == 51)
  }

  @Test
  def spore10(): Unit = {
    val s = spore {
      (i: Int, i2: Int, i3: Int, i4: Int, i5: Int, i6: Int, i7: Int, i8: Int, i9: Int, i10: Int) =>
        42 + i + i2 + i3 + i4 + i5 + i6 + i7 + i8 + i9 + i10
    }
    assert(s(1, 1, 1, 1, 1, 1, 1, 1, 1, 1) == 52)
  }

  @Test
  def spore11(): Unit = {
    val s = spore {
      (i: Int, i2: Int, i3: Int, i4: Int, i5: Int, i6: Int, i7: Int, i8: Int, i9: Int, i10: Int, i11: Int) =>
        42 + i + i2 + i3 + i4 + i5 + i6 + i7 + i8 + i9 + i10 + i11
    }
    assert(s(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1) == 53)
  }

  @Test
  def spore12(): Unit = {
    val s = spore {
      (i: Int, i2: Int, i3: Int, i4: Int, i5: Int, i6: Int, i7: Int, i8: Int, i9: Int, i10: Int, i11: Int, i12: Int) =>
        42 + i + i2 + i3 + i4 + i5 + i6 + i7 + i8 + i9 + i10 + i11 + i12
    }
    assert(s(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1) == 54)
  }

  @Test
  def spore13(): Unit = {
    val s = spore {
      (i: Int, i2: Int, i3: Int, i4: Int, i5: Int, i6: Int, i7: Int, i8: Int, i9: Int, i10: Int, i11: Int, i12: Int, i13: Int) =>
        42 + i + i2 + i3 + i4 + i5 + i6 + i7 + i8 + i9 + i10 + i11 + i12 + i13
    }
    assert(s(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1) == 55)
  }

  @Test
  def spore14(): Unit = {
    val s = spore {
      (i: Int, i2: Int, i3: Int, i4: Int, i5: Int, i6: Int, i7: Int, i8: Int, i9: Int, i10: Int, i11: Int, i12: Int, i13: Int, i14: Int) =>
        42 + i + i2 + i3 + i4 + i5 + i6 + i7 + i8 + i9 + i10 + i11 + i12 + i13 + i14
    }
    assert(s(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1) == 56)
  }

  @Test
  def spore15(): Unit = {
    val s = spore {
      (i: Int, i2: Int, i3: Int, i4: Int, i5: Int, i6: Int, i7: Int, i8: Int, i9: Int, i10: Int, i11: Int, i12: Int, i13: Int, i14: Int, i15: Int) =>
        42 + i + i2 + i3 + i4 + i5 + i6 + i7 + i8 + i9 + i10 + i11 + i12 + i13 + i14 + i15
    }
    assert(s(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1) == 57)
  }

  @Test
  def spore16(): Unit = {
    val s = spore {
      (i: Int, i2: Int, i3: Int, i4: Int, i5: Int, i6: Int, i7: Int, i8: Int, i9: Int, i10: Int, i11: Int, i12: Int, i13: Int, i14: Int, i15: Int, i16: Int) =>
        42 + i + i2 + i3 + i4 + i5 + i6 + i7 + i8 + i9 + i10 + i11 + i12 + i13 + i14 + i15 + i16
    }
    assert(s(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1) == 58)
  }

  @Test
  def spore17(): Unit = {
    val s = spore {
      (i: Int, i2: Int, i3: Int, i4: Int, i5: Int, i6: Int, i7: Int, i8: Int, i9: Int, i10: Int, i11: Int, i12: Int, i13: Int, i14: Int, i15: Int, i16: Int, i17: Int) =>
        42 + i + i2 + i3 + i4 + i5 + i6 + i7 + i8 + i9 + i10 + i11 + i12 + i13 + i14 + i15 + i16 + i17
    }
    assert(s(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1) == 59)
  }

  @Test
  def spore18(): Unit = {
    val s = spore {
      (i: Int, i2: Int, i3: Int, i4: Int, i5: Int, i6: Int, i7: Int, i8: Int, i9: Int, i10: Int, i11: Int, i12: Int, i13: Int, i14: Int, i15: Int, i16: Int, i17: Int, i18: Int) =>
        42 + i + i2 + i3 + i4 + i5 + i6 + i7 + i8 + i9 + i10 + i11 + i12 + i13 + i14 + i15 + i16 + i17 + i18
    }
    assert(s(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1) == 60)
  }

  @Test
  def spore19(): Unit = {
    val s = spore {
      (i: Int, i2: Int, i3: Int, i4: Int, i5: Int, i6: Int, i7: Int, i8: Int, i9: Int, i10: Int, i11: Int, i12: Int, i13: Int, i14: Int, i15: Int, i16: Int, i17: Int, i18: Int, i19: Int) =>
        42 + i + i2 + i3 + i4 + i5 + i6 + i7 + i8 + i9 + i10 + i11 + i12 + i13 + i14 + i15 + i16 + i17 + i18 + i19
    }
    assert(s(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1) == 61)
  }

  @Test
  def spore20(): Unit = {
    val s = spore {
      (i: Int, i2: Int, i3: Int, i4: Int, i5: Int, i6: Int, i7: Int, i8: Int, i9: Int, i10: Int, i11: Int, i12: Int, i13: Int, i14: Int, i15: Int, i16: Int, i17: Int, i18: Int, i19: Int, i20: Int) =>
        42 + i + i2 + i3 + i4 + i5 + i6 + i7 + i8 + i9 + i10 + i11 + i12 + i13 + i14 + i15 + i16 + i17 + i18 + i19 + i20
    }
    assert(s(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1) == 62)
  }

  @Test
  def spore21(): Unit = {
    val s = spore {
      (i: Int, i2: Int, i3: Int, i4: Int, i5: Int, i6: Int, i7: Int, i8: Int, i9: Int, i10: Int, i11: Int, i12: Int, i13: Int, i14: Int, i15: Int, i16: Int, i17: Int, i18: Int, i19: Int, i20: Int, i21: Int) =>
        42 + i + i2 + i3 + i4 + i5 + i6 + i7 + i8 + i9 + i10 + i11 + i12 + i13 + i14 + i15 + i16 + i17 + i18 + i19 + i20 + i21
    }
    assert(s(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1) == 63)
  }

  @Test
  def spore22(): Unit = {
    val s = spore {
      (i: Int, i2: Int, i3: Int, i4: Int, i5: Int, i6: Int, i7: Int, i8: Int, i9: Int, i10: Int, i11: Int, i12: Int, i13: Int, i14: Int, i15: Int, i16: Int, i17: Int, i18: Int, i19: Int, i20: Int, i21: Int, i22: Int) =>
        42 + i + i2 + i3 + i4 + i5 + i6 + i7 + i8 + i9 + i10 + i11 + i12 + i13 + i14 + i15 + i16 + i17 + i18 + i19 + i20 + i21 + i22
    }
    assert(s(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1) == 64)
  }
}
