package shop.domain

import shop.domain.user.UserId
import squants.market.Money

object payment {
  case class Payment(
      id: UserId,
      total: Money
      // FIXME Add card
      //  card: Card
  )
}
