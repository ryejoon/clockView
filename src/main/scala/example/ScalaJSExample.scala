package example

import org.scalajs.dom.{MouseEvent, CanvasRenderingContext2D, html}
import scala.scalajs.js.annotation.JSExport


@JSExport
object ScalaJSExample {
  @JSExport
  def main(canvas: html.Canvas): Unit = {
    canvas.width = 1000
    canvas.height = 1000
    val ctx = canvas.getContext("2d")
                    .asInstanceOf[CanvasRenderingContext2D]
    ClockRenderer.setContent(ctx)
    ClockRenderer.initClockTemplate
    ClockRenderer.drawCurrentTime

    EventDataProvider.eventList
      .foreach(e => RadiusArcEventMap.addEvent(e, Radians.normalizeRadian(ClockRenderer.limitRadian)))

    println(RadiusArcEventMap)
    ClockRenderer.drawEvents()

    canvas.addEventListener("mousemove", (t:MouseEvent) => {
      ClockRenderer.clearCanvas
      ClockRenderer.initClockTemplate
      ClockRenderer.drawCurrentTime
      ClockRenderer.drawEvents
      val canvasPos: Point = ClockEventDetector.getMousePos(canvas, t.clientX.toInt, t.clientY.toInt)
      val nearestLayerRadius = ClockEventDetector.getNearestLayerRadius(canvasPos);
      val pointRadius = ClockRenderer.pointToRadian(canvasPos)
      val learestEvents: List[ArcEvent] = RadiusArcEventMap.get(nearestLayerRadius).getOrElse(List())
      if (learestEvents.length > 0) {
        val nearestAe = learestEvents.min(Ordering.by((_:ArcEvent).arc.radianDistance(pointRadius)))
        //val nearestAe = RadiusEventMap.values.flatMap(l => l).min(Ordering.by((_:ArcEvent).arc.pointDistance(canvasPos - ClockRenderer.center)))
        ClockRenderer.hightlightArcEvent(nearestAe)
      }
    }, false)
    //dom.setInterval(drawCurrentTime _, 1000)


  }
}
