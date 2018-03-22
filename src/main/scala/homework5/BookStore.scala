package homework5

import akka.http.scaladsl.model.{ContentTypes, HttpEntity, StatusCodes}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route

import scala.io.Source

object BookStore {

  private def bookstoreRoute: Route = path("bookstore"){
    get {
      val fileContents: String = Source.fromResource("bookstore.html").getLines.mkString
      complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, fileContents))
    }
  }

  private def rootRoute: Route = pathSingleSlash {
    get {
      redirect("bookstore", StatusCodes.PermanentRedirect)
    }
  }

  def route: Route = rootRoute ~ bookstoreRoute
}
