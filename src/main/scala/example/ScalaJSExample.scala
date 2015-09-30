package example

import org.scalajs.dom
import org.scalajs.dom.{CanvasRenderingContext2D, html}

import scala.scalajs.js
import scala.scalajs.js.annotation.JSExport

case class Point(x: Int, y: Int){
  def +(p: Point) = Point(x + p.x, y + p.y)
  def /(d: Int) = Point(x / d, y / d)
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
    val yCenter = 500;

    val center = Point(xCenter, yCenter);


    drawCircle(10, "black")
    drawCircle(200, "black")
    drawCurrentTime
    drawComponent("Test123")
    drawCurrentTime
    //dom.setInterval(drawCurrentTime _, 1000)

    def drawCurrentTime: Unit = {
      ctx.beginPath()

      val date = new js.Date()
      val hourPoint = hourToPoint(date.getHours() % 12, date.getMinutes(), 80)
      ctx.moveTo(center.x, center.y);
      ctx.lineTo(hourPoint.x, hourPoint.y)
      ctx.strokeStyle = "red"
      ctx.stroke()

      val minutePoint = minuteOrSecondToPoint(date.getMinutes(), 120)
      ctx.moveTo(center.x, center.y);
      ctx.lineTo(minutePoint.x, minutePoint.y)
      ctx.strokeStyle = "red"
      ctx.stroke()

/*      val secondPoint = minuteOrSecondToPoint(date.getSeconds(), 150)
      ctx.moveTo(center.x, center.y);
      ctx.lineTo(secondPoint.x, secondPoint.y)
      ctx.strokeStyle = "red"
      ctx.stroke()*/
    }


    def hourToPoint(clockHour : Integer, minute : Integer, radius : Integer): Point = {
      val piPos = (clockHour * Math.PI / 6) + (minute * Math.PI / 360) - (Math.PI / 2)
      val xLength = radius * Math.cos(piPos)
      val yLength = radius * Math.sin(piPos)
      return Point(center.x + xLength.toInt, center.y + yLength.toInt)
    }

    def minuteOrSecondToPoint(minute : Integer, radius : Integer): Point = {
      val piPos = (minute * Math.PI / 30) - (Math.PI / 2)
      val xLength = radius * Math.cos(piPos)
      val yLength = radius * Math.sin(piPos)
      return Point(center.x + xLength.toInt, center.y + yLength.toInt)
    }

    def drawComponent(componentId : String): Unit = {
      ctx.beginPath()
      ctx.arc(center.x, center.y, 50, 1.5 * Math.PI, 2 * Math.PI)
      ctx.strokeStyle = "blue"
      ctx.stroke()
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
