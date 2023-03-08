package shop.service
import shop.domain.brand.Brand
import shop.domain.brand.BrandName

trait BrandService[F[_]] {
  def findAll: F[List[Brand]]
  def create(name: BrandName): F[Brand]
}
