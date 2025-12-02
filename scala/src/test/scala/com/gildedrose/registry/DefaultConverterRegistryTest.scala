package com.gildedrose.registry

import com.gildedrose.Item
import com.gildedrose.items.{AgedBrie, BackstagePass, InternalItem, Sulfuras, MiscellaneousItem}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class DefaultConverterRegistryTest extends AnyFlatSpec with Matchers {
  private val sellIn = 20
  private val quality = 30

  "Default converter registry" should "have Aged Brie converter" in {
    val item = Item(AgedBrie.NAME, sellIn, quality)
    val result = ConverterRegistry.default.convert(item)

    result shouldBe an [AgedBrie]
    result.name should be (AgedBrie.NAME)
    result.sellIn should be (sellIn)
    result.quality should be (quality)
  }

  it should "have Backstage Pass converter" in {
    val item = Item(BackstagePass.NAME, sellIn, quality)
    val converted = ConverterRegistry.default.convert(item)

    converted shouldBe a [BackstagePass]
    converted.name should be (BackstagePass.NAME)
    converted.sellIn should be (sellIn)
    converted.quality should be (quality)
  }

  it should "have Sulfuras converter" in {
    val item = Item(Sulfuras.NAME, sellIn, quality)
    val converted = ConverterRegistry.default.convert(item)

    converted shouldBe a [Sulfuras]
    converted.name should be (Sulfuras.NAME)
    converted.sellIn should be (sellIn)
    converted.quality should be (quality)
  }

  it should "accept client's converter" in {
    val name = "Test item"
    class TestItem(sellIn: Int, quality: Int) extends InternalItem[TestItem](name, sellIn, quality) {
      override def update(): TestItem = this

      override protected def build(newSellIn: Int, newQuality: Int): TestItem = TestItem(newQuality, newQuality)
    }
    ConverterRegistry.default.register(_ == name, (item: Item) => TestItem(item.sellIn, item.quality))

    val item = Item(name, sellIn, quality)
    val converted = ConverterRegistry.default.convert(item)

    converted shouldBe a [TestItem]
    converted.name should be (name)
    converted.sellIn should be (sellIn)
    converted.quality should be (quality)
  }

  it should "treat unmatched items as Miscellaneous Items" in {
    val unmatchedName = "Some really odd item"
    val item = Item(unmatchedName, sellIn, quality)
    val converted = ConverterRegistry.default.convert(item)

    converted shouldBe a [MiscellaneousItem]
    converted.name should be (unmatchedName)
    converted.sellIn should be (sellIn)
    converted.quality should be (quality)
  }
}
