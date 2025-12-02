package com.gildedrose.items

/** Base class for all internal items.
 *
 * This case class mirrors the external (DTO) item. It has the same properties as DTO
 * item. Those properties will be copied when the item is exported.
 *
 * @param name    item name, can be used as a discriminator.
 * @param sellIn  how many days from now this item should be sold or is valid.
 * @param quality perceived quality of an item.
 */
abstract case class InternalItem(name: String, sellIn: Int, quality: Int) {
  /** Update item properties after a day */
  def update(): InternalItem
}

/** Utility object for converting Items to and from internal form. */
object InternalItem {
  /** Maximum quality some items can reach */
  val MAX_QUALITY = 50
}