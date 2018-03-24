package homework5.views

import homework5._
import scalatags.Text.TypedTag
import scalatags.Text.all._
import scalatags.Text.tags2.title

/**
  * @author Vladimir Nizamutdinov (astartes91@gmail.com)
  */
class BooksView(booksStorage: BooksStorage, authorsStorage: AuthorsStorage) {

  def getBooksView(page: Int, size: Int): String = {
    html(body(h2("Books"))).toString()
  }

  def getAuthorBooks(author: Author): String = {

    val rows: Seq[TypedTag[String]] =
      booksStorage
        .list
        .filter(book => book.authorCode.value.equals(author.code.value))
        .map(bookToTr)

    html(
      head(title("Author's books"), link(rel := "stylesheet", href := "/res/style.css")),
      body(h1(s"${author.name}'s books"), getBooksTable(rows))
    ).toString()
  }

  def getBookView(bookCode: String): String = html(body(h1("Book"))).toString()

  private def bookToTr(book: Book): TypedTag[String] = {
    val author: Author = authorsStorage.list.find(author => author.code.value.equals(book.authorCode.value)).get
    val bookCode: String = book.code.value
    tr(
      td(a(href := s"/bookstore/books/$bookCode", bookCode)),
      td(a(href := s"/bookstore/books/$bookCode", book.title)),
      td(a(href := s"/bookstore/authors/${author.code.value}", author.name)),
      td(book.year),
      td(book.genre.toString),
      td(book.rating)
    )
  }

  private def getBooksTable(rows: Seq[TypedTag[String]]) =
    table(tbody(tr(th("Book Code"), th("Book title"), th("Author name"), th("Year"), th("Genre"), th("Rating")), rows))
}
