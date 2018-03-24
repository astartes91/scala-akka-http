package homework5.routes

import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import homework5.views.AuthorsView
import homework5.{Author, AuthorCode, AuthorsStorage}

class AuthorsRoute() {

  private val authorsStorage: AuthorsStorage = new AuthorsStorage
  private val levCode = AuthorCode("tolstoy")
  private val lev = Author(levCode, "Лев Николаевич толстой")
  authorsStorage.put(lev.code, lev)

  private val authorsView: AuthorsView = new AuthorsView(authorsStorage)

  private def authorsListRoute: Route = pathPrefix("authors"){
    pathEndOrSingleSlash{
      get{
        parameters("page".as[Int] ? 1, "size".as[Int] ? 10){ (page, size) =>
          complete {
            HttpEntity(ContentTypes.`text/html(UTF-8)`, authorsView.getAuthorsView(page, size))
          }
        }
      } ~ post {
        parameters("code".as[String], "name".as[String]){ (code, name) =>
          val authorCode: AuthorCode = AuthorCode(code)
          val author = Author(authorCode, name)
          authorsStorage.put(authorCode, author)
          complete("")
        }
      }
    } ~ authorRoute
  }

  private def authorRoute: Route = path(Segment){ id =>
    get{
      complete{
        HttpEntity(ContentTypes.`text/html(UTF-8)`, authorsView.getAuthorView(id))
      }
    }
  }

  def route: Route = authorsListRoute
}
