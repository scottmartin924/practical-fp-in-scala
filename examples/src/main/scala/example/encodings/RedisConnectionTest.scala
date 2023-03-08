package example.encodings

import cats.effect.IOApp
import dev.profunktor.redis4cats.Redis
import cats.effect.IO
import dev.profunktor.redis4cats.effect.Log.Stdout._
import cats.syntax.all._

object RedisConnectionTest extends IOApp.Simple {

  // NOTE: Redis connection info is default for redis docker image so seems safe putting in source control
  override def run: IO[Unit] =
    Redis[IO].utf8("redis://default:redispw@localhost:55000").use { redis =>
      val key = "test"
      val value = "abc"
      for {
        _ <- redis.set(key, value)
        x <- redis.get(key)
        _ <- IO.println(s"Is it set? ${value === x.getOrElse("NOPE")}")
      } yield ()
    }

}
