package akkahttp.routes.html

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akkahttp._
import akkahttp.views.BooksView

class BookStoreHtmlRoute(authorsStorage: AuthorsStorage, booksStorage: BooksStorage) {

  private val booksView: BooksView = new BooksView(booksStorage, authorsStorage)

  private val authorsRoute: AuthorsHtmlRoute = new AuthorsHtmlRoute(authorsStorage, booksView)
  private val booksRoute: BooksHtmlRoute = new BooksHtmlRoute(booksStorage, booksView)

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
