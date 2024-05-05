import com.google.api.client.googleapis.auth.oauth2.GoogleCredential
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.json.gson.GsonFactory
import com.google.api.services.calendar.Calendar
import com.google.api.services.calendar.CalendarScopes
import com.google.api.client.util.DateTime
import com.google.api.services.calendar.model.Events
import com.google.api.client.util.DateTime

import java.io.FileInputStream
import java.util.Collections

object MyCalendar {
  def buildCalendarService(appName:String, credential: String => GoogleCredential): Calendar = {
    val transport = GoogleNetHttpTransport.newTrustedTransport()
    val jsonFactory = GsonFactory.getDefaultInstance()
    val scope = CalendarScopes.CALENDAR_EVENTS_READONLY
    val calendar = Calendar.Builder(transport, jsonFactory, credential(scope))
      .setApplicationName(appName)
      .build()
    return calendar
  }
  def getTerm(start: String, end: String): Term = {
    val startDate = new DateTime(start)
    val endDate = new DateTime(end)
    return Term(startDate, endDate)
  }
}

case class Term(
  start: DateTime,
  end: DateTime,
)

extension (calendar: Calendar)
  def featchEvents(calendarId: String, term: Term): Events = {
      val events = calendar.events().list(calendarId)
        .setTimeZone("Asia/Tokyo")
        .setTimeMin(term.start)
        .setTimeMax(term.end)
        .setMaxResults(20)
        .execute()
      return events
  }

object MyCredential:
  def createCredential(accessTokenPath: String)(scope: String): GoogleCredential = {
    val credential = GoogleCredential
      .fromStream(new FileInputStream(accessTokenPath))
      .createScoped(Collections.singletonList(scope))
    return credential
  }
