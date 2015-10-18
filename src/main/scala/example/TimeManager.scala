package example

import java.util.Calendar

import scala.scalajs.js
import scala.scalajs.js.Date

/**
 * Created by ryejoon on 10/17/15.
 */
object TimeManager {
  val futureShowHours = 10

  val CLOCK_HOURS = 12
  val HOUR_AS_MILLIS = 1000 * 60 * 60

  var currentDateTime = new js.Date()
  var eventDrawMinDateTime = new js.Date()
  var eventDrawMaxDateTime = new js.Date()
  refreshTimes()

  def refreshTimes(): Unit = {
    currentDateTime = new js.Date()
    eventDrawMinDateTime.setTime(currentDateTime.getTime() - ((CLOCK_HOURS - futureShowHours) * HOUR_AS_MILLIS))
    eventDrawMaxDateTime.setTime(currentDateTime.getTime() + (futureShowHours * HOUR_AS_MILLIS))
    println("current : " + currentDateTime)
    println("Drawing Range : " + eventDrawMinDateTime + "~" + eventDrawMaxDateTime)
  }


  def getHour() {currentDateTime.getHours()}

  def getCurrentMinute() {currentDateTime.getMinutes()}

  def cropArcEventToClockHour(arcEvent : ArcEvent): Option[ArcEvent] = {
    if (arcEvent == null || nothingToDraw(arcEvent.event)) {
      return Option.empty
    }

    val arcToDraw: Arc = Arc.of(cropToClockTime(arcEvent.event.dtStart), cropToClockTime(arcEvent.event.dtEnd), arcEvent.arc.radius)
    return Some(ArcEvent(arcToDraw, arcEvent.event))
  }

  def nothingToDraw(event: Event): Boolean = {
    if (cropToClockTime(event.dtStart).getTime() > event.dtEnd.getTime() ||
      cropToClockTime(event.dtEnd).getTime() < event.dtStart.getTime()) {
      return true
    }
    return false
  }

  def cropToClockTime(target : Date, drawMinTime : Date = eventDrawMinDateTime, drawMaxTime : Date = eventDrawMaxDateTime): Date = {
    target match {
      case _ if target.getTime() < drawMinTime.getTime()
        // TODO : Should Clone
        => drawMinTime
      case _ if target.getTime() >= drawMaxTime.getTime()
        => drawMaxTime
      case _ => target
    }
  }
}
