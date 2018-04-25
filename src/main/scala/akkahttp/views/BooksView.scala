package akkahttp.views

import akkahttp._
import scalatags.Text.TypedTag
import scalatags.Text.all._
import scalatags.Text.tags2.title

/**
  * @author Vladimir Nizamutdinov (astartes91@gmail.com)
  */
class BooksView(booksStorage: BooksStorage, authorsStorage: AuthorsStorage) {

  def getBooksView(page: Int, size: Int): String = {
    html(
      head(
        title("Books"),
        script(src := "/res/jquery-3.3.1.js"),
        script(src := "/res/script.js"),
        link(rel := "stylesheet", href := "/res/style.css")
      ),
      body(h1("Books"), getBookCreationForm, getBookSearchForm, getBooksList(page, size), getPaginationBlock(size))
    ).toString()
  }

  def getFilteredBooks(minRating: Option[Int], maxRating: Option[Int]): String = {
    booksStorage
      .list
      .filter(book => minRating.isEmpty || (book.rating >= minRating.get))
      .filter(book => maxRating.isEmpty || (book.rating <= maxRating.get))
      .map(bookToTr)
      .mkString
  }

  private def getBookCreationForm: TypedTag[String] = {

    val authorOptions: Seq[TypedTag[String]] =
      authorsStorage.list.map(author => option(value := author.code.value, author.name))

    val genreOptions: Seq[TypedTag[String]] = Genres.values.map(
      genre => {
        val genreString: String = genre.toString
        option(value := genreString, genreString)
      }
    ).toSeq

    div(
      hr,
      h2("Create new book"),
      input(`type`:= "text", id := "bookCode"),
      "Book code",
      br,
      input(`type`:= "text", id := "bookTitle"),
      "Book title",
      br,
      select(id := "bookAuthor", authorOptions),
      "Author",
      br,
      input(`type`:= "number", id := "bookYear"),
      "Year",
      br,
      select(id := "bookGenre", genreOptions),
      "Genre",
      br,
      input(`type`:= "number", id := "bookRating"),
      "Rating",
      br,
      input(`type`:= "submit", id := "createBookButton", value := "Create")
    )
  }

  private def getBookSearchForm: TypedTag[String] = {
    div(
      hr,
      h2("Filter books by rating"),
      input(`type`:= "number", id := "minRating"),
      "Min rating",
      br,
      input(`type`:= "number", id := "maxRating"),
      "Max rating",
      br,
      input(`type`:= "submit", id := "filterBooks", value := "Filter"),
      br,
      input(`type`:= "submit", id := "clearFilter", value := "Clear")
    )
  }

  private def getBooksList(page: Int, size: Int): TypedTag[String] = {

    val rows: Seq[TypedTag[String]] = booksStorage.list.drop((page - 1) * size).take(size).map(bookToTr)
    div(hr, getBooksTable(rows))
  }

  private def getPaginationBlock(size: Int): TypedTag[String] = {
    val pageCount: Int = math.ceil(booksStorage.list.size * 1.0 / size).toInt
    val list: Seq[TypedTag[String]] =
      List.range(1, pageCount + 1).map(page => a(href := s"/bookstore/books?page=$page", page))

    div(hr, list)
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

  def getBookView(bookCode: String): String = {
    val book: Book = booksStorage.list.find(book => book.code.value.equals(bookCode)).get
    val author: Author = authorsStorage.list.find(author => author.code.value.equals(book.authorCode.value)).get

    html(
      head(title("Book")),
      body(
        a(href := "/bookstore/books", "Overall books list"),
        hr,
        h1("Book"),
        s"Code: $bookCode",
        br,
        s"Title: ${book.title}",
        br,
        div("Author: ", a(href := s"/bookstore/authors/${author.code.value}", author.name)),
        s"Year: ${book.year}",
        br,
        s"Genre: ${book.genre.toString}",
        br,
        s"Rating: ${book.rating}"
      )
    ).toString()
  }

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
    table(
      id := "booksTable",
      tbody(tr(th("Book Code"), th("Book title"), th("Author name"), th("Year"), th("Genre"), th("Rating")), rows)
    )
}
