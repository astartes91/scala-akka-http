package homework4

case class Genre(value: String)

case class Author(name: String, sname: String)

case class Book(id: Long,
                year: Int,
                author: Author,
                genres: Seq[Genre],
                pages: Int,
                bestseller: Option[Int])
