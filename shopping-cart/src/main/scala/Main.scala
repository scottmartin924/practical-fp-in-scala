import cats.effect.{ExitCode, IO, IOApp}
import com.comcast.ip4s.IpLiteralSyntax
import org.http4s.ember.server.EmberServerBuilder
import shop.http.routes.HelloWorldRoutes

object Main extends IOApp.Simple {
  // FIXME Make port configurable
  override def run: IO[Unit] = EmberServerBuilder
    .default[IO]
    .withHost(ipv4"0.0.0.0")
    .withPort(port"8080")
    .withHttpApp(HelloWorldRoutes.httpRoutes)
    .build
    .use(_ => IO.never)
    .as(ExitCode.Success)
}
