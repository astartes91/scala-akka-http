package homework5.views

import homework5.{Author, AuthorCode, AuthorsStorage}
import scalatags.Text.TypedTag
import scalatags.Text.all._
import scalatags.Text.tags2.title

/**
  * @author Vladimir Nizamutdinov (astartes91@gmail.com)
  */
class AuthorsView() {

  private val authorsStorage: AuthorsStorage = new AuthorsStorage()
  val levCode = AuthorCode("tolstoy")
  val lev = Author(levCode, "Лев Николаевич толстой")
  authorsStorage.put(lev.code, lev)
  private val hooi: AuthorCode = AuthorCode("hooi")
  authorsStorage.put(hooi, Author(hooi, "Хуй Простой"))

  def getAuthorsView(page: Int, size: Int): String =
    html(
      head(
        title("Authors"),
        script(src := "/res/jquery-3.3.1.js"),
        script(src := "/res/script.js"),
        link(rel := "stylesheet", href := "/res/style.css")
      ),
      body(
        h1("Authors"),
        getAuthorCreationForm,
        getAuthorsList(page, size),
        hr
      )
    ).toString()

  def getAuthorCreationForm: TypedTag[String] =
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

  def getAuthorsList(page: Int, size: Int): TypedTag[String] = {

    val res: Seq[Author] = authorsStorage.list.drop((page - 1) * size).take(size)
    val seq: Seq[TypedTag[String]] =
      res.map(
        author => {
          val code: String = author.code.value
          val url: String = s"authors/$code"
          tr(td(a(href := url, code)), td(a(href := url, author.name)))
        }
      )

    div(hr, table(tbody(tr(th("Author Code"), th("Author name")), seq))
    )
  }

  def getAuthorView(id: String): String = {
    html(body(h2(id))).toString()
  }
}
