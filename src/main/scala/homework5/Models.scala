package homework5

object Genres extends Enumeration {
  val novel: Genres.Value = Value("novel")
}

case class AuthorCode(value: String) extends AnyVal
case class BookCode(value: String) extends AnyVal
case class Author(code: AuthorCode, name: String)
case class Book(code: BookCode, title: String, authorCode: AuthorCode, year: Int, genre: Genres.Value, rating: Int)
