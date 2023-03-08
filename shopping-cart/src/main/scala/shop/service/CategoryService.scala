package shop.service

import shop.domain.category.Category
import shop.domain.category.CategoryName

val x: CategoryName = CategoryName("bac")
trait CategoryService[F[_]] {
  def findAll: F[List[Category]]
  def create(name: CategoryName): F[Category]
}
