package com.gildedrose.items

/** Aged Brie - gets better with time.
 *
 * @param sellIn  how many days from now this item reaches full maturity.
 * @param quality perceived quality of an item.
 */
class AgedBrie(sellIn: Int, quality: Int) extends InternalItem(InternalItem.ItemName.BRIE, sellIn, quality) {
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
