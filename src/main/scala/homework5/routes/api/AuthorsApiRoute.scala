package homework5.routes.api

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import homework5.{Author, AuthorsStorage}

class AuthorsApiRoute(authorsStorage: AuthorsStorage) extends JsonSupport {

  private def authorsListRoute: Route =
    pathPrefix("authors"){
      pathEndOrSingleSlash{
        get{
          parameters("page".as[Int] ? 1, "size".as[Int] ? 10){ (page, size) =>
            val res: Seq[Author] = authorsStorage.list.drop((page - 1) * size).take(size)
            complete(res)
          }
        }
      }
    }

  def route: Route = authorsListRoute
}
