package homework4

object Json {

  def stringify[T <: JsValue](value: T): String =
    value match {
      case JsNull => "null"
      case JsNumber(x) => x.toString
      case JsTrue => "true"
      case JsFalse => "false"
      case JsString(x) => s""""$x""""
      case JsObject(x) => {
        val result: String = x.map((entry) => s""""${entry._1}":${stringify(entry._2)}""")
          .reduce((str, str1) => s"$str,$str1")
        s"{$result}"
      }
      case JsArray(x) => {
        val res: String = if (x.nonEmpty) x.map(stringify).reduce((str, str1) => s"$str,$str1") else ""
        s"[$res]"
      }
    }

  def serialize[T : JsonSerializer](value: T)(implicit serializer: JsonSerializer[T]): JsValue =
    serializer.serialize(value)
}
