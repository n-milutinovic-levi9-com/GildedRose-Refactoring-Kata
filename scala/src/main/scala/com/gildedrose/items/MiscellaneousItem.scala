package com.gildedrose.items

/** All other items.
 *
 * The quality of these items drops daily till it reaches 0.
 *
 * @param name item name.
 * @param sellIn how many days from now this item should be sold or is valid.
 * @param quality perceived quality of an item.
 */
class MiscellaneousItem(name: String, sellIn: Int, quality: Int) extends InternalItem(name, sellIn, quality) {
  override def update(): InternalItem = {
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
}