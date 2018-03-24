package homework5.routes

import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import scalatags.Text.all._

object AuthorsRoute {

  private def authorsListRoute: Route = pathPrefix("authors"){
    pathEndOrSingleSlash{
      get{
        parameters("page".as[Int] ? 1, "size".as[Int] ? 10){ (page, size) =>
          complete {
            HttpEntity(ContentTypes.`text/html(UTF-8)`, html(body(h2("Authors"))).toString())
          }
        }
      }
    } ~ authorRoute
  }

  private def authorRoute: Route = path(Segment){ id =>
    get{
      complete{
        HttpEntity(ContentTypes.`text/html(UTF-8)`, html(body(h2(id))).toString())
      }
    }
  }

  def route: Route = authorsListRoute
}
