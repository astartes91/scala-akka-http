package homework5

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route

object BookStore {

  private def bookstoreRoute: Route = path("bookstore"){
    get {
      getFromResource("bookstore.html")
    }
  }

  private def rootRoute: Route = pathSingleSlash {
    get {
      redirect("bookstore", StatusCodes.PermanentRedirect)
    }
  }

  def route: Route = rootRoute ~ bookstoreRoute
}
