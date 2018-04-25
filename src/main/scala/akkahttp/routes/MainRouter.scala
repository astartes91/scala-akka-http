package akkahttp.routes

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akkahttp.routes.api.ApiRouter
import akkahttp.routes.html.BookStoreHtmlRoute

class MainRouter(bookStoreHtmlRoute: BookStoreHtmlRoute, apiRouter: ApiRouter) {

  def route: Route = bookStoreHtmlRoute.route ~ apiRouter.route
}
