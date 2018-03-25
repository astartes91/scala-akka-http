package homework5.routes

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import homework5._
import homework5.views.BooksView

class BookStoreRoute(authorsStorage: AuthorsStorage, booksStorage: BooksStorage) {

  private val booksView: BooksView = new BooksView(booksStorage, authorsStorage)

  private val authorsRoute: AuthorsRoute = new AuthorsRoute(authorsStorage, booksView)
  private val booksRoute: BooksRoute = new BooksRoute(booksStorage, booksView)

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

  private def getResRoute: Route = {
    pathPrefix("res"){
      get {
        getFromResourceDirectory("res")
      }
    }
  }

  def route: Route = rootRoute ~ getResRoute ~ bookstoreRoute
}
