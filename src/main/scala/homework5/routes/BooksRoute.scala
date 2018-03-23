package homework5.routes

import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import scalatags.Text.all._

object BooksRoute {

  private def booksListRoute: Route = pathPrefix("books"){
    pathEndOrSingleSlash{
      get{
        parameters("page".as[Option[Int]] ? 1){ (page) =>
          complete {
            HttpEntity(ContentTypes.`text/html(UTF-8)`, html(body(h2("Books"))).toString())
          }
        }
      }
    }
  }

  def route: Route = booksListRoute
}
