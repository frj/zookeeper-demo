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
    val switch3 = new Switch("Switch3", zookeeper, SwitchboardServer)
    val switch4 = new Switch("Switch4", zookeeper, SwitchboardServer)
    val switch5 = new Switch("Switch5", zookeeper, SwitchboardServer)
    val switch6 = new Switch("Switch6", zookeeper, SwitchboardServer)
    val switch7 = new Switch("Switch7", zookeeper, SwitchboardServer)

    SwitchboardServer.switches = List(switch1, switch2, switch3, switch4, switch5, switch6, switch7)

    // where to search snippet
    LiftRules.addToPackages("com.gu.zkdemo.switchboard.webapp")


//    LiftRules.statelessDispatchTable
//      .append(EndpointUrls)
//      .append(Management.publishWithIndex(Healthcheck, Manifest, Status(TimingMetrics.all), Properties))

  }
}