package com.gildedrose.items

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import com.gildedrose.items.MiscellaneousItem

class MiscellaneousItemTest extends AnyFlatSpec with Matchers {
  "Misc item" should "decrease sell-in on every update" in {
    for { sellIn <- List(20, 10, 4, 1, 0, -2) } {
      val misc = MiscellaneousItem(name = "Test item", quality = 30, sellIn = sellIn)
      val aged = misc.update()

      aged.sellIn should be(misc.sellIn - 1)
    }
  }

  "Misc item" should "not drop value below 0" in {
    val misc = MiscellaneousItem("Test item", quality = 0, sellIn = 20)
    val aged = misc.update()

    aged.quality should be(0)
  }

  "Misc item before expiration" should "decrease value by 1" in {
    val misc = MiscellaneousItem("Test", quality = 20, sellIn = 2)

    misc.update().quality should be(misc.quality - 1)
    misc.update().update().quality should be(misc.quality - 2)
    misc.update().update().update() should not be(misc.quality - 3)
  }

  "Misc item after expiration" should "decrease value by 2" in {
    val misc = MiscellaneousItem("Test", quality = 20, sellIn = 0)

    misc.update().quality should be(misc.quality - 2)
    misc.update().update().quality should be(misc.quality - 4)
  }
}
