package shop.program

import cats.MonadThrow
import cats.data.NonEmptyList
import cats.effect.Temporal
import cats.syntax.all._
import org.typelevel.log4cats.Logger
import retry.RetryPolicies._
import retry._
import shop.client.PaymentClient
import shop.domain.cart.CartItem
import shop.domain.order.{OrderId, PaymentId}
import shop.domain.payment._
import shop.domain.user.UserId
import shop.effects.Background
import shop.program.Checkout.EmptyCartError
import shop.retry.{Retriable, Retry}
import shop.service.{OrderService, ShoppingCartService}
import squants.market.Money

import scala.concurrent.duration._
import scala.util.control.NoStackTrace

final case class Checkout[
    F[_]: MonadThrow: Retry: Logger: Temporal: Background
](
    payments: PaymentClient[F],
    cartService: ShoppingCartService[F],
    orders: OrderService[F]
) {
  // FIXME Make configurable
  val retryPolicy =
    limitRetries[F](3) |+| exponentialBackoff[F](10.milliseconds)

  private def processPayment(in: Payment): F[PaymentId] = Retry[F]
    .retry(retryPolicy, Retriable.Payments)(payments.process(in))
    .adaptError { case err =>
      PaymentError(Option(err.getMessage).getOrElse("UNKNOWN"))
    }

  private def createOrder(
      userId: UserId,
      paymentId: PaymentId,
      items: NonEmptyList[CartItem],
      total: Money
  ): F[OrderId] = {
    val action = Retry[F]
      .retry(retryPolicy, Retriable.Orders)(
        orders.create(userId, paymentId, items, total)
      )
      .adaptError { case e =>
        OrderError(e.getMessage)
      }

    def bgAction(fa: F[OrderId]): F[OrderId] = fa.onError { case _ =>
      Logger[F].error(
        s"Failed to create order for $paymentId"
      ) *> Background[F].schedule(bgAction(fa), 1.hour)
    }

    bgAction(action)
  }

  def process(userId: UserId, card: Card): F[OrderId] = {

    def ensureNonEmpty(items: List[CartItem]): F[NonEmptyList[CartItem]] =
      MonadThrow[F].fromOption(
        NonEmptyList.fromList(items),
        EmptyCartError
      )

    for {
      cart <- cartService.get(userId)
      items <- ensureNonEmpty(cart.items)
      paymentId <- payments.process(Payment(userId, cart.total, Card()))
      orderId <- orders.create(userId, paymentId, items, cart.total)
      _ <- cartService.delete(userId).attempt.void
    } yield orderId
  }
}

object Checkout {
  sealed trait CheckoutError extends NoStackTrace
  case object EmptyCartError extends CheckoutError
}
