package example

import utest._
import utest.framework.TestSuite

import scala.scalajs.js.Date

/**
 * Created by ryejoon on 10/18/15.
 */
object TimeManager$Test extends TestSuite {

  val tests = TestSuite{
    val drawMinTime = new Date(2015, 5, 2, 10, 0, 0)
    val drawMaxTime = new Date(2015, 5, 2, 22, 0, 0)

    'TimeManagerCropTimeTest {
      assert(TimeManager.cropToClockTime(new Date(2015, 5, 2, 8, 0, 0), drawMinTime, drawMaxTime) == drawMinTime)
      assert(TimeManager.cropToClockTime(new Date(2015, 5, 3, 8, 0, 0), drawMinTime, drawMaxTime) == drawMaxTime)
      val testTime: Date = new Date(2015, 5, 2, 11, 0, 0)
      assert(TimeManager.cropToClockTime(testTime, drawMinTime, drawMaxTime) == testTime)
    }

  }
}
