package homework5.routes.html

import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import homework5._
import homework5.views.BooksView

class BooksHtmlRoute(booksStorage: BooksStorage, booksView: BooksView) {

  private def booksListRoute: Route = pathPrefix("books"){
    pathEndOrSingleSlash{
      get{
        parameters("page".as[Int] ? 1, "size".as[Int] ? 10, "minRating".as[Int].?, "maxRating".as[Int].?){
          (page, size, minRating, maxRating) =>
            complete {
              if(minRating.nonEmpty || maxRating.nonEmpty){
                HttpEntity(ContentTypes.`text/plain(UTF-8)`, booksView.getFilteredBooks(minRating, maxRating))
              } else {
                HttpEntity(ContentTypes.`text/html(UTF-8)`, booksView.getBooksView(page, size))
              }
            }
        }
      } ~ createBookRoute
    } ~ bookRoute
  }

  private def createBookRoute: Route = post {
    parameters("code", "title", "authorCode", "year".as[Int], "genre", "rating".as[Int]) {
      (code, title, authorCode, year, genre, rating) =>
        val genreValue: Genres.Value = Genres.withName(genre)
        val bookCode: BookCode = BookCode(code)
        val book: Book = Book(bookCode, title, AuthorCode(authorCode), year, genreValue, rating)
        booksStorage.put(bookCode, book)
        complete("")
      }
  }

  private def bookRoute: Route = pathPrefix(Segment){ bookCode =>
    pathEndOrSingleSlash{
      get{
        complete{
          HttpEntity(ContentTypes.`text/html(UTF-8)`, booksView.getBookView(bookCode))
        }
      }
    }
  }

  def route: Route = booksListRoute
}
