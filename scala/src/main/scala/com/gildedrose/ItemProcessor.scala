package com.gildedrose

/** Item processor class.
 *
 * This class embodies the logic extracted from the `GildedRose`.
 */
@Deprecated(forRemoval = true)
class ItemProcessor {
  def updateItem(item: Item): Unit = {
    val isAgedBrie = item.name.equals(InternalItem.ItemName.BRIE)
    val isBackstagePass = item.name.equals(InternalItem.ItemName.BACKSTAGE)
    val isSulfuras = item.name.equals(InternalItem.ItemName.SULFURAS)

    if (isAgedBrie) {
      updateAgedBrie(item)
    } else {
      if (isSulfuras) {
        updateSulfuras(item)
      } else {
        if (isBackstagePass) {
          updateBackstagePass(item)
        } else {
          updateDefault(item)
        }
      }
    }
  }

  private def updateSulfuras(item: Item): Unit = {}

  private def updateDefault(item: Item): Unit = {
    if (item.quality > 0) {
      item.quality = item.quality - 1
    }

    item.sellIn = item.sellIn - 1

    if (item.sellIn < 0) {
      if (item.quality > 0) {
        item.quality = item.quality - 1
      }
    }
  }

  private def updateBackstagePass(item: Item): Unit = {
    if (item.quality < 50) {
      item.quality = item.quality + 1

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
    item.sellIn = item.sellIn - 1

    if (item.sellIn < 0) {
      item.quality = 0
    }
  }

  private def updateAgedBrie(item: Item): Unit = {
    if (item.quality < 50) {
      item.quality = item.quality + 1
    }
    item.sellIn = item.sellIn - 1

    if (item.sellIn < 0) {
      if (item.quality < 50) {
        item.quality = item.quality + 1
      }
    }
  }
}
