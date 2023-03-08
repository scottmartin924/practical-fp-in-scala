package shop.service

import shop.domain.user.UserId
import shop.domain.order.{Order, OrderId, PaymentId}
import cats.data.NonEmptyList
import shop.domain.cart.CartItem
import squants.market.Money

trait OrderService[F[_]] {
  def get(userId: UserId, orderId: OrderId): F[Option[Order]]

  def findByUser(userId: UserId): F[List[Order]]

  def create(
      userId: UserId,
      paymentId: PaymentId,
      items: NonEmptyList[CartItem],
      total: Money
  ): F[OrderId]
}
