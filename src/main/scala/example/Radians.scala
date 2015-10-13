package example

/**
 * Created by ryejoon on 10/14/15.
 */
object Radians {
  def normalizeRadian(radian : Double) : Double = {
    radian match {
      case _ if isNormalized(radian) => radian
      case _ if radian < 0 => {
        var result = radian
        while (!isNormalized(result)) result = radian + (2 * Math.PI)
        return result
      }
      case _ if radian > 2 * Math.PI => {
        var result = radian
        while (!isNormalized(result)) result = radian - (2 * Math.PI)
        return result
      }
    }
  }

  def isNormalized(radian: Double): Boolean = {
    radian >= 0 && radian < 2 * Math.PI
  }
}
