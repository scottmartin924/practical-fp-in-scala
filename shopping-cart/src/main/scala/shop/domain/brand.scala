package shop.domain

import derevo.cats.{eqv, show}
import derevo.circe.magnolia.{decoder, encoder}
import derevo.derive
import io.estatico.newtype.macros.newtype

import java.util.UUID

object brand {
  @derive(encoder, decoder, eqv, show)
  @newtype case class BrandId(value: UUID)

  @derive(encoder, decoder, eqv, show)
  @newtype case class BrandName(value: String)

  @derive(encoder, decoder, eqv, show)
  case class Brand(id: BrandId, name: BrandName)
}
