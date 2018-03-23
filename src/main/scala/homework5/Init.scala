package homework5

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import homework5.routes.BookStoreRoute

import scala.concurrent.ExecutionContextExecutor

object Init {

  def init {
    implicit val system: ActorSystem = ActorSystem("my-system")
    implicit val materializer: ActorMaterializer = ActorMaterializer()
    implicit val executionContext: ExecutionContextExecutor = system.dispatcher

    println(s"Server online at http://localhost:8080/")
    Http().bindAndHandle(BookStoreRoute.route, "127.0.0.1", 8080)
  }
}
