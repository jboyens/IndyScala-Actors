package com.meetup.chat

import scala.collection.mutable.ListBuffer

object MessageService {
  val msgs = new ListBuffer[Msg]

  def receiveMsg(msg: Msg): Unit = {
    msgs += msg
  }

  def chatLog = msgs.toList
}
