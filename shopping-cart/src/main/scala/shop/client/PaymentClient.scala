package shop.client

import shop.domain.order.PaymentId
import shop.domain.payment.Payment

trait PaymentClient[F[_]] {
  // FIXME Implement once we setup more payment things
  // FIXME Make paymentid
  def process(payment: Payment): F[PaymentId]
}
