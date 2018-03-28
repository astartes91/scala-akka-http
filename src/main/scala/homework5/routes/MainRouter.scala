package homework5.routes

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import homework5.routes.api.{AuthorsApiRoute, BooksApiRoute}
import homework5.routes.html.BookStoreHtmlRoute

class MainRouter(bookStoreHtmlRoute: BookStoreHtmlRoute) {

  def route: Route = bookStoreHtmlRoute.route ~ (new AuthorsApiRoute).route ~ (new BooksApiRoute).route
}
