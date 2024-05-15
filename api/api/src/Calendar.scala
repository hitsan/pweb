import com.google.api.client.googleapis.auth.oauth2.GoogleCredential
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.json.gson.GsonFactory
import com.google.api.services.calendar.Calendar
import com.google.api.services.calendar.CalendarScopes
import com.google.api.services.calendar.model.Event
import com.google.api.client.util.DateTime

import scala.collection.mutable.Buffer

import collection.JavaConverters._
import java.io.FileInputStream
import java.util.Collections

object CalendarService {
  def build(appName:String, credential: GoogleCredential): Calendar = {
    val transport = GoogleNetHttpTransport.newTrustedTransport()
    val jsonFactory = GsonFactory.getDefaultInstance()
    val calendar = Calendar.Builder(transport, jsonFactory, credential)
      .setApplicationName(appName)
      .build()
    return calendar
  }
  def createCredential(accessTokenPath: String): GoogleCredential = {
    val scope = CalendarScopes.CALENDAR_EVENTS_READONLY
    val credential = GoogleCredential
      .fromStream(new FileInputStream(accessTokenPath))
      .createScoped(Collections.singletonList(scope))
    return credential
  }
  def getTerm(start: String, end: String): Term = {
    val time = "T00:00:00.000Z"
    val startDate = new DateTime(start+time)
    val endDate = new DateTime(end+time)
    return Term(startDate, endDate)
  }
  def featchEvents(calendar: Calendar, calendarId: String, term: Term): List[Event] = {
    val events = calendar.events()
      .list(calendarId)
      .setTimeZone("Asia/Tokyo")
      .setTimeMin(term.start)
      .setTimeMax(term.end)
      .setMaxResults(10)
      .execute()
    return events.getItems().asScala.toList
  }
}

case class Term(
  start: DateTime,
  end: DateTime,
)