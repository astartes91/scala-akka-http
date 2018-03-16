package homework4

object Main {

  def main(args: Array[String]) {
    val book = Book(id = 1,
      year = 1869,
      author = Author("Lev", "Tolstoi"),
      genres = Seq(Genre("novel")),
      pages = 10000,
      bestseller = Some(1970))

    implicit val jstSerializer: JsonSerializer[Int] = BookJsonProtocol.intSerializer
    implicit val genreSerializer: JsonSerializer[Genre] = BookJsonProtocol.genreSerializer
    implicit val ss: JsonSerializer[Seq[Genre]] = BookJsonProtocol.seqSerializer
    implicit val os: JsonSerializer[Option[Int]] = BookJsonProtocol.optionSerializer[Int]
    implicit val bookSerializer: JsonSerializer[Book] = BookJsonProtocol.bookSerializer

    val bookJs: JsValue = Json.serialize(book)
    println(bookJs)

    val str: String = Json.stringify(bookJs)
    println(str)
  }
}
