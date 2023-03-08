package shop.service

import shop.domain.user.UserName
import shop.domain.auth.{JwtToken}
import shop.domain.user.{User, Password}

trait Auth[F[_]] {
  def findUser(token: JwtToken): F[Option[User]]
  def newUser(username: UserName, password: Password): F[JwtToken]
  def login(username: UserName, password: Password): F[JwtToken]
  def logout(token: JwtToken, username: UserName): F[Unit]
}
