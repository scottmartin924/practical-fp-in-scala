package shop.domain

import io.estatico.newtype.macros.newtype
import shop.domain.brand.{Brand, BrandId}
import shop.domain.category.{Category, CategoryId}
import squants.market.Money

import java.util.UUID

object item {
  @newtype case class ItemId(value: UUID)
  @newtype case class ItemName(value: String)
  @newtype case class ItemDescription(value: String)

  case class Item(
      id: ItemId,
      name: ItemName,
      description: ItemDescription,
      price: Money,
      brand: Brand,
      category: Category
  )

  case class CreateItem(
      name: ItemName,
      description: ItemDescription,
      price: Money,
      brandId: BrandId,
      categoryId: CategoryId
  )

// NOTE: In this model the only updatable field on an item is the price (seems odd)
  case class UpdateItem(id: ItemId, price: Money)
}
