package com.gildedrose

object ItemName {
  val BRIE = "Aged Brie"
  val BACKSTAGE = "Backstage passes to a TAFKAL80ETC concert"
  val SULFURAS = "Sulfuras, Hand of Ragnaros"
}

class GildedRose(val items: Array[Item]) {


  def updateQuality() : Unit = {
    for (item <- items) {
      if (!item.name.equals(ItemName.BRIE)
        && !item.name.equals(ItemName.BACKSTAGE)) {
        if (item.quality > 0) {
          if (!item.name.equals(ItemName.SULFURAS)) {
            item.quality = item.quality - 1
          }
        }
      } else {
        if (item.quality < 50) {
          item.quality = item.quality + 1

          if (item.name.equals(ItemName.BACKSTAGE)) {
            if (item.sellIn < 11) {
              if (item.quality < 50) {
                item.quality = item.quality + 1
              }
            }

            if (item.sellIn < 6) {
              if (item.quality < 50) {
                item.quality = item.quality + 1
              }
            }
          }
        }
      }

      if (!item.name.equals(ItemName.SULFURAS)) {
        item.sellIn = item.sellIn - 1
      }

      if (item.sellIn < 0) {
        if (!item.name.equals(ItemName.BRIE)) {
          if (!item.name.equals(ItemName.BACKSTAGE)) {
            if (item.quality > 0) {
              if (!item.name.equals(ItemName.SULFURAS)) {
                item.quality = item.quality - 1
              }
            }
          } else {
            item.quality = item.quality - item.quality
          }
        } else {
          if (item.quality < 50) {
            item.quality = item.quality + 1
          }
        }
      }
    }
  }
}
