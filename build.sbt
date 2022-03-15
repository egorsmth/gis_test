name := "gis_test"

version := "0.1"

scalaVersion := "2.13.8"

val http4sVersion = "0.23.10"


libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.2.11" % "test",
  "org.typelevel" %% "cats-core" % "2.7.0",
  "org.typelevel" %% "cats-effect-testing-scalatest" % "1.4.0" % Test,
  "org.http4s" %% "http4s-dsl" % http4sVersion,
  "org.http4s" %% "http4s-blaze-server" % http4sVersion,
  "org.http4s" %% "http4s-blaze-client" % http4sVersion,
  "org.http4s" %% "http4s-circe" % http4sVersion,
  "io.circe" %% "circe-generic" % "0.14.1",
  "org.slf4j" % "slf4j-simple" % "1.7.36",
  "net.ruippeixotog" %% "scala-scraper" % "2.2.1"
)