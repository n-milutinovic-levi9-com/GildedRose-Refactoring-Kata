package com.gildedrose.items

import com.gildedrose.items.AgedBrie
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class AgedBrieTest extends AnyFlatSpec with Matchers {
  "Aged Brie" should "not increase quality beyond 50" in {
    val brie = AgedBrie(quality = 50, sellIn = 10)

    val agedBrie = brie.update()
    agedBrie.quality should be(50)

    val reallyAgedBrie = agedBrie.update()
    reallyAgedBrie.quality should be(50)
  }

  "Aged brie in sell-in period" should "increase quality by 1" in {
    val brie = AgedBrie(quality = 40, sellIn = 10)

    val agedBrie = brie.update()
    agedBrie.quality should be(brie.quality + 1)
  }

  "Aged brie outside sell-in period" should "increase quality by 2" in {
    val brie = AgedBrie(quality = 40, sellIn = -5)

    val agedBrie = brie.update()
    agedBrie.quality should be(brie.quality + 2)
  }

  "Aged brie outside sell-in period" should "increase quality by 2, but not over 50" in {
    val brie = AgedBrie(quality = 49, sellIn = -5)

    val agedBrie = brie.update()
    agedBrie.quality should be(50)
  }

  "Aged brie" should "decrease its sell-in period" in {
    for { sellIn <- List(5, 1, 0, -4) } {
      val brie = AgedBrie(quality = 30, sellIn = sellIn)
      val agedBrie = brie.update()

      agedBrie.sellIn should be(brie.sellIn - 1)
    }
  }
}
