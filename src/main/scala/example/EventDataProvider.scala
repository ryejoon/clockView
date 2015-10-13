package example

import scala.scalajs.js.Date

/**
 * Created by ryejoon on 9/30/15.
 */
object EventDataProvider {

  var eventList:List[Event] = null;

  def init():Unit = {
    eventList = List(
      Event("TEST_UID", "14~16", "blue", new Date(2015, 10, 1, 14, 0, 0, 0), new Date(2015, 10, 1, 16, 0, 0, 0)),
      Event("TEST_UID2", "2~5", "green", new Date(2015, 10, 1, 2, 0, 0, 0), new Date(2015, 10, 1, 5, 0, 0, 0)),
      Event("TEST_UID1", "10~11", "yellow", new Date(2015, 10, 1, 10, 0, 0, 0), new Date(2015, 10, 1, 11, 0, 0, 0)),
      Event("TEST_UID4", "3~7", "green", new Date(2015, 10, 1, 3, 0, 0, 0), new Date(2015, 10, 1, 7, 0, 0, 0)),
      Event("TEST_UID3", "6~8:30", "black", new Date(2015, 10, 1, 6, 0, 0, 0), new Date(2015, 10, 1, 8, 30, 0, 0))
    )
  }
  init

}
