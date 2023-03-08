package shop.domain

object auth {
  object JwtToken extends NewType[String]
  type JwtToken = JwtToken.Type
}
