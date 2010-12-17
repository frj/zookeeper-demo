package com.gu.zkdemo.switchboard

import org.slf4j.LoggerFactory

trait Logging  {
  val log = LoggerFactory.getLogger(getClass)
}