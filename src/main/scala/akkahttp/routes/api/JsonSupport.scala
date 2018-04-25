package akkahttp.routes.api

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akkahttp._
import spray.json.{DefaultJsonProtocol, DeserializationException, JsString, JsValue, JsonFormat}

// collect your json format instances into a support trait:
trait JsonSupport extends SprayJsonSupport with DefaultJsonProtocol {

  implicit val genresFormatter: JsonFormat[Genres.Value] = new JsonFormat[Genres.Value] {

    override def write(obj: Genres.Value): JsValue = JsString(obj.toString)

    override def read(json: JsValue): Genres.Value = json match {
      case JsString(value) => Genres.withName(value)
      case value => throw DeserializationException(s"Can't deserialize Genre with value $value")
    }
  }

  implicit val authorCodeFormat = jsonFormat1(AuthorCode)
  implicit val bookCodeFormat = jsonFormat1(BookCode)
  implicit val authorFormat = jsonFormat2(Author)
  implicit val bookFormat = jsonFormat6(Book)
}
