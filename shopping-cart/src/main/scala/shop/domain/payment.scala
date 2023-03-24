package shop.domain

import shop.domain.user.UserId
import squants.market.Money
import scala.util.control.NoStackTrace

object payment {
  case class Payment(
      id: UserId,
      total: Money,
      card: Card
  )

  // FIXME Just here as a placeholder until create implementation
  case class Card()

  // FIXME just a placeholder shouldn't be here
  trait PaymentOrOrderError extends NoStackTrace
  case class PaymentError(error: String) extends PaymentOrOrderError
  case class OrderError(error: String) extends PaymentOrOrderError
}
