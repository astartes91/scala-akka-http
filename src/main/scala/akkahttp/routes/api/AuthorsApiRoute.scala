package akkahttp.routes.api

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akkahttp._

class AuthorsApiRoute(authorsStorage: AuthorsStorage, booksStorage: BooksStorage) extends JsonSupport {

  private def authorsListRoute: Route =
    pathPrefix("authors"){
      pathEndOrSingleSlash{
        get{
          parameters("page".as[Int] ? 1, "size".as[Int] ? 10){ (page, size) =>
            val res: Seq[Author] = authorsStorage.list.drop((page - 1) * size).take(size)
            complete(res)
          }
        } ~ createAuthorRoute
      } ~ authorRoute
    }

  private def authorRoute: Route = pathPrefix(Segment){ code =>
    pathEndOrSingleSlash{
      get{
        val author = authorsStorage.list.find(author => author.code.value.equals(code))
        if(author.nonEmpty){
          complete(author)
        } else {
          complete("")
        }
      }
    } ~ authorBooksRoute(code)
  }

  private def createAuthorRoute: Route = post {
    entity(as[Author]){ (author) =>
      authorsStorage.put(author.code, author)
      complete("")
    }
  }

  private def authorBooksRoute(code: String): Route = {
    path("books") {
      pathEndOrSingleSlash {
        get {
          val author = authorsStorage.list.find(author => author.code.value.equals(code))
          if(author.nonEmpty){

            val booksList: Seq[Book] =
              booksStorage.list.filter(book => book.authorCode.value.equals(author.get.code.value))

            complete(booksList)
          } else {
            complete("")
          }
        }
      }
    }
  }

  def route: Route = authorsListRoute
}
