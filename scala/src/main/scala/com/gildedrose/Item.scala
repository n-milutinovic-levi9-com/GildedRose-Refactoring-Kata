package com.gildedrose

case class Item(name: String, var sellIn: Int, var quality: Int) {
  def updateItem(): Unit = {
    val isAgedBrie = name.equals(ItemName.BRIE)
    val isBackstagePass = name.equals(ItemName.BACKSTAGE)
    val isSulfuras = name.equals(ItemName.SULFURAS)

    if (isAgedBrie) {
      if (quality < 50) {
        quality = quality + 1
      }
      sellIn = sellIn - 1

      if (sellIn < 0) {
        if (quality < 50) {
          quality = quality + 1
        }
      }
    } else {
      if (isSulfuras) {

      } else {
        if (isBackstagePass) {
          if (quality < 50) {
            quality = quality + 1

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
          sellIn = sellIn - 1

          if (sellIn < 0) {
            quality = quality - quality
          }

        } else {
          if (quality > 0) {
              quality = quality - 1
          }

          sellIn = sellIn - 1

          if (sellIn < 0) {
            if (quality > 0) {
                quality = quality - 1
            }
          }
        }
      }
    }
  }
}