package example

import scala.scalajs.js.Date

/**
 * Created by ryejoon on 9/30/15.
 */
object EventDataProvider {

  var eventList:List[Event] = null;

  def init():Unit = {
    eventList = List(
      Event("TEST_UID", "제목123", new Date(2015, 10, 1, 15, 0, 0, 0), new Date(2015, 10, 1, 16, 0, 0, 0)),
      Event("TEST_UID1", "제목3", new Date(2015, 10, 1, 11, 0, 0, 0), new Date(2015, 10, 1, 12, 0, 0, 0)),
      Event("TEST_UID2", "제목23", new Date(2015, 10, 1, 3, 0, 0, 0), new Date(2015, 10, 1, 5, 0, 0, 0)),
      Event("TEST_UID3", "제목1235", new Date(2015, 10, 1, 6, 0, 0, 0), new Date(2015, 10, 1, 8, 30, 0, 0))
    )
  }
  init

}