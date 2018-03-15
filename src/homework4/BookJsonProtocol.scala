package homework4

import scala.reflect.runtime.universe._

object BookJsonProtocol extends JsonProtocol {
  implicit val genreSerializer = new JsonSerializer[Genre] {
    override def serialize(value: Genre): JsValue = JsString(value.value)
  }

  implicit def bookSerializer[T : JsonSerializer](implicit ev: JsonSerializer[Seq[T]]): JsonSerializer[Book] =
    (value: Book) => {
    val mirror: Mirror = scala.reflect.runtime.currentMirror
    val instanceMirror: InstanceMirror = mirror.reflect(value)

    val map: Map[String, JsValue] = mirror.classSymbol(value.getClass)
      .toType
      .members
      .collect({
        case t: TermSymbol if t.isVar || t.isVal => t
      })
      .map(
        symbol => {
          val value: Any = instanceMirror.reflectField(symbol).get
          val jsValue: JsValue = value match {
            case s: String => JsString(s)
            case l: Long => JsNumber(l)
            case b: Boolean => JsBoolean(b)
            case seq: Seq[T] => ev.serialize(seq)
            //case obj: Object =>
            case Nil => JsNull
          }
          (symbol.fullName, jsValue)
        }
      )
      .toMap
    JsObject(map)
  }
}
