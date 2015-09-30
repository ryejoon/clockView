package example

import org.scalajs.dom
import org.scalajs.dom.raw.MouseEvent
import org.scalajs.dom.{CanvasRenderingContext2D, html}

import scala.scalajs.js
import scala.scalajs.js.annotation.JSExport

case class Point(x: Int, y: Int){
  def +(p: Point) = Point(x + p.x, y + p.y)
  def /(d: Int) = Point(x / d, y / d)
}

case class Arc(startAngle: Double, endAngle: Double, radius: Int) {

}

case class Time(hour: Int, minute: Int) {

}

@JSExport
object ScalaJSExample {
  @JSExport
  def main(canvas: html.Canvas): Unit = {
    canvas.width = 1000
    canvas.height = 1000
    val ctx = canvas.getContext("2d")
                    .asInstanceOf[CanvasRenderingContext2D]
    val xCenter = 500;
    val yCenter = 300;
    val clockRadius = 100
    val futureShowHours = 10

    val center = Point(xCenter, yCenter);


    initClockTemplate
    drawCurrentTime
    drawComponent(new Time(3, 30), new Time(4, 30), 170)
    canvas.addEventListener("click", (t:MouseEvent) => {
      println("event!" + t.`type` + ":" + t.clientX + "," + t.clientY)
    }, false)

    canvas.addEventListener("mousemove", (t:MouseEvent) => {
      println("event!" + t.`type` + ":" + t.clientX + "," + t.clientY)
    }, false)
    //dom.setInterval(drawCurrentTime _, 1000)

    def drawCurrentTime: Unit = {
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
      val futurePoint = hourToPoint((date.getHours() + futureShowHours) % 12, date.getMinutes(), clockRadius)
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


    def timeToRadian(clockHour: Integer, minute: Integer): Double = {
      (clockHour * Math.PI / 6) + (minute * Math.PI / 360) - (Math.PI / 2)
    }

    def hourToPoint(clockHour : Integer, minute : Integer, radius : Integer): Point = {
      val piPos = timeToRadian(clockHour, minute)
      val xLength = radius * Math.cos(piPos)
      val yLength = radius * Math.sin(piPos)
      return Point(center.x + xLength.toInt, center.y + yLength.toInt)
    }

    def timeToArc(fromTime : Time, untilTime : Time, radius : Int): Arc = {
      return Arc(timeToRadian(fromTime.hour, fromTime.minute), timeToRadian(untilTime.hour, untilTime.minute), radius)
    }

    def minuteOrSecondToPoint(minute : Int, radius : Int): Point = {
      val piPos = (minute * Math.PI / 30) - (Math.PI / 2)
      val xLength = radius * Math.cos(piPos)
      val yLength = radius * Math.sin(piPos)
      return Point(center.x + xLength.toInt, center.y + yLength.toInt)
    }

    def drawComponent(from: Time, until: Time, radius : Int): Unit = {
      ctx.moveTo(center.x, center.y)
      val arc = timeToArc(from, until, radius)
      ctx.beginPath()
      ctx.arc(center.x, center.y, arc.radius, arc.startAngle, arc.endAngle)
      ctx.strokeStyle = "blue"
      ctx.lineWidth = 5
      ctx.stroke()
    }

    def initClockTemplate: Unit = {
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
  }
}
