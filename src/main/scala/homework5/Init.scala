package homework5

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import homework5.routes._
import homework5.routes.api.{ApiRouter, AuthorsApiRoute, BooksApiRoute}
import homework5.routes.html.BookStoreHtmlRoute

import scala.concurrent.ExecutionContextExecutor

object Init {

  def init {
    implicit val system: ActorSystem = ActorSystem("my-system")
    implicit val materializer: ActorMaterializer = ActorMaterializer()
    implicit val executionContext: ExecutionContextExecutor = system.dispatcher

    val authorsStorage: AuthorsStorage = new AuthorsStorage()
    val levCode = AuthorCode("tolstoy")
    val lev = Author(levCode, "Лев Николаевич толстой")
    authorsStorage.put(lev.code, lev)

    val booksStorage: BooksStorage = new BooksStorage()
    val warCode = BookCode("voina_i_mir")
    val warAndPeace = Book(warCode, "Война и мир", levCode, 1867, Genres.novel, 10)
    booksStorage.put(warCode, warAndPeace)

    val bookStoreHtmlRoute: BookStoreHtmlRoute = new BookStoreHtmlRoute(authorsStorage, booksStorage)

    val authorsApiRoute: AuthorsApiRoute = new AuthorsApiRoute(authorsStorage, booksStorage)
    val booksApiRoute: BooksApiRoute = new BooksApiRoute()
    val apiRouter: ApiRouter = new ApiRouter(authorsApiRoute, booksApiRoute)

    Http().bindAndHandle(new MainRouter(bookStoreHtmlRoute, apiRouter).route, "127.0.0.1", 8080)
    println(s"Server online at http://localhost:8080/")
  }
}
