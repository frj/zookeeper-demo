package com.gu.zkdemo.switchboard.webapp.comet

import net.liftweb._
import http._
import actor._
import com.gu.zkdemo.switchboard.{Logging, Switch}
import js.{JE, JsCmds}

object SwitchboardServer extends LiftActor with ListenerManager {
  var switches = List[Switch]()

  def createUpdate = switches

  updateListeners

  override def lowPriority = {
    case _ => updateListeners()
 }
}

class Switchboard extends CometActor with CometListener with Logging {

  log.info("Switchboard created")

  def registerWith = SwitchboardServer

  override def lowPriority = {
    case _ => {
      log.info("Switchboard got message")
      reRender(false)
    }
  }

  def render = {
    <div>
      <lift:form>
        <ul>
        {
          SwitchboardServer.switches map {
            switch =>
              <li>
                <b>{switch.name}</b>
                {
                  val currentValue = switch.value
                  val newValue = switch.value match {
                    case "on" => "off"
                    case "off" => "on"
                  }

                  log.info(switch.name + " currentValue " + currentValue)

                  SHtml.ajaxButton(currentValue, () => {
                    switch.changeValue(newValue);
                    JsCmds._Noop
                  })
                }
              </li>
          }
        }
        </ul>
      </lift:form>
    </div>
  }
}