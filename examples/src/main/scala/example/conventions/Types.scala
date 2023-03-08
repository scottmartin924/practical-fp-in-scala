package example.conventions

import eu.timepit.refined.api.Refined
import eu.timepit.refined.collection.Contains

object Types {
  // Opaque type means Username = String ONLY in scope defined (so in the Types object)
  // This is raw then validation done in Username.makeUsername...could also use somethign like refined
  opaque type Username = String

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
        s,
        UsernameInvalidError(s, s"Missing required character $requiredValue")
      )
    }
  }

  // Extension methods for username...these are just random samples
  extension (u: Username) {
    // only works b/c in this scope Username = String
    def toString = u
    // random thing just to show the idea
    def name: String = u.split("@")(0)
  }
}
