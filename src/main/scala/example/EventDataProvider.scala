package example

import example.Event

import scala.scalajs.js.Date

/**
 * Created by ryejoon on 9/30/15.
 */
object EventDataProvider {

  var eventList:List[Event] = null;

  def init():Unit = {
    eventList = List(
      Event("TEST_UID", "14~16", "blue", new Date(2015, 9, 18, 14, 0, 0, 0), new Date(2015, 9, 18, 16, 0, 0, 0)),
      Event("TEST_UID2", "2~5", "green", new Date(2015, 9, 18, 2, 0, 0, 0), new Date(2015, 9, 18, 5, 0, 0, 0)),
      Event("TEST_UID1", "10~11", "yellow", new Date(2015, 9, 18, 10, 0, 0, 0), new Date(2015, 9, 18, 11, 0, 0, 0)),
      Event("TEST_UID4", "3~7", "green", new Date(2015, 9, 18, 3, 0, 0, 0), new Date(2015, 9, 18, 7, 0, 0, 0)),
      Event("TEST_UID3", "6~8:30", "black", new Date(2015, 9, 18, 6, 0, 0, 0), new Date(2015, 9, 18, 8, 30, 0, 0)),
      Event("TEST_UID6", "20~21", "green", new Date(2015, 9, 18, 20, 0, 0, 0), new Date(2015, 9, 18, 21, 0, 0, 0)),
      Event("TEST_UID7", "22~23", "black", new Date(2015, 9, 18, 22, 0, 0, 0), new Date(2015, 9, 18, 23, 30, 0, 0))
    )
  }
  init

}
