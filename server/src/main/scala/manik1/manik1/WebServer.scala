package manik1.manik1

import monix.execution.Scheduler.Implicits.global

import com.typesafe.config.ConfigFactory
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.RouteResult._
import akka.stream.ActorMaterializer
import akka.actor.ActorSystem

object WebServer {
  def main(args: Array[String]) {
    implicit val system = ActorSystem("server-system")
    implicit val materializer = ActorMaterializer()

    val configApp = ConfigFactory.load()
    val interface = configApp.getString("http.interface")
    val port = configApp.getInt("http.port")

    val service = new WebService()
    Http().bindAndHandle(service.route, interface, port)

    println(s"Server online at http://$interface:$port")

  }
}
