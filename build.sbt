import sbtcrossproject.CrossPlugin.autoImport.crossProject

val commonSettings = Seq(
  name := "scala-stripe",
  organization := "com.outr",
  version := "1.1.31",
  scalaVersion := "3.6.2",
  scalacOptions ++= Seq("-Xmax-inlines", "128"),
  resolvers += Resolver.sonatypeRepo("releases"),

  publishTo := sonatypePublishTo.value,
  sonatypeProfileName := "com.outr",
  publishMavenStyle := true,
  licenses := Seq("MIT" -> url("https://github.com/harana-oss/scala-stripe/blob/master/LICENSE")),
  sonatypeProjectHosting := Some(xerial.sbt.Sonatype.GitHubHosting("outr", "scala-stripe", "matt@outr.com")),
  homepage := Some(url("https://github.com/harana-oss/scala-stripe")),
  scmInfo := Some(
    ScmInfo(
      url("https://github.com/harana-oss/scala-stripe"),
      "scm:git@github.com:harana-oss/scala-stripe.git"
    )
  ),
  developers := List(
    Developer(id="darkfrog", name="Matt Hicks", email="matt@matthicks.com", url=url("http://matthicks.com"))
  )
)
developers in ThisBuild := List(
  Developer(id="darkfrog", name="Matt Hicks", email="matt@matthicks.com", url=url("http://matthicks.com"))
)

lazy val root = project.in(file("."))
  .aggregate(coreJS, coreJVM)
  .settings(
    publish := {},
    publishLocal := {}
  )

lazy val core = crossProject(JVMPlatform, JSPlatform).in(file("core"))
  .settings(
    commonSettings,
    libraryDependencies ++= Seq(
      "io.circe" %%% "circe-core" % "0.14.10",
      "io.circe" %%% "circe-generic" % "0.14.10",
      "io.circe" %%% "circe-parser" % "0.14.10",
      "org.scalactic" %%% "scalactic" % "3.2.19",
      "org.scalatest" %%% "scalatest" % "3.2.19" % "test",
      "org.scalatest" %% "scalatest-wordspec" % "3.2.19" % "test",
      "org.scalatest" %%% "scalatest-matchers-core" % "3.2.19" % "test",
      "com.outr" %%% "profig" % "3.4.16" % "test"
    )
  )
  .jvmSettings(
    commonSettings,
    fork := true,
    libraryDependencies ++= Seq(
      "com.softwaremill.sttp.client3" %% "core" % "3.10.1",
      "com.softwaremill.sttp.client3" %% "circe" % "3.10.1"
    )
  )
  .jsSettings(
    commonSettings,
    jsEnv := new org.scalajs.jsenv.jsdomnodejs.JSDOMNodeJSEnv(),
    libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "2.8.0"
  )

lazy val coreJS = core.js
lazy val coreJVM = core.jvm
