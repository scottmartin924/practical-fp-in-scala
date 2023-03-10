package shop.retry

sealed trait Retriable
object Retriable {
  case object Orders extends Retriable
  case object Payments extends Retriable
}
