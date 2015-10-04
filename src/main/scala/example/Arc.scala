package example

/**
 * Created by ryejoon on 10/4/15.
 */
case class Arc(startAngle: Double, endAngle: Double, radius: Int) {

  def overlap(that: Arc, limitRadian: Double):Boolean = {
    if (this.radius != that.radius) {
      return false;
    }

    val normalizedThisStart = this.startAngle - limitRadian
    val normalizedThisEnd = this.endAngle - limitRadian
    val normalizedThatStart = that.startAngle - limitRadian
    val normalizedThatEnd = that.endAngle - limitRadian

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
