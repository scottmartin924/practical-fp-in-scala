package effects

import cats.effect.syntax.*
import cats.syntax.all.*
import cats.effect.Temporal
import cats.effect.std.Supervisor

import scala.concurrent.duration.FiniteDuration

trait Background[F[_]] {
  def schedule[A](fa: F[A], duration: FiniteDuration): F[Unit]
}

object Background {
  def apply[F[_]: Background]: Background[F] = summon[Background[F]]

  given defaultBackground[F[_]](using
      supervisor: Supervisor[F],
      temporal: Temporal[F]
  ): Background[F] = new Background[F]:
    override def schedule[A](fa: F[A], duration: FiniteDuration): F[Unit] =
      supervisor.supervise(temporal.sleep(duration) *> fa).void
}
