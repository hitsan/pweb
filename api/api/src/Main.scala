import zio.ZIOAppDefault
import zio.http._

object Main extends ZIOAppDefault:
  val app =
    Routes(
      Method.GET / "todo" -> Handler.text("Welcome to JSON APIs!")
    ).toHttpApp
  def run = Server.serve(app).provide(Server.defaultWithPort(8080))