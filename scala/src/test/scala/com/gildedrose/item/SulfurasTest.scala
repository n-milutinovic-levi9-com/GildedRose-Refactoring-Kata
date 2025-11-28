package com.gildedrose.item

import com.gildedrose.Sulfuras
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

/** Test immutability of Sulfuras */
class SulfurasTest extends AnyFlatSpec with Matchers {
  "Sulfuras" should "not change any property" in {
    val sulfuras = Sulfuras(quality = 30, sellIn = 25)

    sulfuras.update() should be(sulfuras)
  }

  "Sulfuras" should "not change any property, ever" in {
    val sulfuras = Sulfuras(quality = 30, sellIn = 25)

    sulfuras.update().update().update().update() should be(sulfuras)
  }
}
