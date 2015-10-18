package example

/**
 * Created by ryejoon on 10/17/15.
 */
object Clocks {

  def toClockHour(hour24: Int): Int = {
    if (hour24 > 12) {
      return hour24 - 12
    }
    if (hour24 < 1) {
      return hour24 + 12
    }
    return hour24
  }

}
