import sbt._

object Dependencies {
  object Version {
    val cats = "2.7.0"
    val catsEffect = "3.3.12"
    val catsMtl = "1.2.1"
    val catsRetry = "3.1.0"
    val circe = "0.14.2"
    val derevo = "0.13.0"
    val fs2 = "3.0.3"
    val http4s = "0.23.1"
    val http4sJwt = "1.0.0"
    val monocle = "3.1.0"
    val munit = "0.7.29"
    val newtype = "0.4.4"
    val refined = "0.10.2"
    val redis4cats = "1.4.0"
    val squants = "1.8.3"

    def kindProjector = "0.13.2"
  }

  object Libraries {
    def circe(artifactName: String) =
      "io.circe" %% artifactName % Version.circe

    def http4s(artifactName: String) =
      "org.http4s" %% s"http4s-$artifactName" % Version.http4s

    val cats = "org.typelevel" %% "cats-core" % Version.cats
    val catsEffect = "org.typelevel" %% "cats-effect" % Version.catsEffect
    val catsMtl = "org.typelevel" %% "cats-mtl" % Version.catsMtl
    val catsRetry = "com.github.cb372" %% "cats-retry" % Version.catsRetry
    val circeCore = circe("circe-core")
    val circeGeneric = circe("circe-generic")
    val circeParser = circe("circe-parser")
    val derevoCats = "tf.tofu" %% "derevo-cats" % Version.derevo
    val derevoTagless = "tf.tofu" %% "derevo-cats-tagless" % Version.derevo
    val derevoCirce = "tf.tofu" %% "derevo-circe-magnolia" % Version.derevo
    val fs2 = "co.fs2" %% "fs2-core" % Version.fs2
    val http4sDsl = http4s("dsl")
    val http4sServer = http4s("ember-server")
    val http4sClient = http4s("ember-client")
    val http4sCirce = http4s("circe")
    val http4sJwt = ""
    val monocle = "dev.optics" %% "monocle-core" % Version.monocle
    val monocleMacro = "dev.optics" %% "monocle-macro" % Version.monocle
    val newtype = "io.estatico" %% "newtype" % Version.newtype
    val refined = "eu.timepit" %% "refined" % Version.refined
    val refinedCats = "eu.timepit" %% "refined-cats" % Version.refined
    val redis4Cats =
      "dev.profunktor" %% "redis4cats-effects" % Version.redis4cats
    val redis4CatsLogging =
      "dev.profunktor" %% "redis4cats-log4cats" % Version.redis4cats
    val sqaunts = "org.typelevel" %% "squants" % Version.squants
  }
}
