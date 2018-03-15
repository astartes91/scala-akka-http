package homework4

object Json {

  def stringify[T <: JsValue](value: T): String = value match {
    case JsNull => "null"
    case JsNumber(x) => x.toString
    case JsTrue => "true"
    case JsFalse => "false"
    case JsString(x) => x
    case JsObject(x) => x.toString
    case JsArray(x) => x.toString
  }

  def serialize[T : JsonSerializer](value: T)(implicit serializer: JsonSerializer[T]): JsValue =
    serializer.serialize(value)
}
