package homework5.views

import homework5.BooksStorage
import scalatags.Text.all._

/**
  * @author Vladimir Nizamutdinov (astartes91@gmail.com)
  */
class BooksView {
  private val booksStorage: BooksStorage = new BooksStorage()

  def getBooksView(page: Int, size: Int): String = {
    html(body(h2("Books"))).toString()
  }
}
