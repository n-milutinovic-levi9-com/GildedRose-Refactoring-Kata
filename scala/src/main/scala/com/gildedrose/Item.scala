package com.gildedrose

case class Item(name: String, var sellIn: Int, var quality: Int) {
  def updateItem(): Unit = {
    val isAgedBrie = name.equals(ItemName.BRIE)

    if (isAgedBrie) {
      {
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

        if (!name.equals(ItemName.SULFURAS)) {
          sellIn = sellIn - 1
        }

        if (sellIn < 0) {
          if (quality < 50) {
            quality = quality + 1
          }
        }
      }
    } else {
      {
        if (!name.equals(ItemName.BACKSTAGE)) {
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
          if (!name.equals(ItemName.BACKSTAGE)) {
            if (quality > 0) {
              if (!name.equals(ItemName.SULFURAS)) {
                quality = quality - 1
              }
            }
          } else {
            quality = quality - quality
          }
        }
      }
    }

  }

}