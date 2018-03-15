package homework4

object Json {

  def stringify[T <: JsValue](value: T): String = value match {
    case JsNull => "null"
    case JsNumber(x) => x.toString
    case JsTrue => "true"
    case JsFalse => "false"
    case JsString(x) => "\"" + x + "\""
    case JsObject(x) => {
      "{" + x.map((entry) => "\"" + entry._1 + "\"" +":" + stringify(entry._2)).reduce((str, str1) => str + "," + str1) + "}"
    }
    case JsArray(x) => "[" + (if(x.nonEmpty) x.map(stringify).reduce((str, str1) => str + "," + str1) else "") + "]"
  }

  def serialize[T : JsonSerializer](value: T)(implicit serializer: JsonSerializer[T]): JsValue =
    serializer.serialize(value)
}
