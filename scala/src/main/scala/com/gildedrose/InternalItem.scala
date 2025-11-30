package com.gildedrose

/** Utility object for converting Items to and from internal form. */
object InternalItem {
  /** Known item names */
  object ItemName {
    /** Aged Brie - gets better with time */
    val BRIE = "Aged Brie"
    /** Backstage pass - gets more valuable as the concert draws near */
    val BACKSTAGE = "Backstage passes to a TAFKAL80ETC concert"
    /** Sulfuras - doesn't change, no matter how much time passes. */
    val SULFURAS = "Sulfuras, Hand of Ragnaros"
  }

  /** Convert Item DTO to the internal representation.
   *
   * @param item
   * @return
   */
  def apply(item: Item): InternalItem = {
    item match {
      case Item(ItemName.BRIE, sellIn, quality) => new AgedBrie(sellIn, quality)
      case Item(ItemName.BACKSTAGE, sellIn, quality) => new BackstagePass(sellIn, quality)
      case Item(ItemName.SULFURAS, sellIn, quality) => new Sulfuras(sellIn, quality)
      case Item(name, sellIn, quality) => new MiscellaneousItem(name, sellIn, quality)
    }
  }

  /** Convert internal item to DTO form.
   *
   * @param internal internal item to be exported.
   * @return (external) DTO item.
   */
  def exportItem(internal: InternalItem): Item = Item(internal.name, internal.sellIn, internal.quality)

  /** Maximum quality some items can reach */
  val MAX_QUALITY = 50
}

/** Base for all items.
 *
 * @param name item name, can be used as a discriminator.
 * @param sellIn how many days from now this item should be sold or is valid.
 * @param quality perceived quality of an item.
 */
abstract case class InternalItem(name: String, sellIn: Int, quality: Int) {
  def update(): InternalItem
}

/** Aged Brie - gets better with time.
 *
 * @param sellIn how many days from now this item reaches full maturity.
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

/** Backstage Pass constants. */
object BackstagePass {
  /** Days to the concert when this pass becomes more interesting. */
  val FIRST_CALL_SELL_IN = 11
  /** Days to the concert when this pass becomes fascinating. */
  val LAST_CALL_SELL_IN = 6
}

/** Backstage pass - closer to the concert, the more valuable it is.
 *
 * @param sellIn how many days from now this item should be sold or is valid.
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

/** WoW hammer - doesn't change with time.
 *
 * @param sellIn has no semantics in the context of this item.
 * @param quality perceived quality of an item.
 */
class Sulfuras(sellIn: Int, quality: Int) extends InternalItem(InternalItem.ItemName.SULFURAS, sellIn, quality) {
  override def update(): InternalItem = this
}

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
