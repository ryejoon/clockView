package example

import scala.collection.mutable

/**
 * Created by ryejoon on 10/1/15.
 */
object RadiusEventMap extends mutable.HashMap[Int, List[ArcEvent]]{

  def addEvent(event: Event, limitRadianToNormalize: Double):Unit = {
    for (i <- 1 to ClockRenderer.clockLayers) {
      val tempRadius = ClockRenderer.clockRadius - (i * ClockRenderer.layerRadiusUnit)
      val tempArc = ClockRenderer.dateToArc(event.dtStart, event.dtEnd, tempRadius)
      if (!isCollide(tempArc, limitRadianToNormalize)) {
        var arcEventList = get(tempRadius).getOrElse(List[ArcEvent]())
        arcEventList = arcEventList :+ ArcEvent(tempArc, event)
        put(tempRadius, arcEventList)
        return
      }
    }
  }

  def isCollide(arc: Arc, limitRadianToNormalize: Double):Boolean = {
    val arcEventList = get(arc.radius).getOrElse(List[ArcEvent]())
    arcEventList.exists(ae => ae.arc.overlap(arc, limitRadianToNormalize))
  }
}
