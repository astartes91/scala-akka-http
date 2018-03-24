package homework5.routes

import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import homework5.views.AuthorsView

class AuthorsRoute() {

  private val authorsView: AuthorsView = new AuthorsView()

  private def authorsListRoute: Route = pathPrefix("authors"){
    pathEndOrSingleSlash{
      get{
        parameters("page".as[Int] ? 1, "size".as[Int] ? 10){ (page, size) =>
          complete {
            HttpEntity(ContentTypes.`text/html(UTF-8)`, authorsView.getAuthorsView(page, size))
          }
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
