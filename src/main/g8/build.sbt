import com.typesafe.sbt.packager.docker.DockerVersion

name := "$name$"

organization := "$organization$"

scalaVersion := "2.13.1"

version := "0.1.3"

libraryDependencies ++= Seq(
  "com.typesafe.scala-logging" %% "scala-logging"           % "3.9.2",
  "ch.qos.logback"             % "logback-classic"          % "1.2.3",
  "org.scalatest"              %% "scalatest"               % "3.0.8" % "it,test",
)

// testing configuration
fork in Test := true
parallelExecution := false

// SBT Native Packager plugin
enablePlugins(JavaAppPackaging)
enablePlugins(AshScriptPlugin)
packageName := "$name$"
packageSummary := ""
maintainer := "$author_name$"
dockerBaseImage := "openjdk:12-alpine"
dockerUpdateLatest := true
// We tell the package plugin that this version is installed. Pipelines is running Moby
// which screws up the output of `docker version` which is used by the plugin internally.
// The version is only used for feature discovery and is not binding per se.
dockerVersion := Some(DockerVersion(18, 9, 0, None))
dockerRepository := sys.env.get("REGISTRYSERVERNAME").orElse(Some(""))

// Add extra tags to Docker image
val gitCommitString = SettingKey[Option[String]]("commit")
gitCommitString := git.gitHeadCommit.value
dockerAliases ++= Seq(
  dockerAlias.value.withTag(gitCommitString.value)
)