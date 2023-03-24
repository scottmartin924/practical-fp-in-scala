package shop.http.routes

import cats.effect.IO
import org.http4s.HttpRoutes
import org.http4s.Method.GET
import org.http4s.dsl.io._
import org.http4s.implicits._

// FIXME IO Should not be here...this is just a sample
object HelloWorldRoutes {
  private[this] val prefix = "/test"

  val httpRoutes = HttpRoutes
    .of[IO] { case GET -> Root / prefix =>
      Ok("hello, world")
    }
    .orNotFound
}
