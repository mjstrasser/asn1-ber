lazy val commonSettings = Seq(
  organization := "michaelstrasser.com",
  version := "0.0.2",
  scalaVersion := "2.11.6"
)

lazy val root = (project in file(".")).
  settings(commonSettings: _*).
  settings(
    name := "ASN.1 BER library in Scala"
  )

libraryDependencies ++= {
  Seq(
    "org.scalatest" % "scalatest_2.11" % "2.2.1" % "test"
  )
}
