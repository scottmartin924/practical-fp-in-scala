package shop.program

import shop.client.PaymentClient
import shop.service.ShoppingCartService
import shop.service.OrderService
import cats.Monad
import shop.domain.user.UserId
import shop.domain.order.OrderId
import shop.domain.payment.Card
import cats.syntax.all.*
import shop.domain.cart.CartItem
import cats.data.NonEmptyList
import cats.MonadThrow
import org.typelevel.log4cats.Logger

import scala.util.control.NoStackTrace
import shop.program.Checkout.EmptyCartError
import shop.domain.payment.Payment
import shop.domain.order.PaymentId
import retry.*
import retry.RetryPolicies.*

import concurrent.duration.*
import shop.retry.Retry
import shop.retry.Retriable
import shop.domain.payment.*
import shop.domain.payment.OrderError
import squants.market.Money
import cats.effect.Temporal
import retry.RetryPolicy
import retry.RetryDetails
import retry.RetryDetails.WillDelayAndRetry
import org.typelevel.log4cats.Logger
import shop.effects.Background

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
