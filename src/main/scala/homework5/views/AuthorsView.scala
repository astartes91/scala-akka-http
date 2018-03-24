package homework5.views

import homework5.AuthorsStorage
import scalatags.Text.all._

/**
  * @author Vladimir Nizamutdinov (astartes91@gmail.com)
  */
class AuthorsView() {

  private val authorsStorage: AuthorsStorage = new AuthorsStorage()

  def getAuthorsView(page: Int, size: Int): String = {
    html(body(h2("Authors"))).toString()
  }

  def getAuthorView(id: String): String = {
    html(body(h2(id))).toString()
  }
}
