package shop.domain

import java.io.ObjectInputFilter.Status
import monocle.Iso
import io.circe.Encoder

object healthcheck {

  object RedisStatus extends NewType[Status]
  type RedisStatus = RedisStatus.Type

  object PostgresStatus extends NewType[Status]
  type PostgresStatus = PostgresStatus.Type

  case class AppStatus(redis: RedisStatus, postgres: PostgresStatus)

  sealed trait Status
  object Status {
    case object Okay extends Status
    case object Unreachable extends Status

    val _Bool: Iso[Status, Boolean] = Iso[Status, Boolean] {
      case Okay        => true
      case Unreachable => false
    }(if (_) Okay else Unreachable)
  }

  given encoder: Encoder[Status] = Encoder.forProduct1("status")(_.toString())
}
