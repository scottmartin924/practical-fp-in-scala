package shop.domain

import java.util.UUID
import squants.market.Money
import shop.domain.item.ItemId
import shop.domain.cart.Quantity

object order {
  object OrderId extends NewType[UUID]
  type OrderId = OrderId.Type

  object PaymentId extends NewType[UUID]
  type PaymentId = PaymentId.Type

  case class Order(
      id: OrderId,
      paymentId: PaymentId,
      items: Map[ItemId, Quantity],
      total: Money
  )
}
