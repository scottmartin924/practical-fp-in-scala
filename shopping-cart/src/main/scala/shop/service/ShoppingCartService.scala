package shop.service

import shop.domain.item.ItemId
import shop.domain.cart.Quantity
import shop.domain.user.UserId
import shop.domain.cart.CartTotal
import shop.domain.cart.Cart

trait ShoppingCartService[F[_]] {
  def add(userId: UserId, itemId: ItemId, quantity: Quantity): F[Unit]
  def get(userId: UserId): F[CartTotal]
  def delete(userId: UserId): F[Unit]
  def removeItem(userId: UserId, itemId: ItemId): F[Unit]
  def update(userId: UserId, cart: Cart): F[CartTotal]
}
