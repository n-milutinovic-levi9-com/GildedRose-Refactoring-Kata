package com.gildedrose.registry

import com.gildedrose.items.{InternalItem, MiscellaneousItem}
import com.gildedrose.Item
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatest.BeforeAndAfter
import scala.compiletime.uninitialized

class UnregisterConverterRegistryTest extends AnyFlatSpec with Matchers with BeforeAndAfter {
  private val testName = "Test Item"
  private val testSellIn = 20
  private val testQuality = 30

  private class TestItem(sellIn: Int, quality: Int) extends InternalItem(testName, sellIn, quality) {
    override def update(): InternalItem = this
  }

  private var registry: ConverterRegistry = uninitialized
  private val matcher = (name: String) => name == testName
  private val item = Item(testName, testSellIn, testQuality)

  before {
    registry =  ConverterRegistry((item: Item) => MiscellaneousItem(item.name, item.sellIn, item.quality))
    registry.register(matcher, (item: Item) => TestItem(item.sellIn, item.quality))
  }

  "Test converter registry" should "be setup properly" in {
    val converted = registry.convert(item)

    converted shouldBe a[TestItem]
    converted.name should be(testName)
    converted.sellIn should be(testSellIn)
    converted.quality should be(testQuality)
  }

  "Converter Registry" should "allow to unregister via matcher" in {
    registry.unregister(matcher)
    val converted = registry.convert(item)

    converted shouldBe a [MiscellaneousItem]
    converted.name should be (testName)
    converted.sellIn should be(testSellIn)
    converted.quality should be(testQuality)
  }

  it should "correctly unregister via item name" in {
    registry.unregister(testName)
    val converted = registry.convert(item)

    converted shouldBe a [MiscellaneousItem]
    converted.name should be(testName)
    converted.sellIn should be(testSellIn)
    converted.quality should be(testQuality)
  }
}
