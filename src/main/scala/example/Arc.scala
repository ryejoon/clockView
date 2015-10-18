package example

import scala.scalajs.js.Date

/**
 * Created by ryejoon on 10/4/15.
 */
object Arc {

  def of(from : Date, until : Date, radius : Int): Arc = {
    val fromRadian: Double = dateToNormalizedRadian(from)
    val untilRadian: Double = dateToNormalizedRadian(until)
    return Arc(fromRadian, untilRadian, radius)
  }

  def dateToNormalizedRadian(from: Date): Double = {
    Radians.normalizeRadian(Radians.clockTimeToRadian(Clocks.toClockHour(from.getHours()), from.getMinutes()))
  }
}

case class Arc(var startAngle: Double, var endAngle: Double, radius: Int) {
  startAngle = Radians.normalizeRadian(startAngle)
  endAngle = Radians.normalizeRadian(endAngle)

  def overlap(that: Arc, limitRadian: Double):Boolean = {
    if (this.radius != that.radius) {
      return false;
    }

    val normalizedThisStart = Radians.normalizeRadian(this.startAngle - limitRadian)
    val normalizedThisEnd = Radians.normalizeRadian(this.endAngle - limitRadian)
    val normalizedThatStart = Radians.normalizeRadian(that.startAngle - limitRadian)
    val normalizedThatEnd = Radians.normalizeRadian(that.endAngle - limitRadian)

    if (normalizedThisStart < normalizedThatEnd && normalizedThatStart < normalizedThisEnd) {
      return true
    }

    if (normalizedThatStart < normalizedThisEnd && normalizedThisStart < normalizedThatEnd) {
      return true
    }

    return false
  }

  def radianDistance(rad : Double):Double = {
    if (startAngle <= rad && rad <= endAngle) return 0
    if (rad <= startAngle) return Math.abs(startAngle - rad)
    return Math.abs(rad - endAngle)
  }

  def pointDistance(p : Point):Double = {
    val startX = Math.cos(startAngle) * radius
    val startY = Math.sin(startAngle) * radius
    val endX = Math.cos(endAngle) * radius
    val endY = Math.sin(endAngle) * radius

    return Math.min(Point(startX.toInt, startY.toInt).dist(p), Point(endX.toInt, endY.toInt).dist(p))
  }

  def startAsPoint(radiusDelta : Int) : Point = {
    val resultRadius = radius + radiusDelta
    val startX = Math.cos(startAngle) * resultRadius
    val startY = Math.sin(startAngle) * resultRadius
    Point(startX.toInt, startY.toInt)
  }
}
