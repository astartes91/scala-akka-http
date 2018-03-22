package homework4

import scala.annotation.implicitNotFound

@implicitNotFound("No member of type class JsonSerializer in scope for ${T}")
trait JsonSerializer[T] {

  def serialize(value: T): JsValue
}

trait JsonProtocol {

  implicit val stringSerializer = new JsonSerializer[String] {
    def serialize(value: String): JsValue = JsString(value)
  }

  implicit val longSerializer = new JsonSerializer[Long] {
    def serialize(value: Long): JsValue = JsNumber(value)
  }

  implicit val intSerializer = new JsonSerializer[Int] {
    def serialize(value: Int): JsValue = JsNumber(value)
  }

  implicit val booleanSerializer = new JsonSerializer[Boolean] {
    def serialize(value: Boolean): JsValue = JsBoolean(value)
  }

  implicit def optionSerializer[T](implicit ev: JsonSerializer[T]): JsonSerializer[Option[T]] =
    new JsonSerializer[Option[T]] {
      override def serialize(value: Option[T]): JsValue = if(value.isDefined) ev.serialize(value.get) else JsNull
  }

  implicit def seqSerializer[T : JsonSerializer](implicit ev: JsonSerializer[T]) =
    new JsonSerializer[Seq[T]] {
      def serialize(value: Seq[T]): JsValue = JsArray(value.map(ev.serialize))
    }
}

object JsonProtocol extends JsonProtocol