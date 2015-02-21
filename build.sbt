organization  := "com.hoppyfrog"

version       := "0.1"

resolvers     += "spray" at "http://repo.spray.io/"

scalaVersion  := "2.11.5"

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8")

libraryDependencies ++= {
  val akkaV = "2.3.6"
  val sprayV = "1.3.2"
  Seq(
    "io.spray"            %%  "spray-can"     % sprayV,
    "io.spray"            %%  "spray-routing" % sprayV,
    "io.spray"            %%  "spray-json"    % "1.3.1",
    "org.mongodb"         %%  "casbah"        % "2.8.0",
    "com.novus"           %%  "salat"         % "1.9.9",
    "io.spray"            %%  "spray-testkit" % sprayV  % "test",
    "com.typesafe.akka"   %%  "akka-actor"    % akkaV,
    "com.typesafe.akka"   %%  "akka-testkit"  % akkaV   % "test",
    "org.specs2"          %%  "specs2-core"   % "2.3.11" % "test",
    "org.scalaj"          %%  "scalaj-http"   % "1.1.4"
  )
}

Revolver.settings

