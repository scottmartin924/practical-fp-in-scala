import Dependencies._

val scala3Version = "2.13.10"

lazy val root = project
  .in(file("."))
  .settings(
    name := "examples",
    version := "0.1.0-SNAPSHOT",
    scalaVersion := scala3Version,
    libraryDependencies ++= Seq(
      Libraries.cats,
      Libraries.catsEffect,
      Libraries.derevoCats,
      Libraries.derevoCirce,
      Libraries.derevoTagless,
      Libraries.fs2,
      Libraries.monocle,
      Libraries.monocleMacro,
      Libraries.newtype,
      Libraries.refined,
      Libraries.refinedCats,
      Libraries.redis4Cats,
      Libraries.redis4CatsLogging,
      Libraries.higherKind,
      Libraries.munit
    ),
    scalacOptions ++= Seq(
      "-Ymacro-annotations",
      "-Wconf:cat=unused:info"
    )
  )
