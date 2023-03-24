package example.encodings

import cats.Functor
import cats.effect.kernel.Ref
import cats.effect.kernel.Resource
import cats.syntax.all._
import dev.profunktor.redis4cats.RedisCommands
import io.estatico.newtype.macros.newtype

object CounterEncodings {
  trait Counter[F[_]] {
    def increment: F[Unit]
    def decrement: F[Unit]
    def get: F[Long]
  }

  object Counter {
    @newtype case class RedisKey(value: String)

    def liveCounter[F[_]: Functor](
        key: RedisKey
    ): Resource[F, Counter[F]] = makeRedis[F].map { redis =>
      new Counter[F] {
        // Ack...that's annoying: since redis.incr returns F[Long] and we don't know this is IO (so can't just do .void) have to map to void? Probably a better way
        override def increment: F[Unit] = redis.incr(key.value).map(_ => ())

        override def decrement: F[Unit] = redis.decr(key.value).map(_ => ())

        override def get: F[Long] = redis.get(key.value).map {
          // FIXME Wrong...if we want to do this need F to be ApplicativeError and be able to throw
          case Some(value) => value
          case None => throw new Exception(s"oops...Redis key $key is missing")
        }

      }
    }

    // Most naive way to make redis connection (w/o external config too)
    // Note: connection info is default for redis docker image so seems safe in vcs
    private def makeRedis[F[_]]: Resource[F, RedisCommands[F, String, Long]] =
      ???

    // Counter for test implementations (just a local ref...maybe calling it "test" is a bit unfair)
    def testCounter[F[_]](ref: Ref[F, Long]): Counter[F] = new Counter[F] {

      override def increment: F[Unit] = ref.update(_ + 1)

      override def decrement: F[Unit] = ref.update(_ - 1)

      override def get: F[Long] = ref.get

    }
  }
}
