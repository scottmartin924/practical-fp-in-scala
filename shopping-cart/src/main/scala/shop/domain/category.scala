package shop.domain

import java.util.UUID

object category {

// This would be the raw way of handling the opaque types...trying with the NewType too
// opaque type CategoryId = UUID
// def CategoryId(id: UUID): CategoryId = id
  object CategoryId extends NewType[UUID]
  type CategoryId = CategoryId.Type

  object CategoryName extends NewType[String]
  type CategoryName = CategoryName.Type

  case class Category(id: CategoryId, name: CategoryName)
}
