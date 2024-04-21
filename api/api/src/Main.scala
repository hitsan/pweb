import zio.ZIOAppDefault
import zio.http._

object Main extends ZIOAppDefault:
  val todo = Routes(Method.GET / "todo" -> Handler.text("todo!"))
  val done = Routes(Method.GET / "done/list" -> Handler.text("done!"))
  val app = (todo ++ done).toHttpApp
  def run = Server.serve(app).provide(Server.defaultWithPort(8080))