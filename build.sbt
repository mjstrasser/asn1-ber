lazy val commonSettings = Seq(
  organization := "michaelstrasser.com",
  version := "0.0.1",
  scalaVersion := "2.11.6"
)

lazy val root = (project in file(".")).
  settings(commonSettings: _*).
  settings(
    name := "ASN1. BER library in Scala"
  )

libraryDependencies ++= {
  val akkaVersion = "2.3.9"
  Seq(
    "com.typesafe.akka" % "akka-actor_2.11" % akkaVersion,
    "org.scalatest" % "scalatest_2.11" % "2.2.1" % "test"
  )
}
