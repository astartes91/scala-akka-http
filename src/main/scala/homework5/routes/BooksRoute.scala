package homework5.routes

import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import homework5.views.BooksView

class BooksRoute() {

  private val booksView: BooksView = new BooksView()

  private def booksListRoute: Route = pathPrefix("books"){
    pathEndOrSingleSlash{
      get{
        parameters("page".as[Int] ? 1, "size".as[Int] ? 10){ (page, size) =>
          complete {
            HttpEntity(ContentTypes.`text/html(UTF-8)`, booksView.getBooksView(page, size))
          }
        }
      }
    }
  }

  def route: Route = booksListRoute
}
