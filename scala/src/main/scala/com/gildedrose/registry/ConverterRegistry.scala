package com.gildedrose.registry

import com.gildedrose.InternalItem.ItemName
import com.gildedrose.{AgedBrie, BackstagePass, InternalItem, Item, MiscellaneousItem, Sulfuras}

import scala.collection.mutable.Seq

object ConverterRegistry {
  /** Function used to match the name to the converter */
  type Matcher = String => Boolean
  /** Function used to convert `Item` to `InternalItem` */
  type Converter = Item => InternalItem

  val default = ConverterRegistry((item: Item) => MiscellaneousItem(item.name, item.sellIn, item.quality))
  default.register(_ == ItemName.BRIE, (item: Item) => AgedBrie(item.sellIn, item.quality))
  default.register(_ == ItemName.BACKSTAGE, (item: Item) => BackstagePass(item.sellIn, item.quality))
  default.register(_ == ItemName.SULFURAS, (item: Item) => Sulfuras(item.sellIn, item.quality))
}

/** Converter registry for Item -> InternalItem
 *
 * Clients of this class can submit a public DTO Item to be converted into the `InternalItem`.
 * The registry holds any number of converters from `Item` to `InternalItem`. The appropriate
 * converter is chosen using a matcher supplied when the converter is registered. The matcher
 * is matching on item name.
 *
 * If no match can be made, the default converter is used.
 *
 * @param defaultConverter converter to be used when no matchers apply to the given name.
 */
class ConverterRegistry(private val defaultConverter: ConverterRegistry.Converter) {
  private var registry = Set.empty[(ConverterRegistry.Matcher, ConverterRegistry.Converter)]
  private val defaultEntry = ((_: String) => true, defaultConverter)

  /** Register a converter with a matcher.
   *
   * A matcher is a predicate selecting a converter by the name of the Item. Such a matcher
   * can utilize a RegEx to match on a name. A converter will take an Item and generate the
   * required subtype of `InternalItem`.
   *
   * @param matcher matcher for the converter.
   * @param converter converter Item -> InternalItem.
   */
  def register(matcher: ConverterRegistry.Matcher, converter: ConverterRegistry.Converter): Unit = {
    registry = registry + Tuple2(matcher, converter)
  }

  def convert(item: Item): InternalItem = {
    registry
      .find(entry => entry._1(item.name))
      .getOrElse(defaultEntry)
      ._2(item)
  }
}