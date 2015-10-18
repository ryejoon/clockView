package example

import org.scalatest.FunSuite
import utest.framework.TestSuite

import scala.scalajs.js.Date
import utest._
/**
 * Created by ryejoon on 10/13/15.
 */
object RadiusEventMap$Test extends TestSuite {

  RadiusArcEventMap.addEvent(
    Event("TEST_UID", "제목123", "blue", new Date(2015, 10, 1, 15, 0, 0, 0), new Date(2015, 10, 1, 16, 0, 0, 0)), 0)

  def tests = TestSuite {

    'DetectCollision {
      val outerRadius = RadiusArcEventMap.getLayerRadiusList()(0)
      assert(RadiusArcEventMap.isCollide(Arc(0, 0.5 * Math.PI, outerRadius), 0) == true)
    }

    'DetectNonCollision {
      val innerRadius = RadiusArcEventMap.getLayerRadiusList()(1)
      assert(RadiusArcEventMap.isCollide(Arc(0, 0.5 * Math.PI, innerRadius), 0) == false)
    }
  }

}
