object HomeWork3 {
  def main(args: Array[String]) {
    println(countSignChange(Seq(1, -1, 0, -2, -3)))
    println(findMinNegativeMaxPositive(Seq(1, -1, 100, -200)))
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

  //todo:
  def findMinNegativeMaxPositive(seq: Seq[Int]): (Option[Int], Option[Int]) = {
    val negatives = seq.filter(number => number < 0)
    val positives = seq.filter(number => number > 0)
    (if(negatives.isEmpty) None else Some(negatives.min), if(positives.isEmpty) None else Some(positives.max))
  }
}
