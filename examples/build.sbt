import Dependencies._

val scala3Version = "3.2.2"

lazy val root = project
  .in(file("."))
  .settings(
    name := "examples",
    version := "0.1.0-SNAPSHOT",
    scalaVersion := scala3Version,
    libraryDependencies ++= Seq(
      Libraries.cats,
      Libraries.catsEffect,
      Libraries.fs2,
      Libraries.monocle,
      Libraries.monocleMacro,
      Libraries.refined,
      Libraries.refinedCats,
      Libraries.redis4Cats,
      Libraries.redis4CatsLogging,
      Libraries.munit
    ),
    scalacOptions ++= Seq(
      "-Wconf:cat=unused:info"
    )
  )
