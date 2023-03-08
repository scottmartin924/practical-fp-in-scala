package shop.client

import shop.domain.payment.{Payment}

trait PaymentClient[F[_]] {
  // FIXME Implement once we setup more payment things
  // def process(payment: Payment): F[PaymentId]
}
