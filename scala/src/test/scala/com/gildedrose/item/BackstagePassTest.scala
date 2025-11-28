package com.gildedrose.item

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import com.gildedrose.BackstagePass

/** Test Backstage pass. */
class BackstagePassTest extends AnyFlatSpec with Matchers {
  "Backstage Pass" should "never exceed max quality" in {
    val pass = BackstagePass(quality = 50, sellIn = 10)
    val agedPass = pass.update()

    agedPass.quality should be(50)
  }

  "Backstage pass not within first call days" should "increase its quality by 1" in {
    val pass = BackstagePass(quality = 30, sellIn = BackstagePass.FIRST_CALL_SELL_IN + 1)
    val agedPass = pass.update()

    agedPass.quality should be(pass.quality + 1)
  }

  "Backstage pass between first and last call days" should "increase its quality by 2" in {
    val pass = BackstagePass(quality = 30, sellIn = BackstagePass.LAST_CALL_SELL_IN + 1)
    val agedPass = pass.update()

    agedPass.quality should be(pass.quality + 2)
  }

  "Backstage pass after the last call days" should "increase its quality by 3" in {
    val pass = BackstagePass(quality = 30, sellIn = BackstagePass.LAST_CALL_SELL_IN - 1)
    val agedPass = pass.update()

    agedPass.quality should be(pass.quality + 3)
  }

  "Backstage pass, after the concert" should "be worth nothing" in {
    val pass = BackstagePass(quality = 30, sellIn = 0)
    val agedPass = pass.update()

    agedPass.quality should be(0)
  }

  "Backstage pass sell-in" should "always decrease" in {
    for { sellIn <- List(20, 10, 1, 0 -4)} {
      val pass = BackstagePass(quality = 30, sellIn = sellIn)
      val agedPass = pass.update()

      agedPass.sellIn should be(pass.sellIn - 1)
    }

  }
}
