package homework5

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import homework5.routes._

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

    println(s"Server online at http://localhost:8080/")
    Http().bindAndHandle(new BookStoreRoute(authorsStorage, booksStorage).route, "127.0.0.1", 8080)
  }
}
