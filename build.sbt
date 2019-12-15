name := "kafka-avro-udemy"

organization := "com.github.vinyasshetty"

version := "0.1"

scalaVersion := "2.12.10"

libraryDependencies ++= Seq("org.apache.avro" % "src/main/avro" % "1.9.1")

AvroConfig / stringType := "String"