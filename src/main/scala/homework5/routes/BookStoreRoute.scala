package homework5.routes

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route

class BookStoreRoute() {

  val authorsRoute: AuthorsRoute = new AuthorsRoute()
  val booksRoute: BooksRoute = new BooksRoute()

  private def bookstoreRoute: Route = pathPrefix("bookstore"){
    pathEndOrSingleSlash {
      get {
        getFromResource("bookstore.html")
      }
    } ~ authorsRoute.route ~ booksRoute.route
  }

  private def rootRoute: Route = pathSingleSlash {
    get {
      redirect("bookstore", StatusCodes.PermanentRedirect)
    }
  }

  def route: Route = rootRoute ~ bookstoreRoute
}
