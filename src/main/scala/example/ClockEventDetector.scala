package example

import org.scalajs.dom.html

/**
 * Created by ryejoon on 10/4/15.
 */
object ClockEventDetector {

  def getNearestLayerRadius(p : Point) : Int = {
    val distToCenter = ClockRenderer.center.dist(p)
    if (distToCenter > ClockRenderer.clockRadius) return ClockRenderer.clockRadius
    distToCenter - ((distToCenter + (ClockRenderer.layerRadiusUnit / 2)) % ClockRenderer.layerRadiusUnit) + ClockRenderer.layerRadiusUnit / 2
  }

  def getMousePos(canvas : html.Canvas, clientX : Int, clientY : Int) : Point = {
    val rect = canvas.getBoundingClientRect();
    return Point(clientX - rect.left.toInt, clientY - rect.top.toInt)
  }
}
