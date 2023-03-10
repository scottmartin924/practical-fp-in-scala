package shop.program

import shop.client.PaymentClient
import shop.service.ShoppingCartService
import shop.service.OrderService
import cats.Monad
import shop.domain.user.UserId
import shop.domain.order.OrderId
import shop.domain.payment.Card
import cats.syntax.all._
import shop.domain.cart.CartItem
import cats.data.NonEmptyList
import cats.MonadThrow
import scala.util.control.NoStackTrace
import shop.program.Checkout.EmptyCartError
import shop.domain.payment.Payment
import shop.domain.order.PaymentId
import retry._
import retry.RetryPolicies._
import concurrent.duration._
import shop.retry.Retry
import shop.retry.Retriable
import shop.domain.payment._
import squants.market.Money

final case class Checkout[F[_]: MonadThrow: Retry](
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
      // FIXME Map to custom error type
      PaymentError(Option(err.getMessage).getOrElse("UNKNOWN"))
    }

  // private def createOrder(
  //     usrId: UserId,
  //     paymentId: PaymentId,
  //     items: NonEmptyList[CartItem],
  //     total: Money
  // ): F[OrderId] = {
  //   val
  // }

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
