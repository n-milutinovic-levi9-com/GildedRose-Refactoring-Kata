package com.gildedrose.items

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import com.gildedrose.Item
import com.gildedrose.registry.ConverterRegistry

class ConjuredItemTest extends AnyFlatSpec with Matchers {
  val testName = "Conjured Mana Cake"
  val testSellIn = 20
  val testQuality = 30

  "Default registry" should "recognize conjured items" in {
    val item = Item(testName, testSellIn, testQuality)
    val converted = ConverterRegistry.default.convert(item)

    converted shouldBe a [ConjuredItem]
    converted.name should be (testName)
    converted.sellIn should be (testSellIn)
    converted.quality should be (testQuality)
  }

  "Conjured item" should "drop quality 2x the regular item" in {
    val conjured = ConjuredItem(testName, testSellIn, testQuality)
    val updated = conjured.update()

    updated.name should be (conjured.name)
    updated.sellIn should be (conjured.sellIn - 1)
    updated.quality should be (conjured.quality - 2)
  }
}
