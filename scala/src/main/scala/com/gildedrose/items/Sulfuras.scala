package com.gildedrose.items

/** WoW hammer - doesn't change with time.
 *
 * @param sellIn  has no semantics in the context of this item.
 * @param quality perceived quality of an item.
 */
class Sulfuras(sellIn: Int, quality: Int) extends InternalItem(InternalItem.ItemName.SULFURAS, sellIn, quality) {
  /** Sulfuras doesn't change over time.
   *
   * @return same unchanged instance.
   */
  override def update(): InternalItem = this
}
