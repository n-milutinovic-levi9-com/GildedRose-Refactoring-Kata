package com.gildedrose.items

object Sulfuras {
  import com.gildedrose.registry.ConverterRegistry.{Converter, Matcher}
  import com.gildedrose.Item

  /** Item name */
  val NAME = "Sulfuras, Hand of Ragnaros"
  /** Matcher by name */
  val MATCHER: Matcher = (name: String) => name == NAME
  /** Converter */
  val CONVERTER: Converter = (item: Item) => Sulfuras(item.sellIn, item.quality)
}
/** WoW hammer - doesn't change with time.
 *
 * @param sellIn  has no semantics in the context of this item.
 * @param quality perceived quality of an item.
 */
class Sulfuras(sellIn: Int, quality: Int) extends InternalItem(Sulfuras.NAME, sellIn, quality) {
  /** Sulfuras doesn't change over time.
   *
   * @return same unchanged instance.
   */
  override def update(): InternalItem = this
}
