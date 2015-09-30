package example

import org.scalajs.dom._

import scala.scalajs.js
import scala.scalajs.js.Date

/**
 * Created by ryejoon on 10/1/15.
 */

case class Point(x: Int, y: Int){
  def +(p: Point) = Point(x + p.x, y + p.y)
  def /(d: Int) = Point(x / d, y / d)
}

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

case class ArcEvent(arc: Arc, event: Event) {

}

object ClockRenderer {
  val center = Point(500, 300)
  val clockRadius = 200
  val futureShowHours = 10
  var ctx:CanvasRenderingContext2D = null
  var limitRadian:Double = 0.0

  def setContent(ctx : CanvasRenderingContext2D):Unit = {
    this.ctx = ctx
  }


  def dateToArc(from : Date, until : Date, radius : Int): Arc = {
    val fromRadian: Double = clockTimeToRadian(toClockHour(from.getHours()), from.getMinutes())
    val untilRadian: Double = clockTimeToRadian(toClockHour(until.getHours()), until.getMinutes())
    return Arc(fromRadian, untilRadian, radius)
  }

  def clockTimeToRadian(clockHour: Integer, minute: Integer): Double = {
    (clockHour * Math.PI / 6) + (minute * Math.PI / 360) - (Math.PI / 2)
  }

  def drawCurrentTime(): Unit = {
    ctx.beginPath()

    val date = new js.Date()
    val hourPoint = hourToPoint(date.getHours() % 12, date.getMinutes(), (clockRadius * 0.5).toInt)
    ctx.moveTo(center.x, center.y);
    ctx.lineWidth = 7
    ctx.lineTo(hourPoint.x, hourPoint.y)
    ctx.strokeStyle = "red"
    ctx.stroke()

    val minutePoint = minuteOrSecondToPoint(date.getMinutes(), (clockRadius * 0.7).toInt)
    ctx.beginPath()
    ctx.moveTo(center.x, center.y);
    ctx.lineWidth = 5
    ctx.lineTo(minutePoint.x, minutePoint.y)
    ctx.strokeStyle = "red"
    ctx.stroke()

    ctx.beginPath()
    val clockHour = toClockHour((date.getHours() + futureShowHours) % 12)
    limitRadian = clockTimeToRadian(clockHour, date.getMinutes())
    val futurePoint = hourToPoint(clockHour, date.getMinutes(), clockRadius)
    ctx.moveTo(center.x, center.y);
    ctx.lineTo(futurePoint.x, futurePoint.y)
    ctx.strokeStyle = "black"

    ctx.setLineDash(scalajs.js.Array(5.0));
    ctx.lineWidth = 1
    ctx.stroke()
    ctx.setLineDash(null)


    /*      val secondPoint = minuteOrSecondToPoint(date.getSeconds(), 150)
          ctx.moveTo(center.x, center.y);
          ctx.lineTo(secondPoint.x, secondPoint.y)
          ctx.strokeStyle = "red"
          ctx.stroke()*/
  }

  def hourToPoint(clockHour : Integer, minute : Integer, radius : Integer): Point = {
    val piPos = clockTimeToRadian(clockHour, minute)
    val xLength = radius * Math.cos(piPos)
    val yLength = radius * Math.sin(piPos)
    return Point(center.x + xLength.toInt, center.y + yLength.toInt)
  }

  def minuteOrSecondToPoint(minute : Int, radius : Int): Point = {
    val piPos = (minute * Math.PI / 30) - (Math.PI / 2)
    val xLength = radius * Math.cos(piPos)
    val yLength = radius * Math.sin(piPos)
    return Point(center.x + xLength.toInt, center.y + yLength.toInt)
  }

  def drawComponent(from: Date, until: Date, radius : Int): Unit = {
    ctx.moveTo(center.x, center.y)
    val arc = dateToArc(from, until, radius)
    ctx.beginPath()
    ctx.arc(center.x, center.y, arc.radius, arc.startAngle, arc.endAngle)
    ctx.strokeStyle = "blue"
    ctx.lineWidth = 10
    ctx.stroke()
  }

  def drawArc(arc: Arc): Unit = {
    ctx.moveTo(center.x, center.y)
    ctx.beginPath()
    ctx.arc(center.x, center.y, arc.radius, arc.startAngle, arc.endAngle)
    ctx.strokeStyle = "blue"
    ctx.lineWidth = 5
    ctx.stroke()
  }

  def initClockTemplate(): Unit = {
    val hourBarLength = 10

    drawCircle(clockRadius, "black")
    drawCircle(5, "black")

    for (i <- 0 to 11) {
      val barFrom = hourToPoint(i, 0, clockRadius - hourBarLength)
      val barUntil = hourToPoint(i, 0, clockRadius)
      ctx.moveTo(barFrom.x, barFrom.y);
      ctx.lineTo(barUntil.x, barUntil.y)
      ctx.strokeStyle = "black"
      ctx.stroke()
    }
  }

  def drawCircle(radius: Double, color : String): Unit = {
    ctx.fillStyle = color
    ctx.moveTo(center.x, center.y);
    ctx.beginPath()
    ctx.arc(center.x, center.y, radius, 0, 2 * Math.PI)
    ctx.stroke()
  }


  def drawEvent(ev: Event): Unit = {
    drawComponent(ev.dtStart, ev.dtEnd, 100 + (Math.random() * 30).toInt)
  }

  def drawArcEvent(ae: ArcEvent): Unit = {
    drawComponent(ae.event.dtStart, ae.event.dtEnd, ae.arc.radius)
  }

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
