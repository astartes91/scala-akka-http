package homework5.routes.api

import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route

class BooksApiRoute {

  private def booksListRoute: Route = pathPrefix("/api/bookstore/books"){
    pathEndOrSingleSlash{
      get{
        parameters("page".as[Int] ? 1, "size".as[Int] ? 10, "minRating".as[Int].?, "maxRating".as[Int].?){
          (page, size, minRating, maxRating) =>
            complete {
              if(minRating.nonEmpty || maxRating.nonEmpty){
                HttpEntity(ContentTypes.`text/plain(UTF-8)`, "")
              } else {
                HttpEntity(ContentTypes.`text/html(UTF-8)`, "")
              }
            }
        }
      }
    }
  }

  def route: Route = booksListRoute
}
