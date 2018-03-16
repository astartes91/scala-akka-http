package homework4

import scala.reflect.runtime.universe._

object BookJsonProtocol extends JsonProtocol {

  implicit val genreSerializer = new JsonSerializer[Genre] {
    override def serialize(value: Genre): JsValue = JsString(value.value)
  }

  implicit def bookSerializer[S](implicit os: JsonSerializer[Option[Int]], ss: JsonSerializer[Seq[S]]):
  JsonSerializer[Book] =
    new JsonSerializer[Book] {
      override def serialize(value: Book): JsValue = serializeAnyValue(value)
    }

  private def serializeAnyValue[S](value: Any)(implicit ss: JsonSerializer[Seq[S]], os: JsonSerializer[Option[Int]])
  : JsValue =
     value match {
      case s: String => stringSerializer.serialize(s)
      case i: Integer => intSerializer.serialize(i)
      case l: Long => longSerializer.serialize(l)
      case b: Boolean => booleanSerializer.serialize(b)
      case seq: Seq[S] => ss.serialize(seq)
      case o: Option[Int] => os.serialize(o)
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
              val jsValue: JsValue = serializeAnyValue(fieldValue)
              (symbol.name.toTermName.toString.trim, jsValue)
            }
          )
          .toMap
        JsObject(map)
      }
      case _ => JsNull
    }
}
