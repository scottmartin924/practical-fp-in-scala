package shop.domain

import squants.market.Money
import shop.domain.item.{ItemId, Item}

object cart {
  object Quantity extends NewType[Int]
  type Quantity = Quantity.Type

  object Cart extends NewType[Map[ItemId, Quantity]]
  type Cart = Cart.Type

  case class CartItem(item: Item, quantity: Quantity)
  case class CartTotal(items: List[CartItem], total: Money)
}
