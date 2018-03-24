package homework5.views

import homework5.AuthorsStorage
import scalatags.Text
import scalatags.Text.all._
import scalatags.Text.tags2.title

/**
  * @author Vladimir Nizamutdinov (astartes91@gmail.com)
  */
class AuthorsView() {

  private val authorsStorage: AuthorsStorage = new AuthorsStorage()

  def getAuthorsView(page: Int, size: Int): String = {
    val tag: Text.TypedTag[String] = html(
      head(
        title("Authors"),
        script(src := "/script/jquery-3.3.1.js"),
        script(src := "/script/script.js")
      ),
      body(
        h1("Authors"),
        hr,
        h2("Create new author"),
        input(`type`:= "text", id := "authorCode"),
        "Author code",
        br,
        input(`type`:= "text", id := "authorName"),
        "Author name",
        br,
        input(`type`:= "submit", id := "createAuthorButton", value := "Create"),
        hr
      )
    )
    tag.toString()
  }

  def getAuthorView(id: String): String = {
    html(body(h2(id))).toString()
  }
}
