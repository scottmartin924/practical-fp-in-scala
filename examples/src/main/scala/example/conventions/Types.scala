package example.conventions

import io.estatico.newtype.macros.newtype

object Types {
  @newtype final case class Username(value: String)

  sealed trait ValidationError
  case class UsernameInvalidError(username: String, message: String)
      extends ValidationError

  object Username {
    // Arbitrarily say that all usernames need an @ (maybe it's an email?...really just a sample)
    // NOTE: The lack of an apply method forces all Username creation to go through makeUsername
    def makeUsername(s: String): Either[ValidationError, Username] = {
      val requiredValue = "@"
      Either.cond(
        s.contains(requiredValue),
        Username(s),
        UsernameInvalidError(s, s"Missing required character $requiredValue")
      )
    }
  }
}
