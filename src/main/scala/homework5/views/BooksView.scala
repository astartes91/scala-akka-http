package homework5.views

import homework5._
import scalatags.Text.all._

/**
  * @author Vladimir Nizamutdinov (astartes91@gmail.com)
  */
class BooksView {

  private val booksStorage: BooksStorage = new BooksStorage()
  private val warCode = BookCode("voina_i_mir")
  private val levCode = AuthorCode("tolstoy")
  private val warAndPeace = Book(warCode, "Война и мир", levCode, 1867, Genres.novel, 10)
  booksStorage.put(warCode, warAndPeace)

  def getBooksView(page: Int, size: Int): String = {
    html(body(h2("Books"))).toString()
  }
}
