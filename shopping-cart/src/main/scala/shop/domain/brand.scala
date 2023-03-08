package shop.domain

import java.util.UUID

object brand {
// Frustrating to have to do the type alias...hmmm newtype might still be easier??
  object BrandId extends NewType[UUID]
  type BrandId = BrandId.Type

  object BrandName extends NewType[String]
  type BrandName = BrandName.Type

  case class Brand(id: BrandId, name: BrandName)
}
