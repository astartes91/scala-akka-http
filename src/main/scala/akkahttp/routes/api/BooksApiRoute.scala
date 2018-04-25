package akkahttp.routes.api

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akkahttp.{Book, BooksStorage}

class BooksApiRoute(booksStorage: BooksStorage) extends JsonSupport {

  private def booksListRoute: Route = pathPrefix("books"){
    pathEndOrSingleSlash{
      get{
        parameters("page".as[Int] ? 1, "size".as[Int] ? 10, "minRating".as[Int].?, "maxRating".as[Int].?){
          (page, size, minRating, maxRating) =>
            val books: Seq[Book] =
              if(minRating.nonEmpty || maxRating.nonEmpty){
                booksStorage
                  .list
                  .filter(book => minRating.isEmpty || (book.rating >= minRating.get))
                  .filter(book => maxRating.isEmpty || (book.rating <= maxRating.get))
              } else {
                booksStorage.list.drop((page - 1) * size).take(size)
              }

            complete(books)
        }
      } ~ createBookRoute
    } ~ bookRoute
  }

  private def createBookRoute: Route = post {
    entity(as[Book]){ (book) =>
      booksStorage.put(book.code, book)
      complete("")
    }
  }

  private def bookRoute: Route = pathPrefix(Segment){ bookCode =>
    pathEndOrSingleSlash{
      get{
        val book = booksStorage.list.find(book => book.code.value.equals(bookCode))
        if(book.nonEmpty){
          complete(book)
        } else {
          complete("")
        }
      }
    }
  }

  def route: Route = booksListRoute
}
