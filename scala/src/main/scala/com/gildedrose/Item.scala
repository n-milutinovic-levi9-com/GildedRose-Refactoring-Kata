package com.gildedrose

case class Item(name: String, var sellIn: Int, var quality: Int) {
  def updateItem(): Unit = {
    if (!name.equals(ItemName.BRIE) && !name.equals(ItemName.BACKSTAGE)) {
      if (quality > 0) {
        if (!name.equals(ItemName.SULFURAS)) {
          quality = quality - 1
        }
      }
    } else {
      if (quality < 50) {
        quality = quality + 1

        if (name.equals(ItemName.BACKSTAGE)) {
          if (sellIn < 11) {
            if (quality < 50) {
              quality = quality + 1
            }
          }

          if (sellIn < 6) {
            if (quality < 50) {
              quality = quality + 1
            }
          }
        }
      }
    }

    if (!name.equals(ItemName.SULFURAS)) {
      sellIn = sellIn - 1
    }

    if (sellIn < 0) {
      if (!name.equals(ItemName.BRIE)) {
        if (!name.equals(ItemName.BACKSTAGE)) {
          if (quality > 0) {
            if (!name.equals(ItemName.SULFURAS)) {
              quality = quality - 1
            }
          }
        } else {
          quality = quality - quality
        }
      } else {
        if (quality < 50) {
          quality = quality + 1
        }
      }
    }
  }
}