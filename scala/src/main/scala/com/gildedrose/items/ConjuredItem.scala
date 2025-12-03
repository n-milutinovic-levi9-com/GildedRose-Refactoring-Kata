package com.gildedrose.items

object ConjuredItem {
  import com.gildedrose.registry.ConverterRegistry.{Matcher, Converter}
  import com.gildedrose.Item

  /** Prefix used to identify conjured items */
  val PREFIX = "Conjured "
  /** Matcher for conjured items, uses prefix */
  val MATCHER: Matcher = (name: String) => name.startsWith(PREFIX)
    /** Converter for conjured items */
  val CONVERTER: Converter = (item: Item) => ConjuredItem(item.name, item.sellIn, item.quality)
}

class ConjuredItem(name: String, sellIn: Int, quality: Int) extends InternalItem[ConjuredItem](name, sellIn, quality) {
  override def update(): ConjuredItem = {
    this
      .decreaseQuality()
      .decreaseQuality()
      .decreaseSellIn()
  }

  override protected def build(newSellIn: Int, newQuality: Int): ConjuredItem = ConjuredItem(name, newSellIn, newQuality)
}
