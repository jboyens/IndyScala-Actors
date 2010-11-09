package com.meetup.chat

import se.scalablesolutions.akka.actor.Actor._
import se.scalablesolutions.akka.remote._
import se.scalablesolutions.akka.camel.CamelServiceManager._

case object Go

/** H-AKKA-thon */
object SweetChatAction {
  def start = {
    startCamelService

    RemoteNode.start("localhost", 2552)
    RemoteNode.register("central-service", actorOf[CentralActor])
   
    val central = RemoteClient.actorFor("central-service", "localhost", 2552)

    val bobMsgs = "Hey guys!" :: "What's up?" :: "You did what?!" :: Nil
    val maryMsgs = "Wassup?!" :: "Nutin'. jus chilaxin'" :: "Boooo" :: Nil
    val timMsgs = "Typing skills" :: "OMGWTFBBQ!!!11one" :: "Tempest RULEZ!" :: Nil

    val bob = actorOf(new ClientActor(central, "bob", bobMsgs))
    val mary = actorOf(new ClientActor(central, "mary", maryMsgs))
    val tim = actorOf(new ClientActor(central, "tim", timMsgs))

    central.start

    bob.start
    mary.start
    tim.start

    bob ! Go
    mary ! Go
    tim ! Go
  }
}
