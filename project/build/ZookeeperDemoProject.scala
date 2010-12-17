import sbt._

class ZookeeperDemoProject(info: ProjectInfo) extends DefaultProject(info) {

  lazy val server = project("server", "server", new Server(_))
  lazy val switchboardWebapp = project("switchboard-webapp", "switchboard-webapp", new SwitchboardWebapp(_))
  lazy val zookeeper = project("zookeeper", "zookeeper", new Zookeeper(_))

  class Server(info: ProjectInfo) extends DefaultProject(info) {
  }

  class SwitchboardWebapp(info: ProjectInfo) extends DefaultWebProject(info) with Lift with Jetty{
  }

  class Zookeeper(info: ProjectInfo) extends DefaultProject(info) {
    val zookeeperDep = "org.apache.zookeeper" % "zookeeper" % "3.3.2" withSources ()
    override def ivyXML =
      <dependencies>
        <dependency org="org.apache.zookeeper" name="zookeeper" rev="3.3.2">
          <exclude artifact="jms"/>
          <exclude artifact="jmxtools"/>
          <exclude artifact="jmxri"/>
        </dependency>
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


}