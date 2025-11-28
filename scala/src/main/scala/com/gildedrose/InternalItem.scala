package com.gildedrose

abstract case class InternalItem(name: String, sellIn: Int, quality: Int) {
  def update(): InternalItem
}

class AgedBrie(name: String, sellIn: Int, quality: Int) extends InternalItem(name, sellIn, quality) {
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
    AgedBrie(name, newSellIn, newQuality)
  }
}

class BackstagePass(name: String, sellIn: Int, quality: Int) extends InternalItem(name, sellIn, quality) {
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
    BackstagePass(name, newSellIn, newQuality)
  }
}

class Sulfuras(name: String, sellIn: Int, quality: Int) extends InternalItem(name, sellIn, quality) {
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