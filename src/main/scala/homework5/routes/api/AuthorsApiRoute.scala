package homework5.routes.api

import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route

class AuthorsApiRoute {

  private def authorsListRoute: Route = pathPrefix("/api/bookstore/authors"){
    pathEndOrSingleSlash{
      get{
        parameters("page".as[Int] ? 1, "size".as[Int] ? 10){ (page, size) =>
          complete {
            HttpEntity(ContentTypes.`application/json`, "")
          }
        }
      }
    }
  }

  def route: Route = authorsListRoute
}
