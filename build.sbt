scalaVersion := "2.12.8"

name := "slick-h2-instant-bug"
version := "0.1"

libraryDependencies ++= Seq(
  "com.h2database" % "h2" % "1.4.199",
  "com.typesafe.slick" %% "slick" % "3.3.2",
  "com.typesafe.slick" %% "slick-hikaricp" % "3.3.2",
  "com.zaxxer" % "HikariCP" % "3.3.1",
  "ch.qos.logback" % "logback-classic" % "1.2.3" % Runtime
)
