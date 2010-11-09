import sbt._

class ServerProject(info: ProjectInfo) extends DefaultProject(info) with AkkaProject {
  val akkaCamel = akkaModule("camel")
  val akkaKernel = akkaModule("kernel")

  val camelJetty = "org.apache.camel" % "camel-jetty" % "2.4.0"

  override def mainClass = Some("se.scalablesolutions.akka.kernel.Main")
}
