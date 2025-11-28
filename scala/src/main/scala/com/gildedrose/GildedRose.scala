package com.gildedrose

class GildedRose(val items: Array[Item]) {


  def updateQuality() : Unit = {
    val processor = ItemProcessor()
    for (item <- items) {
      //processor.updateItem(item)
      val internal = InternalItem(item)
      val updated = internal.update()
      item.quality = updated.quality
      item.sellIn = updated.sellIn
    }
  }
}
