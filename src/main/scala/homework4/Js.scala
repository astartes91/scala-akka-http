package homework4

trait JsValue
object JsNull extends JsValue
case class JsString(value: String) extends JsValue
case class JsNumber(value: Double) extends JsValue
case class JsBoolean(value: Boolean) extends JsValue
object JsTrue extends JsBoolean(true)
object JsFalse extends JsBoolean(false)
case class JsObject(data: Map[String, JsValue]) extends JsValue
case class JsArray(elements: Seq[JsValue]) extends JsValue
