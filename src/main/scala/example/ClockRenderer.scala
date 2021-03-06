package example

import org.scalajs.dom._

import scala.scalajs.js
import scala.scalajs.js.Date

object ClockRenderer {
  val center = Point(500, 300)
  val clockLayers = 10
  val clockRadius = 200
  val layerRadiusUnit = clockRadius / clockLayers
  var ctx:CanvasRenderingContext2D = null
  var limitRadian:Double = 0.0

  def setContent(ctx : CanvasRenderingContext2D):Unit = {
    this.ctx = ctx
  }

  def pointToRadian(p : Point): Double = {
    val circlePos = p - ClockRenderer.center
    val atan = Math.atan2(circlePos.y, circlePos.x)
    if (atan < 0) {
      atan + (2 * Math.PI)
    } else {
      atan
    }
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
    val clockHour = Clocks.toClockHour((date.getHours() + TimeManager.futureShowHours) % 12)
    limitRadian = Radians.normalizeRadian(Radians.clockTimeToRadian(clockHour, date.getMinutes()))
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
    val piPos = Radians.clockTimeToRadian(clockHour, minute)
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

  def drawComponent(from: Date, until: Date, radius : Int, style : String): Unit = {
    ctx.moveTo(center.x, center.y)
    val arc = Arc.of(from, until, radius)
    ctx.beginPath()
    ctx.arc(center.x, center.y, arc.radius, arc.startAngle, arc.endAngle)
    ctx.strokeStyle = style
    ctx.lineWidth = 10
    ctx.stroke()
  }

  def drawArc(arc: Arc, style: String): Unit = {
    ctx.moveTo(center.x, center.y)
    ctx.beginPath()
    ctx.arc(center.x, center.y, arc.radius, arc.startAngle, arc.endAngle)
    ctx.strokeStyle = style
    ctx.lineWidth = 5
    ctx.stroke()
  }

  def hightlightArcEvent(ae : ArcEvent): Unit = {
    drawArc(ae.arc, "red")
    val textPoint = ae.arc.startAsPoint(50) + ClockRenderer.center
    ctx.font = "20px Arial";
    ctx.fillText(ae.event.summary, textPoint.x, textPoint.y, 100);
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
    drawComponent(ev.dtStart, ev.dtEnd, 100 + (Math.random() * 30).toInt, ev.style)
  }

  def drawArcEvent(ae: ArcEvent): Unit = {
    drawComponent(ae.event.dtStart, ae.event.dtEnd, ae.arc.radius, ae.event.style)
  }

  def indicateSummary(ae : ArcEvent): Unit = {
    drawArc(ae.arc, "gray")
  }

  def drawEvents():Unit = {
    RadiusArcEventMap.values.flatMap(l => l)
      .map(ae => TimeManager.cropArcEventToClockHour(ae))
      .filter(ae => !ae.isEmpty).foreach(ae => ClockRenderer.drawArcEvent(ae.get))
  }

  def clearCanvas():Unit = {
    ctx.fillStyle = "white"
    ctx.fillRect(0, 0, ctx.canvas.width, ctx.canvas.height)
  }
}
