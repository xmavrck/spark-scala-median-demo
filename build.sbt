lazy val root = (project in file(".")).
  settings(
    name := "DemoMedian",
    version := "1.0",
    scalaVersion := "2.11.8",
    mainClass in Compile := Some("com.xenonstack.Main")
  )

libraryDependencies ++= {
  Seq(
    "org.apache.spark" % "spark-sql_2.11" % "2.1.0"
  )

}

assemblyMergeStrategy in assembly := {
  case PathList("META-INF", xs@_*) => MergeStrategy.discard
  case PathList("reference.conf") => MergeStrategy.concat
  case x => MergeStrategy.first
}
