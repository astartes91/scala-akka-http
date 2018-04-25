package akkahttp.views

import akkahttp.{Author, AuthorsStorage}
import scalatags.Text.TypedTag
import scalatags.Text.all._
import scalatags.Text.tags2.title

/**
  * @author Vladimir Nizamutdinov (astartes91@gmail.com)
  */
class AuthorsView(authorsStorage: AuthorsStorage) {

  def getAuthorsView(page: Int, size: Int): String =
    html(
      head(
        title("Authors"),
        script(src := "/res/jquery-3.3.1.js"),
        script(src := "/res/script.js"),
        link(rel := "stylesheet", href := "/res/style.css")
      ),
      body(h1("Authors"), getAuthorCreationForm, getAuthorsList(page, size), getPaginationBlock(size))
    ).toString()

  private def getAuthorCreationForm: TypedTag[String] =
    div(
      hr,
      h2("Create new author"),
      input(`type`:= "text", id := "authorCode"),
      "Author code",
      br,
      input(`type`:= "text", id := "authorName"),
      "Author name",
      br,
      input(`type`:= "submit", id := "createAuthorButton", value := "Create")
    )

  private def getAuthorsList(page: Int, size: Int): TypedTag[String] = {

    val res: Seq[Author] = authorsStorage.list.drop((page - 1) * size).take(size)
    val rows: Seq[TypedTag[String]] =
      res.map(
        author => {
          val code: String = author.code.value
          val url: String = s"/bookstore/authors/$code"
          tr(td(a(href := url, code)), td(a(href := url, author.name)))
        }
      )

    div(hr, table(tbody(tr(th("Author Code"), th("Author name")), rows)))
  }

  private def getPaginationBlock(size: Int): TypedTag[String] = {
    val pageCount: Int = math.ceil(authorsStorage.list.size * 1.0 / size).toInt
    val list: Seq[TypedTag[String]] =
      List.range(1, pageCount + 1).map(page => a(href := s"/bookstore/authors?page=$page", page))

    div(hr, list)
  }

  def getAuthorView(code: String): String = {

    val author: Author = authorsStorage.list.find(author => author.code.value.equals(code)).get

    html(
      head(title("Author")),
      body(
        a(href := "/bookstore/books", "Overall books list"),
        br,
        a(href := "/bookstore/authors", "Overall authors list"),
        br,
        a(href := s"/bookstore/authors/$code/books", "Author's books list"),
        hr,
        h1("Author"),
        s"Code: $code",
        br,
        s"Name: ${author.name}"
      )
    ).toString()
  }
}
