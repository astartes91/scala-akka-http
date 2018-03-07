
object HomeWork3 {
  def main(args: Array[String]) {
    println(countSignChange(Seq(1, -1, 0, -2, -3)))
  }

  def countSignChange(seq: Seq[Int]): Int =
    seq match {
      case Nil => 0
      case head :: Nil => 0
      case head :: tail =>
        ((if(Seq(head, tail.head).map(number => number.signum).distinct.size == 2) 1
        else 0)
        + countSignChange(tail))
    }
}
