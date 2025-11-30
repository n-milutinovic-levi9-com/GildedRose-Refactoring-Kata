package com.gildedrose

import registry.ConverterRegistry

class GildedRose(val items: Array[Item]) {


  def updateQuality() : Unit = {
    for (item <- items) {
      val internal = ConverterRegistry.default.convert(item)
      val updated = internal.update()
      item.quality = updated.quality
      item.sellIn = updated.sellIn
    }
  }
}
