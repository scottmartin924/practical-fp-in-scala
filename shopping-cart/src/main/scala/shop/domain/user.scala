package shop.domain

import java.util.UUID

object user {

  object UserId extends NewType[UUID]
  type UserId = UserId.Type

  object UserName extends NewType[String]
  type UserName = UserName.Type

  object Password extends NewType[String]
  type Password = Password.Type

  object EncryptedPassword extends NewType[String]
  type EncryptedPassword = EncryptedPassword.Type

  case class User(id: UserId, name: UserName)

  case class UserWithPassword(
      id: UserId,
      name: UserName,
      password: EncryptedPassword
  )
}
