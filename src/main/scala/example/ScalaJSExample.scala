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

    EventDataProvider.eventList.foreach(e => RadiusEventMap.addEvent(e, ClockRenderer.limitRadian))
    println(RadiusEventMap.keys)
    RadiusEventMap.values.flatMap(l => l).foreach(ae => ClockRenderer.drawArcEvent(ae))

    canvas.addEventListener("click", (t:MouseEvent) => {
      val point = ClockEventDetector.getMousePos(canvas, t.clientX.toInt, t.clientY.toInt);
      ClockRenderer.highlightClockLayer(ClockEventDetector.getNearestLayerRadius(point))
    }, false)

    /*canvas.addEventListener("mousemove", (t:MouseEvent) => {
      println("event!" + t.`type` + ":" + t.clientX + "," + t.clientY)
    }, false)*/
    //dom.setInterval(drawCurrentTime _, 1000)


  }
}
