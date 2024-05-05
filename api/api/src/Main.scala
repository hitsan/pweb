import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.json.gson.GsonFactory
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential
import com.google.api.services.calendar.Calendar
import com.google.api.services.calendar.CalendarScopes
import com.google.api.client.util.DateTime

import java.io.FileInputStream
import Env._

import java.util.Collections

object Main:
  def getCalendar(): Calendar = {
    val scope = CalendarScopes.CALENDAR_EVENTS_READONLY
    val credential = GoogleCredential
      .fromStream(new FileInputStream(accessTokenPath))
      .createScoped(Collections.singletonList(scope))
    val transport = GoogleNetHttpTransport.newTrustedTransport()
    val jsonFactory = GsonFactory.getDefaultInstance()
    val calenar = Calendar.Builder(transport, jsonFactory, credential)
      .setApplicationName(appName)
      .build()
    return calenar
  }

  def main(args: Array[String]): Unit = {
    val calendar = getCalendar()

    val p = new DateTime("2024-04-20T00:00:00.000Z")
    val n = new DateTime("2024-05-05T00:00:00.000Z")
    val acl = calendar.events().list(calendarId)
      .setTimeZone("Asia/Tokyo")
      .setTimeMin(p)
      .setTimeMax(n)
      .setMaxResults(20)
      .execute()
    acl.getItems().forEach(e => println(e.getSummary()))
  }
