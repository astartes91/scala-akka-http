package homework4

import homework4.BookJsonProtocol._

object Main {

  def main(args: Array[String]) {
    val book = Book(id = 1,
      year = 1869,
      author = Author("Lev", "Tolstoi"),
      genres = Seq(Genre("novel")),
      pages = 10000,
      bestseller = Some(1970))

    val bookJs: JsValue = Json.serialize(book)
    println(bookJs)

    val str: String = Json.stringify(bookJs)
    println(str)
  }
}