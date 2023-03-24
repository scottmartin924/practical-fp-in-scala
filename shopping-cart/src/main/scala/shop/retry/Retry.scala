package shop.retry

import cats.effect.Temporal
import retry.RetryPolicy
import retry.RetryDetails
import retry.RetryDetails.WillDelayAndRetry
import org.typelevel.log4cats.Logger
import retry.RetryDetails.GivingUp
import retry._

trait Retry[F[_]] {
  def retry[A](policy: RetryPolicy[F], retriable: Retriable)(f: F[A]): F[A]
}

object Retry {
  def apply[F[_]: Retry]: Retry[F] = implicitly

  implicit def retryWithLogger[F[_]: Logger: Temporal]: Retry[F] = new Retry[F] {
    override def retry[A](policy: RetryPolicy[F], retriable: Retriable)(
        f: F[A]
    ): F[A] = {
      def onError(err: Throwable, details: RetryDetails): F[Unit] =
        details match {
          case WillDelayAndRetry(nextDelay, retriesSoFar, cumulativeDelay) =>
            Logger[F].error(
              s"Failed on $retriable. We retried $retriesSoFar times."
            )
          case GivingUp(totalRetries, totalDelay) =>
            Logger[F].error(
              s"Giving up on $retriable after $totalRetries retries."
            )
        }

      retryingOnAllErrors[A](policy, onError)(f)
    }
  }
}
