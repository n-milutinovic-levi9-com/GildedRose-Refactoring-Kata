package com.gildedrose.items

/** Backstage Pass constants. */
object BackstagePass {
  import com.gildedrose.registry.ConverterRegistry.{Converter, Matcher}
  import com.gildedrose.Item

  /** Days to the concert when this pass becomes more interesting. */
  val FIRST_CALL_SELL_IN = 11
  /** Days to the concert when this pass becomes fascinating. */
  val LAST_CALL_SELL_IN = 6
  /** Item name */
  val NAME = "Backstage passes to a TAFKAL80ETC concert"
  /** Item name matcher */
  val MATCHER: Matcher = (name: String) => name == NAME
  /** Converter */
  val CONVERTER: Converter = (item: Item) => BackstagePass(item.sellIn, item.quality)
}

/** Backstage pass - closer to the concert, the more valuable it is.
 *
 * @param sellIn  how many days from now this item should be sold or is valid.
 * @param quality perceived quality of an item.
 */
class BackstagePass(sellIn: Int, quality: Int) extends InternalItem[BackstagePass](BackstagePass.NAME, sellIn, quality) {
  private def updateFirstCall(): BackstagePass = if (sellIn < BackstagePass.FIRST_CALL_SELL_IN)
      increaseQuality()
    else
      this

  private def updateLastCall(): BackstagePass = if (sellIn < BackstagePass.LAST_CALL_SELL_IN)
      increaseQuality()
    else
      this

  private def expirePass(): BackstagePass = if (sellIn < 0)
    build(sellIn, 0)
  else
    this

  override def update(): BackstagePass = increaseQuality()
      .updateFirstCall()
      .updateLastCall()
      .decreaseSellIn()
      .expirePass()

  override protected def build(newSellIn: Int, newQuality: Int): BackstagePass = BackstagePass(newSellIn, newQuality)
}