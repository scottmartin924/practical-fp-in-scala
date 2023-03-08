package shop.service

import shop.domain.user.UserName
import shop.domain.user.UserWithPassword
import shop.domain.user.EncryptedPassword
import shop.domain.user.UserId

trait UserService[F[_]] {
  def find(username: UserName): F[Option[UserWithPassword]]

  def create(username: UserName, password: EncryptedPassword): F[UserId]
}
