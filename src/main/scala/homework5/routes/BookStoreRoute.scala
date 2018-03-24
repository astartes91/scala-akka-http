package homework5.routes

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import homework5._
import homework5.views.BooksView

class BookStoreRoute() {

  private val booksStorage: BooksStorage = new BooksStorage()
  private val warCode = BookCode("voina_i_mir")
  private val levCode = AuthorCode("tolstoy")
  private val warAndPeace = Book(warCode, "Война и мир", levCode, 1867, Genres.novel, 10)
  booksStorage.put(warCode, warAndPeace)

  private val booksView: BooksView = new BooksView(booksStorage)

  private val authorsRoute: AuthorsRoute = new AuthorsRoute(booksView)
  private val booksRoute: BooksRoute = new BooksRoute(booksView)

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
