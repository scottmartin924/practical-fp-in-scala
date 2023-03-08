import Dependencies._

val scala3Version = "3.2.2"

ThisBuild / scalaVersion := scala3Version
ThisBuild / version := "0.0.1"

lazy val root = project
  .in(file("."))
  .settings(
    name := "shopping-cart",
    version := "0.1.0-SNAPSHOT",
    scalaVersion := scala3Version,
    libraryDependencies ++= Seq(
      Libraries.cats,
      Libraries.catsEffect,
      Libraries.circeCore,
      Libraries.circeGeneric,
      Libraries.circeParser,
      Libraries.fs2,
      Libraries.monocle,
      Libraries.monocleMacro,
      Libraries.refined,
      Libraries.refinedCats,
      Libraries.redis4Cats,
      Libraries.redis4CatsLogging,
      Libraries.sqaunts
    ),
    scalacOptions ++= Seq(
      "-Wconf:cat=unused:info"
    )
  )
