sbtPlugin := true

name := "sbt-protobuf"

organization := "com.qifun"

scalacOptions += "-deprecation"

scalacOptions += "-feature"

version := "0.1.0-SNAPSHOT"

description := "A Sbt plugin used to compile google protobuf files."

homepage := Some(url(s"https://github.com/qifun/${name.value}"))

startYear := Some(2014)

licenses := Seq("Apache License, Version 2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0.html"))

publishTo <<= (isSnapshot) { isSnapshot: Boolean =>
  if (isSnapshot)
    Some("snapshots" at "https://oss.sonatype.org/content/repositories/snapshots")
  else
    Some("releases" at "https://oss.sonatype.org/service/local/staging/deploy/maven2")
}

scmInfo := Some(ScmInfo(
  url(s"https://github.com/qifun/${name.value}"),
  s"scm:git:git://github.com/qifun/${name.value}.git",
  Some(s"scm:git:git@github.com:qifun/${name.value}.git")))

pomExtra :=
  <developers>
    <developer>
      <id>chank</id>
      <name>方里权</name>
      <timezone>+8</timezone>
      <email>fangliquan@qq.com</email>
    </developer>
  </developers>
