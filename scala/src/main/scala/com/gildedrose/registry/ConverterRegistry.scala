package com.gildedrose.registry

import com.gildedrose.items.{AgedBrie, BackstagePass, InternalItem, Sulfuras}
import com.gildedrose.Item
import com.gildedrose.items.MiscellaneousItem

import scala.collection.mutable.Seq

object ConverterRegistry {
  /** Function used to match the name to the converter */
  type Matcher = String => Boolean
  /** Function used to convert `Item` to `InternalItem` */
  type Converter = Item => InternalItem

  /** The default converter registry, holding all known types of items. */
  val default: ConverterRegistry = ConverterRegistry((item: Item) => MiscellaneousItem(item.name, item.sellIn, item.quality))
    .register(_ == AgedBrie.NAME, (item: Item) => AgedBrie(item.sellIn, item.quality))
    .register(_ == BackstagePass.NAME, (item: Item) => BackstagePass(item.sellIn, item.quality))
    .register(_ == Sulfuras.NAME, (item: Item) => Sulfuras(item.sellIn, item.quality))
}

/** Converter registry for Item -> InternalItem
 *
 * Clients of this class can submit a public DTO Item to be converted into the `InternalItem`.
 * The registry holds any number of converters from `Item` to `InternalItem`. The appropriate
 * converter is chosen using a matcher supplied when the converter is registered. The matcher
 * is matching on item name.
 *
 * CAUTION: If several matchers overlap in their positive results, the first one picked will
 * win. There is no clear order on how matchers are iterated on.
 *
 * If no match can be made, the default converter is used.
 *
 * @param defaultConverter converter to be used when no matchers apply to the given name.
 */
class ConverterRegistry(private val defaultConverter: ConverterRegistry.Converter) {
  private var registry = Map.empty[ConverterRegistry.Matcher, ConverterRegistry.Converter]
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
  def register(matcher: ConverterRegistry.Matcher, converter: ConverterRegistry.Converter): ConverterRegistry = {
    registry = registry + (matcher -> converter)
    this
  }

  /** Unregister a converter.
   *
   * This method may be tricky, since the key for a converter is a `Matcher`. Whoever registers
   * a converter must keep the reference to the matcher, to be able to remove the converter.
   *
   * @param matcher matcher that is a key to the converter.
   */
  def unregister(matcher: ConverterRegistry.Matcher): ConverterRegistry = {
    registry = registry - matcher
    this
  }

  /** Unregister converter(s) matching a given name.
   *
   * This method will filter out all converter whose matchers trigger on the given name. If there
   * are multiple matcher that trigger, all will be removed.
   *
   * @param name item name used to filter out converter(s).
   */
  def unregister(name: String): ConverterRegistry = {
    registry = registry.view.filterKeys(! _.apply(name)).toMap
    this
  }

  /** Convert DTO Item to internal form.
   *
   * @param item DTO (public API) item to convert.
   * @return internal item converted from DTO form.
   */
  def convert(item: Item): InternalItem = {
    registry.keySet
      .find(_.apply(item.name))
      .map(registry(_))
      .getOrElse(defaultConverter)
      .apply(item)
  }
}