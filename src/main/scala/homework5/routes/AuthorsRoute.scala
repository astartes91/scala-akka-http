package homework5.routes

import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import homework5.views.{AuthorsView, BooksView}
import homework5.{Author, AuthorCode, AuthorsStorage}

class AuthorsRoute(authorsStorage: AuthorsStorage, booksView: BooksView) {

  private val authorsView: AuthorsView = new AuthorsView(authorsStorage)

  private def authorsListRoute: Route = pathPrefix("authors"){
    pathEndOrSingleSlash{
      get{
        parameters("page".as[Int] ? 1, "size".as[Int] ? 10){ (page, size) =>
          complete {
            HttpEntity(ContentTypes.`text/html(UTF-8)`, authorsView.getAuthorsView(page, size))
          }
        }
      } ~ createAuthorRoute
    } ~ authorRoute
  }

  private def createAuthorRoute: Route = post {
    parameters("code".as[String], "name".as[String]){ (code, name) =>
      val authorCode: AuthorCode = AuthorCode(code)
      val author = Author(authorCode, name)
      authorsStorage.put(authorCode, author)
      complete("")
    }
  }

  private def authorRoute: Route = pathPrefix(Segment){ code =>
    pathEndOrSingleSlash{
      get{
        complete{
          HttpEntity(ContentTypes.`text/html(UTF-8)`, authorsView.getAuthorView(code))
        }
      }
    } ~ authorBooksRoute(code)
  }

  private def authorBooksRoute(code: String): Route = {
    path("books") {
      pathEndOrSingleSlash {
        get {
          val author: Author = authorsStorage.list.find(author => author.code.value.equals(code)).get
          complete{
            HttpEntity(ContentTypes.`text/html(UTF-8)`, booksView.getAuthorBooks(author))
          }
        }
      }
    }
  }

  def route: Route = authorsListRoute
}
