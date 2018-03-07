object HomeWork2 {

  def main(args: Array[String]) {
    println(stringSearch(List("a", "b", "c"), "b"))
    val seq: Seq[Either[String, Int]] = convertStringToInt(List("a", "1", "2", "b", "c"))
    println(seq)
    println(filter(seq))
  }

  def stringSearch(sourceList: List[String], targetString: String, index: Int = 0): Int =
    sourceList match {
      case head :: tail if head.equals(targetString) => index
      case head :: tail if !head.equals(targetString) => stringSearch(tail, targetString, index+1)
      case Nil => -1
    }

  def convertStringToInt(sourceList: List[String]): Seq[Either[String, Int]] =
    sourceList match {
      case head :: tail => Seq(
        try {
          Right(Integer.parseInt(head))
        } catch {
          case e: Exception => Left(e.getMessage)
        }
      ) ++ convertStringToInt(tail)
      case Nil => Nil
    }

  def filter(sourceSeq: Seq[Either[String, Int]]): Seq[Int] =

    sourceSeq match {
      case head :: tail if head.isRight => Seq(head.right.get) ++ filter(tail)
      case head :: tail if head.isLeft => filter(tail)
      case Nil => Nil
    }
}
