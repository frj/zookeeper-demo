import sbt._

class ZookeeperDemoProject(info: ProjectInfo) extends DefaultProject(info) {

  lazy val server = project("server", "server", new Server(_))
  lazy val switchboardWebapp = project("switchboard-webapp", "switchboard-webapp", new SwitchboardWebapp(_))
  lazy val zookeeper = project("zookeeper", "zookeeper", new Zookeeper(_))

  class Server(info: ProjectInfo) extends DefaultProject(info) with Logback{
    override def ivyXML =
      <dependencies>
        <exclude module="log4j"/>
        <exclude module="slf4j-log4j12"/>
      </dependencies>
  }

  class SwitchboardWebapp(info: ProjectInfo) extends DefaultWebProject(info) with Lift with Jetty with Logback{
    val zookeeperDep = "org.apache.zookeeper" % "zookeeper" % "3.3.2" withSources ()
    override def ivyXML =
      <dependencies>
        <exclude artifact="jms"/>
        <exclude artifact="jmxtools"/>
        <exclude artifact="jmxri"/>
        <exclude module="log4j"/>
        <exclude module="slf4j-log4j12"/>
      </dependencies>

  }

  class Zookeeper(info: ProjectInfo) extends DefaultProject(info) {
    val zookeeperDep = "org.apache.zookeeper" % "zookeeper" % "3.3.2" withSources ()
    override def ivyXML =
      <dependencies>
        <exclude artifact="jms"/>
        <exclude artifact="jmxtools"/>
        <exclude artifact="jmxri"/>
      </dependencies>

    override def mainClass = Some("org.apache.zookeeper.server.quorum.QuorumPeerMain")
  }

  trait Lift extends BasicScalaProject {
    val liftVersion = "2.1"

    val liftActor = "net.liftweb" %% "lift-actor" % liftVersion withSources ()
    val liftCommon = "net.liftweb" %% "lift-common" % liftVersion withSources ()
    val liftJson = "net.liftweb" %% "lift-json" % liftVersion withSources ()
    val liftUtil = "net.liftweb" %% "lift-util" % liftVersion withSources ()
    val liftWebkit = "net.liftweb" %% "lift-webkit" % liftVersion withSources ()
  }

  trait Jetty {
    val servletApi = "javax.servlet" % "servlet-api" % "2.5" % "provided" withSources ()
    val jetty7 = "org.eclipse.jetty" % "jetty-webapp" % "7.0.2.v20100331" % "test"
  }

  trait Logback extends BasicScalaProject {
    val SLF4J_VERSION = "1.6.0"

    val log4jOverSlf4j = "org.slf4j" % "log4j-over-slf4j" % SLF4J_VERSION withSources()
    val slf4jApi = "org.slf4j" % "slf4j-api" % SLF4J_VERSION withSources()
    val logback = "ch.qos.logback" % "logback-classic" % "0.9.24" withSources()
  }
}