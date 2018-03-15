package homework4

import scala.reflect.runtime.universe._

object BookJsonProtocol extends JsonProtocol {

  implicit val genreSerializer = new JsonSerializer[Genre] {
    override def serialize(value: Genre): JsValue = JsString(value.value)
  }

  implicit def bookSerializer[T : JsonSerializer](implicit ev: JsonSerializer[Seq[T]]): JsonSerializer[Book] =
    (value: Book) => serializeAnyValue(value)(ev)

  private def serializeAnyValue[T: JsonSerializer](value: Any)(ev: JsonSerializer[Seq[T]])(implicit os: JsonSerializer[Option[T]]): JsValue =
     value match {
      case s: String => stringSerializer.serialize(s)
      case i: Integer => intSerializer.serialize(i)
      case l: Long => longSerializer.serialize(l)
      case b: Boolean => booleanSerializer.serialize(b)
      case seq: Seq[T] => ev.serialize(seq)
      case o: Option[T] => os.serialize(o)
      case obj: Object => {
        val mirror: Mirror = scala.reflect.runtime.currentMirror
        val instanceMirror: InstanceMirror = mirror.reflect(obj)

        val map: Map[String, JsValue] = mirror.classSymbol(obj.getClass)
          .toType
          .members
          .collect(
            {
              case t: TermSymbol if t.isVar || t.isVal => t
            }
          )
          .map(
            symbol => {
              val fieldValue: Any = instanceMirror.reflectField(symbol).get
              val jsValue: JsValue = serializeAnyValue(fieldValue)(ev)
              (symbol.name.toTermName.toString.trim, jsValue)
            }
          )
          .toMap
        JsObject(map)
      }
      case _ => JsNull
    }
}
