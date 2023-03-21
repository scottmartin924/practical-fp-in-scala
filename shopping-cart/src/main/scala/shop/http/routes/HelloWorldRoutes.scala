package shop.http.routes

import cats.Monad
import cats.effect.IO
import org.http4s.{HttpRoutes, Response, Status}
import org.http4s.Method.GET
import org.http4s.dsl.io.*
import org.http4s.server.Router
import org.http4s.ember.server.*
import org.http4s.implicits.*

// FIXME IO Should not be here...this is just a sample
object HelloWorldRoutes {
  private[this] val prefix = "/test"

  val httpRoutes = HttpRoutes
    .of[IO] { case GET -> Root / prefix =>
      Ok("hello, world")
    }.orNotFound
}
