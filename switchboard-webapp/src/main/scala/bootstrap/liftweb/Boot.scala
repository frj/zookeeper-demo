package bootstrap.liftweb

import net.liftweb.http.LiftRules

class Boot {

  def boot = {
    LiftRules.early.append(_.setCharacterEncoding("UTF-8"))

//    LiftRules.statelessDispatchTable
//      .append(EndpointUrls)
//      .append(Management.publishWithIndex(Healthcheck, Manifest, Status(TimingMetrics.all), Properties))

  }
}