package shop.http.routes

import cats.Monad
import org.http4s.HttpRoutes
import shop.service.BrandService
import org.http4s.dsl.Http4sDsl
import org.http4s.server.Router
import org.http4s.circe.CirceEntityEncoder._

final case class BrandRoutes[F[_]: Monad](
    brands: BrandService[F]
) extends Http4sDsl[F] {

  private[routes] val prefix = "/brands"

  private val httpRoutes: HttpRoutes[F] = HttpRoutes.of[F] { case GET -> Root =>
    Ok(brands.findAll)
  }

  val routes = Router(
    prefix -> httpRoutes
  )
}
