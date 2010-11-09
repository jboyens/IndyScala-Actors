package com.meetup.chat

import se.scalablesolutions.akka.actor._
import se.scalablesolutions.akka.util._
import se.scalablesolutions.akka.camel._
import se.scalablesolutions.akka.config._

import java.util.Date

case class Login(username: String)
case class Logout(username: String)

case class Msg(username: String, text: String)

case object GetLoggedIn
case class LoggedIn(users: List[String])

case object GetLog
case class Log(list: List[Msg])

class CentralActor extends Actor with Logging with Consumer {
  def endpointUri = Config.config("jetty.endpoint")

  def receive = behaving

  def behaving: Receive = {
    case msg: Message =>
        val text = msg.headers("text").toString
        val user = msg.headers("user").toString

        MessageService.receiveMsg(Msg(user, text))
        log.info("[%s] %s: %s".format(new Date().toLocaleString(), user, text))

        self.reply("")

    case Login(user) if user == "bob" =>
        UserService.login(user)
        log.info("User %s logged in".format(user))

        goRogue

    case Login(user) => 
        UserService.login(user)
        log.info("User %s logged in".format(user))

    case Logout(user) => 
        UserService.logout(user)
        log.info("User %s logged out".format(user))

    case x @ Msg(user, text) => 
        MessageService.receiveMsg(x)
        log.info("[%s] %s: %s".format(new Date().toLocaleString(), user, text))

    case GetLoggedIn =>
        self.reply(LoggedIn(UserService.loggedInUsers))

    case GetLog =>
        self.reply(Log(MessageService.chatLog))
  }

  def rogue: Receive = {
    case Msg(user, text) if user == "bob" => 
        MessageService.receiveMsg(Msg(user, "You betcha!"))
        log.info("[%s] %s: %s".format(new Date().toLocaleString(), user, "You betcha!"))
  }

  def goRogue = {
    become(rogue.orElse(behaving))
  }
}
