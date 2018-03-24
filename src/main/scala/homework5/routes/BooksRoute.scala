package homework5.routes

import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import homework5._
import homework5.views.BooksView

class BooksRoute() {

  private val booksStorage: BooksStorage = new BooksStorage()
  private val warCode = BookCode("voina_i_mir")
  private val levCode = AuthorCode("tolstoy")
  private val warAndPeace = Book(warCode, "Война и мир", levCode, 1867, Genres.novel, 10)
  booksStorage.put(warCode, warAndPeace)

  private val booksView: BooksView = new BooksView(booksStorage)

  private def booksListRoute: Route = pathPrefix("books"){
    pathEndOrSingleSlash{
      get{
        parameters("page".as[Int] ? 1, "size".as[Int] ? 10){ (page, size) =>
          complete {
            HttpEntity(ContentTypes.`text/html(UTF-8)`, booksView.getBooksView(page, size))
          }
        }
      }
    }
  }

  def route: Route = booksListRoute
}
