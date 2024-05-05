import com.google.api.services.calendar.CalendarScopes
import MyCalendar._
import MyCredential._
import Term._
import Env._

object Main:
  def main(args: Array[String]): Unit = {
    val credenrial = createCredential(accessTokenPath)
    val calendar = buildCalendarService(appName, credenrial)
    val term = getTerm("2024-04-20T00:00:00.000Z", "2024-05-05T00:00:00.000Z")
    val events = calendar.featchEvents(calendarId, term)
    events.getItems().forEach(e => println(e.getSummary()))
  }
