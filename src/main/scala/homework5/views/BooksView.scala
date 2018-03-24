package homework5.views

import homework5._
import scalatags.Text.all._

/**
  * @author Vladimir Nizamutdinov (astartes91@gmail.com)
  */
class BooksView(booksStorage: BooksStorage) {

  def getBooksView(page: Int, size: Int): String = {
    html(body(h2("Books"))).toString()
  }
}
