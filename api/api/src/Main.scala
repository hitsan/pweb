import zio.ZIOAppDefault
import zio.http._

object Main extends ZIOAppDefault:
  val app = Handler.text("hello APP").toHttp
    def run = Server.serve(app).provide(Server.defaultWithPort(8080))