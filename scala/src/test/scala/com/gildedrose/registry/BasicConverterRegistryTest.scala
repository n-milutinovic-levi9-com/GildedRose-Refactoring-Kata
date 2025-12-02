package com.gildedrose.registry

import com.gildedrose.items.{InternalItem, MiscellaneousItem}
import com.gildedrose.Item
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import scala.language.postfixOps

class BasicConverterRegistryTest extends AnyFlatSpec with Matchers {
  private val testName = "Test item"
  private val testSellIn = 10
  private val testQuality = 20
  private val defaultConverter = (item: Item) => MiscellaneousItem(item.name, item.sellIn, item.quality)

  "Empty registry" should "have the default entry" in {
    val registry = ConverterRegistry(defaultConverter)

    val myItem = registry.convert(Item(testName, testSellIn, testQuality))
    myItem shouldBe an [InternalItem]
    myItem.name should be (testName)
    myItem.sellIn should be (testSellIn)
    myItem.quality should be (testQuality)
  }

  "Adding a converter" should "have precedence over default converter" in {
    class TestItem extends InternalItem(testName, testSellIn, testQuality) {
      override def update(): InternalItem = TestItem()
    }
    val matcher = (name: String) => name == testName
    val converter = (item: Item) => TestItem()
    val registry = ConverterRegistry(defaultConverter)
    registry.register(matcher, converter)

    val myItem = registry.convert(Item(testName, testSellIn, testQuality))
    myItem shouldBe a [TestItem]
    myItem.name should be (testName)
    myItem.sellIn should be(testSellIn)
    myItem.quality should be(testQuality)

    val miscItem = registry.convert(Item(testName + " not", testSellIn + 1, testQuality + 1))
    miscItem shouldBe a [MiscellaneousItem]
    miscItem.name should be (testName + " not")
    miscItem.sellIn should be (testSellIn + 1)
    miscItem.quality should be (testQuality + 1)
  }
}
