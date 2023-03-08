import sbt._

object Dependencies {
  object Version {
    val cats = "2.6.1"
    val catsEffect = "3.1.1"
    val catsMtl = "1.2.1"
    val fs2 = "3.0.3"
    val monocle = "3.0.0"
    val refined = "0.10.2"
    val redis4cats = "1.4.0"
    val munit = "0.7.29"
  }

  object Libraries {
    val cats = "org.typelevel" %% "cats-core" % Version.cats
    val catsEffect = "org.typelevel" %% "cats-effect" % Version.catsEffect
    val catsMtl = "org.typelevel" %% "cats-mtl" % Version.catsMtl
    val fs2 = "co.fs2" %% "fs2-core" % Version.fs2
    val monocle = "dev.optics" %% "monocle-core" % Version.monocle
    val monocleMacro = "dev.optics" %% "monocle-macro" % Version.monocle
    val refined = "eu.timepit" %% "refined" % Version.refined
    val refinedCats = "eu.timepit" %% "refined-cats" % Version.refined
    val redis4Cats =
      "dev.profunktor" %% "redis4cats-effects" % Version.redis4cats
    val redis4CatsLogging =
      "dev.profunktor" %% "redis4cats-log4cats" % Version.redis4cats
    val munit = "org.scalameta" %% "munit" % Version.munit % Test
  }
}
