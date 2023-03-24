import Dependencies._

val scala2Version = "2.13.10"

ThisBuild / scalaVersion := scala2Version
ThisBuild / version := "0.0.1"

lazy val root = project
  .in(file("."))
  .settings(
    name := "shopping-cart",
    version := "0.1.0-SNAPSHOT",
    scalaVersion := scala2Version,
    libraryDependencies ++= Seq(
      Libraries.cats,
      Libraries.catsEffect,
      Libraries.catsRetry,
      Libraries.circeCore,
      Libraries.circeGeneric,
      Libraries.circeParser,
      Libraries.derevoCats,
      Libraries.derevoCirce,
      Libraries.derevoTagless,
      Libraries.fs2,
      Libraries.http4sDsl,
      Libraries.http4sClient,
      Libraries.http4sServer,
      Libraries.http4sCirce,
      Libraries.monocle,
      Libraries.monocleMacro,
      Libraries.newtype,
      Libraries.refined,
      Libraries.refinedCats,
      Libraries.redis4Cats,
      Libraries.redis4CatsLogging,
      Libraries.sqaunts
    ),
    scalacOptions ++= Seq(
      "-Ymacro-annotations",
      "-Wconf:cat=unused:info"
    )
  )
