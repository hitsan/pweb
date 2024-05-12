import com.google.api.services.calendar.CalendarScopes
import CalendarService._
import UserCredential._
import Env._

object Main:
  def main(args: Array[String]): Unit = {
    val credenrial = createCredential(accessTokenPath)
    val calendar = build(appName, credenrial)
    val term = getTerm("2024-04-20", "2024-05-05")
    val events = featchEvents(calendar, calendarId, term)
    for {
      event <- events
    } yield {
      val start = event.getStart()
      val end = event.getEnd()
      val summary = event.getSummary()
      println(s"start: $start, end: $end, summary: $summary")
    }
  }
