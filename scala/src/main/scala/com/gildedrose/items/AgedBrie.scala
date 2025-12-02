package com.gildedrose.items

import com.gildedrose.Item

object AgedBrie {
  import com.gildedrose.registry.ConverterRegistry.{Matcher, Converter}

  /** Name */
  val NAME = "Aged Brie"
  /** Matcher for the converter registry. */
  val MATCHER: Matcher = (name: String) => name == NAME
  /** Converter */
  val CONVERTER: Converter = (item: Item) => AgedBrie(item.sellIn, item.quality)
}

/** Aged Brie - gets better with time.
 *
 * @param sellIn  how many days from now this item reaches full maturity.
 * @param quality perceived quality of an item.
 */
class AgedBrie(sellIn: Int, quality: Int) extends InternalItem(AgedBrie.NAME, sellIn, quality) {
  override def update(): InternalItem = {
    var newQuality = quality
    var newSellIn = sellIn

    if (newQuality < InternalItem.MAX_QUALITY) {
      newQuality = newQuality + 1
    }
    newSellIn = newSellIn - 1

    if (newSellIn < 0) {
      if (newQuality < InternalItem.MAX_QUALITY) {
        newQuality = newQuality + 1
      }
    }
    AgedBrie(newSellIn, newQuality)
  }
}

