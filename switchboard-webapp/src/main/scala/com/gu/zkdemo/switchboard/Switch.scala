package com.gu.zkdemo.switchboard

import org.apache.zookeeper.{WatchedEvent, CreateMode, Watcher, ZooKeeper}
import scala.collection.JavaConversions._
import org.apache.zookeeper.data.{Id, ACL}
import org.apache.zookeeper.ZooDefs.{Ids, Perms}
import org.apache.zookeeper.Watcher.Event.EventType
import actors.Actor
import net.liftweb.common.SimpleActor
import net.liftweb.actor.LiftActor

class Switch (val name:String, zooKeeper:ZooKeeper, actorToNotify: LiftActor) extends Watcher with Logging {
  var value = "off"

  if(zooKeeper.exists("/" + name, false) == null) {
    zooKeeper.create("/" + name, value.getBytes("UTF-8"), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT )
  }

  updateFromZooKeeper

  def process(event: WatchedEvent): Unit = {
    log.info("received event" + event.getPath + "  " + event.getType)
    if( List(EventType.NodeCreated, EventType.NodeDataChanged) contains event.getType ) {
        updateFromZooKeeper
        actorToNotify ! "go"
    }
  }

  def updateFromZooKeeper = {
    value = new String(zooKeeper.getData("/"+ name, this, null), "UTF-8" )
    log.info("Switch " + name + " value is '" + value + "'")
  }

  def changeValue(newValue: String) = {
    zooKeeper.setData("/" + name, newValue.getBytes("UTF-8"), -1)
  }
}