package example

import utest._
import utest.framework.TestSuite

/**
 * Created by ryejoon on 10/17/15.
 */
object Radians$Test extends TestSuite {

  def tests = TestSuite {

    'checkNormalized {
      assert(Radians.isNormalized(0) == true)
      assert(Radians.isNormalized(Math.PI) == true)
      assert(Radians.isNormalized(2 * Math.PI - 0.01) == true)
      assert(Radians.isNormalized(2 * Math.PI) == false)
      assert(Radians.isNormalized(-0.01) == false)
      assert(Radians.isNormalized(-2 * Math.PI) == false)
    }

    'normalize {
      assert(Radians.normalizeRadian(0.0) == 0.0)
      assert(Radians.normalizeRadian(2 * Math.PI) == 0.0)
      assert(Radians.normalizeRadian(-2 * Math.PI) == 0.0)
      assert(Radians.normalizeRadian(3 * Math.PI) == Math.PI)
      assert(Radians.normalizeRadian(1.5 * Math.PI) == 1.5 * Math.PI)

    }
  }
}
