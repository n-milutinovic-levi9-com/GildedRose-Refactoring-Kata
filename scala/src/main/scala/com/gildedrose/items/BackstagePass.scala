package com.gildedrose.items

/** Backstage Pass constants. */
object BackstagePass {
  /** Days to the concert when this pass becomes more interesting. */
  val FIRST_CALL_SELL_IN = 11
  /** Days to the concert when this pass becomes fascinating. */
  val LAST_CALL_SELL_IN = 6
}

/** Backstage pass - closer to the concert, the more valuable it is.
 *
 * @param sellIn  how many days from now this item should be sold or is valid.
 * @param quality perceived quality of an item.
 */
class BackstagePass(sellIn: Int, quality: Int) extends InternalItem(InternalItem.ItemName.BACKSTAGE, sellIn, quality) {
  override def update(): InternalItem = {
    var newQuality = quality
    var newSellIn = sellIn

    if (newQuality < InternalItem.MAX_QUALITY) {
      newQuality = newQuality + 1

      if (newSellIn < BackstagePass.FIRST_CALL_SELL_IN) {
        if (newQuality < InternalItem.MAX_QUALITY) {
          newQuality = newQuality + 1
        }
      }

      if (newSellIn < BackstagePass.LAST_CALL_SELL_IN) {
        if (newQuality < InternalItem.MAX_QUALITY) {
          newQuality = newQuality + 1
        }
      }
    }
    newSellIn = newSellIn - 1

    if (newSellIn < 0) {
      newQuality = 0
    }
    BackstagePass(newSellIn, newQuality)
  }
}