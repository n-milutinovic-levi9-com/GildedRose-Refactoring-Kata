package com.gildedrose

object ItemName {
  val BRIE = "Aged Brie"
  val BACKSTAGE = "Backstage passes to a TAFKAL80ETC concert"
  val SULFURAS = "Sulfuras, Hand of Ragnaros"
}

class GildedRose(val items: Array[Item]) {


  def updateQuality() : Unit = {
    val processor = ItemProcessor()
    for (item <- items) {
      //item.updateItem()
      processor.updateItem(item)
    }
  }
}
