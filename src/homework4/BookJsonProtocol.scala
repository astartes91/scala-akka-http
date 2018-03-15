package homework4

import scala.reflect.runtime.universe._

object BookJsonProtocol extends JsonProtocol {

  implicit val genreSerializer = new JsonSerializer[Genre] {
    override def serialize(value: Genre): JsValue = JsString(value.value)
  }

  implicit def bookSerializer[S : JsonSerializer, O : JsonSerializer](
    implicit os: JsonSerializer[Option[O]],
    ss: JsonSerializer[Seq[S]]
   ): JsonSerializer[Book] =
    new JsonSerializer[Book] {
      override def serialize(value: Book): JsValue = serializeAnyValue(value, ss, os)
    }

  private def serializeAnyValue[S : JsonSerializer, O : JsonSerializer](
    value: Any, ss: JsonSerializer[Seq[S]], os: JsonSerializer[Option[O]]
  ): JsValue =
     value match {
      case s: String => stringSerializer.serialize(s)
      case i: Integer => intSerializer.serialize(i)
      case l: Long => longSerializer.serialize(l)
      case b: Boolean => booleanSerializer.serialize(b)
      case seq: Seq[S] => ss.serialize(seq)
      case o: Option[O] => os.serialize(o)
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
              val jsValue: JsValue = serializeAnyValue(fieldValue, ss, os)
              (symbol.name.toTermName.toString.trim, jsValue)
            }
          )
          .toMap
        JsObject(map)
      }
      case _ => JsNull
    }
}
