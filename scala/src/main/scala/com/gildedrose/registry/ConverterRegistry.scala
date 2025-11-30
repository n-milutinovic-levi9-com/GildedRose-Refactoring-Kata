package com.gildedrose.registry

import com.gildedrose.{InternalItem, Item}

import scala.collection.mutable.Seq

object ConverterRegistry {
  /** Function used to match the name to the converter */
  type Matcher = String => Boolean
  /** Function used to convert `Item` to `InternalItem` */
  type Converter = Item => InternalItem
}

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