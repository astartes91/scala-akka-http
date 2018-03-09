package homework3

object HomeWork3Part1 {

  def main(args: Array[String]) {
    val seq: Seq[Int] = Seq(1, -1, 0, -2, -3)
    println(countSignChangeWithZeroPreRemoving(seq))
    println(countSignChange(seq))
  }

  def countSignChangeWithZeroPreRemoving(seq: Seq[Int]): Int = {
    val filteredSeq: Seq[Int] = seq.filter(_ != 0)
    filteredSeq match {
      case Nil => 0
      case head :: Nil => 0
      case head :: tail =>
        val product: Int = Seq(head, tail.head).map(_.signum).product
        val isElementsDistinct: Boolean = product == -1
        val number: Int = if(isElementsDistinct) 1 else 0
        number  + countSignChangeWithZeroPreRemoving(tail)
    }
  }

  def countSignChange(seq: Seq[Int]): Int = {
    seq match {
      case Nil => 0
      case head :: Nil => 0
      case head :: tail =>
        if(head == 0) countSignChange(tail)
        else {
          val tailHead: Int = tail.head
          if (tailHead == 0) countSignChange(Seq(head) ++ tail.tail)
          else {
            val product: Int = Seq(head, tailHead).map(_.signum).product
            val isElementsDistinct: Boolean = product == -1
            val number: Int = if (isElementsDistinct) 1 else 0
            number  + countSignChange(tail)
          }
        }
    }
  }
}
