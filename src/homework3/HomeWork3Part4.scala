package homework3

/**
  * @author Vladimir Nizamutdinov (astartes91@gmail.com)
  */
object HomeWork3Part4 {

  def main(args: Array[String]) {
    val csv: CsvData = parseCsv(Seq(
      "A|B|C",
      "1|2|3",
      "4|5|6",
      "1|1|1",
      "2|2|2",
      "3|3|3",
      "4|4|4"
    ))
    println(csv)
  }

  def parseCsv(content: Seq[String]): CsvData = {
    var headerColumns: Seq[HeaderColumn] = Seq.empty
    var rows: Seq[Row] = Seq.empty
    if (content.nonEmpty){
      val headerString: String = content.head
      val headerStrings: Seq[String] = headerString.split("|").filterNot(_.equals("|")).toSeq
      headerColumns = headerStrings.map(HeaderColumn)
      val dataStrings: Seq[String] = content.tail
      if (dataStrings.nonEmpty){
        rows = dataStrings.indices
          .map(index => Row(index, dataStrings(index).split("|").filterNot(_.equals("|")).map(RowColumn)))
      }
    }
    CsvData(Header(headerColumns), rows)
  }

  trait Column {
    def data: String
  }

  trait Line {
    def items: Seq[Column]
  }

  case class HeaderColumn(data: String) extends Column

  case class Header(items: Seq[HeaderColumn]) extends Line

  case class RowColumn(data: String) extends Column

  case class Row(index: Int, items: Seq[RowColumn]) extends Line

  case class CsvData(header: Header, rows: Seq[Row])
}
