package example

/**
 * Created by ryejoon on 10/4/15.
 */
case class Arc(startAngle: Double, endAngle: Double, radius: Int) {

  def overlap(that: Arc, limitRadian: Double):Boolean = {
    println("OverlapCheck :" + this + " vs " + that + ", limitRadian : " + limitRadian)
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

}
