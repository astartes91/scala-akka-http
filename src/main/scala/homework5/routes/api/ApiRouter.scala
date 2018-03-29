package homework5.routes.api

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route

class ApiRouter(authorsApiRoute: AuthorsApiRoute, booksApiRoute: BooksApiRoute) {

  def route: Route = pathPrefix("api"){
    pathPrefix("bookstore"){
      authorsApiRoute.route ~ booksApiRoute.route
    }
  }
}
