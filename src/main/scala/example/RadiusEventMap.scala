package example

import scala.collection.mutable

/**
 * Created by ryejoon on 10/1/15.
 */
object RadiusEventMap extends mutable.HashMap[Int, List[ArcEvent]]{

  def addEvent(event: Event, limitRadianToNormalize: Double):Unit = {
    val radius = getLayerRadiusList
      .map(r => ClockRenderer.dateToArc(event.dtStart, event.dtEnd, r))
      .find(a => !isCollide(a, limitRadianToNormalize))
      .foreach(a => {
        var arcEventList = get(a.radius).getOrElse(List[ArcEvent]())
        arcEventList = arcEventList :+ ArcEvent(a, event)
        put(a.radius, arcEventList)
      })
  }

  def getLayerRadiusList():List[Int] = {
    (1 to ClockRenderer.clockLayers).map(i => ClockRenderer.clockRadius - (i * ClockRenderer.layerRadiusUnit)).toList
  }

  def isCollide(arc: Arc, limitRadianToNormalize: Double):Boolean = {
    val arcEventList = get(arc.radius).getOrElse(List[ArcEvent]())
    arcEventList.exists(ae => ae.arc.overlap(arc, limitRadianToNormalize))
  }
}
