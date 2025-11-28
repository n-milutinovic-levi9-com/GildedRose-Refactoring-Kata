package com.gildedrose

object InternalItem {
  def apply(item: Item): InternalItem = {
    item match {
      case Item(ItemName.BRIE, sellIn, quality) => new AgedBrie(sellIn, quality)
      case Item(ItemName.BACKSTAGE, sellIn, quality) => new BackstagePass(sellIn, quality)
      case Item(ItemName.SULFURAS, sellIn, quality) => new Sulfuras(sellIn, quality)
      case Item(name, sellIn, quality) => new MiscellaneousItem(name, sellIn, quality)
    }
  }

  def exportItem(internal: InternalItem): Item = Item(internal.name, internal.sellIn, internal.quality)
}

abstract case class InternalItem(name: String, sellIn: Int, quality: Int) {
  def update(): InternalItem
}

class AgedBrie(sellIn: Int, quality: Int) extends InternalItem(ItemName.BRIE, sellIn, quality) {
  @Override
  def update(): InternalItem = {
    var newQuality = quality
    var newSellIn = sellIn

    if (newQuality < 50) {
      newQuality = newQuality + 1
    }
    newSellIn = newSellIn - 1

    if (newSellIn < 0) {
      if (newQuality < 50) {
        newQuality = newQuality + 1
      }
    }
    AgedBrie(newSellIn, newQuality)
  }
}

class BackstagePass(sellIn: Int, quality: Int) extends InternalItem(ItemName.BACKSTAGE, sellIn, quality) {
  @Override
  def update(): InternalItem = {
    var newQuality = quality
    var newSellIn = sellIn

    if (newQuality < 50) {
      newQuality = newQuality + 1

      if (newSellIn < 11) {
        if (newQuality < 50) {
          newQuality = newQuality + 1
        }
      }

      if (newSellIn < 6) {
        if (newQuality < 50) {
          newQuality = newQuality + 1
        }
      }
    }
    newSellIn = newSellIn - 1

    if (newSellIn < 0) {
      newQuality = 0
    }
    BackstagePass(newSellIn, newQuality)
  }
}

class Sulfuras(sellIn: Int, quality: Int) extends InternalItem(ItemName.SULFURAS, sellIn, quality) {
  @Override
  override def update(): InternalItem = this
}

class MiscellaneousItem(name: String, sellIn: Int, quality: Int) extends InternalItem(name, sellIn, quality) {
  override def update(): InternalItem = {
    var newQuality = quality
    var newSellIn = sellIn

    if (newQuality > 0) {
      newQuality = newQuality - 1
    }

    newSellIn = newSellIn - 1

    if (newSellIn < 0) {
      if (newQuality > 0) {
        newQuality = newQuality - 1
      }
    }
    MiscellaneousItem(name, newSellIn, newQuality)
  }
}