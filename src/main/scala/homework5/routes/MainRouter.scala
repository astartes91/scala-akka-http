package homework5.routes

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import homework5.routes.api.ApiRouter
import homework5.routes.html.BookStoreHtmlRoute

class MainRouter(bookStoreHtmlRoute: BookStoreHtmlRoute, apiRouter: ApiRouter) {

  def route: Route = bookStoreHtmlRoute.route ~ apiRouter.route
}
