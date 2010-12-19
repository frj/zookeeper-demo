package com.gu.zkdemo.switchboard.webapp.comet

import net.liftweb._
import http._
import actor._
import com.gu.zkdemo.switchboard.{Logging, Switch}
import js.{JE, JsCmds}
import xml.NodeSeq

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
    bind(
      "switchboard",
      "switches" -> {
        xml:NodeSeq =>
          SwitchboardServer.switches flatMap { switch =>
            bind(
              "switchboard",
              xml,
              "name" -> switch.name,
              "button" -> {
                val currentValue = switch.value
                val newValue = switch.value match {
                  case "on" => "off"
                  case "off" => "on"
                }

                SHtml.ajaxButton(currentValue, () => {
                  switch.changeValue(newValue);
                  JsCmds._Noop
              })}
            )
          } : NodeSeq
      }
    )
  }
}