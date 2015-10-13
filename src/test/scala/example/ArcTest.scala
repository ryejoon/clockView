package example

import utest._
import utest.framework.TestSuite

/**
 * Created by ryejoon on 10/14/15.
 */
object ArcTest extends TestSuite {

  val tests = TestSuite{
    val limitRadian = 0
    val radius = 10
    val baseArc = Arc(1 * Math.PI, 1.5 * Math.PI, radius)

    'overlapArcNormalizeSameArc {
      assert(baseArc.overlap(baseArc, limitRadian) == true)
    }

    'overlapArcNormalizeZeroSmallerArc {
      assert(baseArc.overlap(Arc(1.1 * Math.PI, 1.3 * Math.PI, radius), limitRadian) == true)
    }

    'overlapArcNormalizeZeroBiggerArc {
      assert(baseArc.overlap(Arc(0.5 * Math.PI, 1.7 * Math.PI, radius), limitRadian) == true)
    }

    'overlapArcNormalizeZero1 {
      assert(baseArc.overlap(Arc(0.5 * Math.PI, 1.3 * Math.PI, radius), limitRadian) == true)
    }

    'overlapArcNormalizeZero2 {
      assert(baseArc.overlap(Arc(1.3 * Math.PI, 1.8 * Math.PI, radius), limitRadian) == true)
    }

    'nonOverlapArcNormalizeZero1 {
      assert(baseArc.overlap(Arc(0 * Math.PI, 0.5 * Math.PI, radius), limitRadian) == false)
    }

    'nonOverlapArcNormalizeZero2 {
      assert(baseArc.overlap(Arc(1.6 * Math.PI, 1.8 * Math.PI, radius), limitRadian) == false)
    }
  }
}
