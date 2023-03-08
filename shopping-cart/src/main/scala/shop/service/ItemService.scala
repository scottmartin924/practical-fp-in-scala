package shop.service

import shop.domain.item.Item
import shop.domain.brand.BrandId
import shop.domain.item.ItemId
import shop.domain.item.CreateItem
import shop.domain.item.UpdateItem

trait ItemService[F[_]] {
  def findAll: F[List[Item]]
  def findByBrand(brand: BrandId): F[List[Item]]
  def findById(id: ItemId): F[Option[Item]]
  def create(item: CreateItem): F[Item]
  def update(item: UpdateItem): F[Item]
}
