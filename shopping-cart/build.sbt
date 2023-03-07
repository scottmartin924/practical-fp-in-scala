val scala3Version = "3.2.2"

ThisBuild / scalaVersion := scala3Version
ThisBuild / version := "0.0.1"
ThisBuild / organization := "com.example"

lazy val root = project
  .in(file("."))
  .settings(
    name := "shopping-cart",
    version := "0.1.0-SNAPSHOT",
    scalaVersion := scala3Version,
    libraryDependencies ++= Seq(
      "org.typelevel" %% "" % "",
      "org.scalameta" %% "munit" % "0.7.29" % Test
    )
  )
