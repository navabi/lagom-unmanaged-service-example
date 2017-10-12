organization in ThisBuild := "ir.mohsennavabi"
version in ThisBuild := "1.0-SNAPSHOT"

// the Scala version that will be used for cross-compiled libraries
scalaVersion in ThisBuild := "2.11.8"

val macwire = "com.softwaremill.macwire" %% "macros" % "2.2.5" % "provided"
val scalaTest = "org.scalatest" %% "scalatest" % "3.0.1" % Test

lagomUnmanagedServices in ThisBuild := Map("external-service" -> "http://services.groupkt.com")


lazy val `lagomunmanagedservices-example` = (project in file("."))
  .aggregate(`lagom-hello-api`, `lagom-hello-impl`,`external-service-api`,`external-service-impl`)

lazy val `lagom-hello-api` = (project in file("lagom-hello-api"))
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslApi
    )
  )

lazy val `external-service-api` = (project in file("external-service-api"))
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslApi
    )
  )
  .dependsOn(`lagom-hello-api`)


lazy val `lagom-hello-impl` = (project in file("lagom-hello-impl"))
  .enablePlugins(LagomScala)
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslPersistenceCassandra,
      lagomScaladslKafkaBroker,
      lagomScaladslTestKit,
      macwire,
      scalaTest
    )
  )
  .settings(lagomForkedTestSettings: _*)
  .dependsOn(`lagom-hello-api`)


lazy val `external-service-impl` = (project in file("external-service-impl"))
  .enablePlugins(LagomScala)
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslTestKit,
      macwire,
      scalaTest
    )
  )
  .settings(lagomForkedTestSettings: _*)
  .dependsOn(`external-service-api`)