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

}

object ClockRenderer {

  val center = Point(500, 300)
  val clockRadius = 100
  val futureShowHours = 10
  var ctx:CanvasRenderingContext2D = null

  def setContent(ctx : CanvasRenderingContext2D):Unit = {
    this.ctx = ctx
  }


  def timeToArc(from : Date, until : Date, radius : Int): Arc = {
    return Arc(timeToRadian(from.getHours(), from.getMinutes()), timeToRadian(until.getHours(), until.getMinutes()), radius)
  }

  def timeToRadian(clockHour: Integer, minute: Integer): Double = {
    (clockHour * Math.PI / 6) + (minute * Math.PI / 360) - (Math.PI / 2)
  }

  def drawCurrentTime(): Unit = {
    ctx.beginPath()

    val date = new js.Date()
    val hourPoint = hourToPoint(date.getHours() % 12, date.getMinutes(), 50)
    ctx.moveTo(center.x, center.y);
    ctx.lineTo(hourPoint.x, hourPoint.y)
    ctx.strokeStyle = "red"
    ctx.stroke()

    val minutePoint = minuteOrSecondToPoint(date.getMinutes(), 70)
    ctx.beginPath()
    ctx.moveTo(center.x, center.y);
    ctx.lineTo(minutePoint.x, minutePoint.y)
    ctx.strokeStyle = "red"
    ctx.stroke()

    ctx.beginPath()
    val futurePoint = hourToPoint((date.getHours() + futureShowHours) % 12, date.getMinutes(), clockRadius + 100)
    ctx.moveTo(center.x, center.y);
    ctx.lineTo(futurePoint.x, futurePoint.y)
    ctx.strokeStyle = "black"

    ctx.setLineDash(scalajs.js.Array(5.0));
    ctx.stroke()
    ctx.setLineDash(null)


    /*      val secondPoint = minuteOrSecondToPoint(date.getSeconds(), 150)
          ctx.moveTo(center.x, center.y);
          ctx.lineTo(secondPoint.x, secondPoint.y)
          ctx.strokeStyle = "red"
          ctx.stroke()*/
  }

  def hourToPoint(clockHour : Integer, minute : Integer, radius : Integer): Point = {
    val piPos = timeToRadian(clockHour, minute)
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
    val arc = timeToArc(from, until, radius)
    ctx.beginPath()
    ctx.arc(center.x, center.y, arc.radius, arc.startAngle, arc.endAngle)
    ctx.strokeStyle = "blue"
    ctx.lineWidth = 5
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

}
