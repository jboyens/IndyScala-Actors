package com.meetup.chat

import se.scalablesolutions.akka.actor.{Actor, ActorRef}

class ClientActor(central: ActorRef, user: String, msgs: List[String]) extends Actor {
  private val random = new scala.util.Random

  def receive = {
    case Go =>
        central ! Login(user)

        msgs.foreach { m =>
            central ! Msg(user, m)

            Thread.sleep(random.nextInt(5000))
        }

        central ! Logout(user)
  }
}
