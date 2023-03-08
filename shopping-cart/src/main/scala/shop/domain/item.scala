package shop.domain

import java.util.UUID
import squants.market.Money

import shop.domain.brand.{BrandId, Brand}

import shop.domain.category.{CategoryId, Category}

object item {

  object ItemId extends NewType[UUID]
  type ItemId = ItemId.Type

  object ItemName extends NewType[String]
  type ItemName = ItemId.Type

  object ItemDescription extends NewType[String]
  type ItemDescription = ItemDescription.Type

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

// NOTE: In this model the only updateable field on an item is the price (seems odd)
  case class UpdateItem(id: ItemId, price: Money)
}
