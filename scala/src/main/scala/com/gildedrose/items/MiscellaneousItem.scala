package com.gildedrose.items

import com.gildedrose.Item

object MiscellaneousItem {
  import com.gildedrose.registry.ConverterRegistry.{Converter, Matcher}

  /** Mather matching everything */
  val MATCHER: Matcher = (name: String) => true
  /** Converter */
  val CONVERTER: Converter = (item: Item) => MiscellaneousItem(item.name, item.sellIn, item.quality)
}

/** All other items.
 *
 * The quality of these items drops daily till it reaches 0.
 *
 * @param name item name.
 * @param sellIn how many days from now this item should be sold or is valid.
 * @param quality perceived quality of an item.
 */
class MiscellaneousItem(name: String, sellIn: Int, quality: Int) extends InternalItem[MiscellaneousItem](name, sellIn, quality) {
  override def update(): MiscellaneousItem = {
    var newQuality = quality
    var newSellIn = sellIn

    if (newQuality > 0) {
      newQuality = newQuality - 1
    }

    newSellIn = newSellIn - 1

    if (newSellIn < 0) {
      if (newQuality > 0) {
        newQuality = newQuality - 1
      }
    }
    MiscellaneousItem(name, newSellIn, newQuality)
  }

  override protected def build(newSellIn: Int, newQuality: Int): MiscellaneousItem = MiscellaneousItem(name, newSellIn, newQuality)
}