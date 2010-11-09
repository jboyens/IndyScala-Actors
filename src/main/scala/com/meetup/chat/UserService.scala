package com.meetup.chat

import scala.collection.mutable._

object UserService {
  val users = new ListBuffer[String]

  def loggedInUsers: List[String] = users.toList

  def login(user: String): Unit = {
    users += user;
  }

  def logout(user: String): Unit = {
    users -= user;
  }
}
