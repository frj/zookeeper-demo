package bootstrap.liftweb

import net.liftweb.http.LiftRules
import com.gu.zkdemo.switchboard.Switch
import org.apache.zookeeper.{WatchedEvent, Watcher, ZooKeeper}
import com.gu.zkdemo.switchboard.webapp.comet.SwitchboardServer

class Boot {

  def boot = {


    val zookeeper = new ZooKeeper("localhost:2181", 1000, new Watcher{def process(event: WatchedEvent): Unit = {}} )

    val switch1 = new Switch("Switch1", zookeeper, SwitchboardServer)
    val switch2 = new Switch("Switch2", zookeeper, SwitchboardServer)

    SwitchboardServer.switches = List(switch1, switch2)

    // where to search snippet
    LiftRules.addToPackages("com.gu.zkdemo.switchboard.webapp")


//    LiftRules.statelessDispatchTable
//      .append(EndpointUrls)
//      .append(Management.publishWithIndex(Healthcheck, Manifest, Status(TimingMetrics.all), Properties))

  }
}