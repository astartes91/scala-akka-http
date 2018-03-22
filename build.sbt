name := "lectures"
organization := "ru.tinkoff.fintech"
version := "0.0.1"

scalaVersion in ThisBuild := "2.12.3"

libraryDependencies ++= Seq(
  // config
  "com.typesafe" % "config" % "1.3.0"

  // json
  , "io.spray" %%  "spray-json" % "1.3.3"

  // logging
  , "org.slf4j" % "slf4j-api" % "1.7.12"
  , "ch.qos.logback" % "logback-classic" % "1.1.3"

  // db
  //, "org.postgresql" % "postgresql" % "9.4-1201-jdbc41"
  //, "io.ratpack" % "ratpack-hikari" % "0.9.18"
  //,  "org.xerial" % "sqlite-jdbc" % "3.20.1"

  // akka
  , "com.typesafe.akka" %% "akka-http" % "10.0.9"
  , "com.typesafe.akka" %% "akka-http-spray-json" % "10.0.10"

  // testing
  , "org.scalatest" %% "scalatest" % "3.0.4" % "test"
  , "org.mockito" % "mockito-core" % "1.9.5" % "test"
)

assemblyMergeStrategy in assembly := {
  case PathList("javax", "ws", _@_*) => MergeStrategy.last
  case x if x.endsWith("io.netty.versions.properties") => MergeStrategy.first
  case x =>
    val oldStrategy = (assemblyMergeStrategy in assembly).value
    oldStrategy(x)
}

