package homework3

/**
  * @author Vladimir Nizamutdinov (astartes91@gmail.com)
  */
object HomeWork3Part2 {
  def main(args: Array[String]) {
    val seq: Seq[Int] = Seq(1, -1, 100, -200)
    println(findMinNegativeMaxPositive(seq))
    println(findMinNegativeMaxPositiveByLoop(seq))
  }

  def findMinNegativeMaxPositive(seq: Seq[Int]): (Option[Int], Option[Int]) = {
    val negatives: Seq[Int] = seq.filter(number => number < 0)
    val positives: Seq[Int] = seq.filter(number => number > 0)
    val tuple: (Option[Int], Option[Int]) =
      (if(negatives.isEmpty) None else Some(negatives.min),
        if(positives.isEmpty) None else Some(positives.max))
    tuple
  }

  def findMinNegativeMaxPositiveByLoop(seq: Seq[Int]): (Option[Int], Option[Int]) = {
    var min: Option[Int] = None
    var max: Option[Int] = None

    seq.foreach(
      number => {
        if (number < 0){
          if (min.isEmpty || number < min.get) {
            min = Some(number)
          }
        }
        if (number > 0){
          if (max.isEmpty || number > max.get) {
            max = Some(number)
          }
        }
      }
    )

    val tuple: (Option[Int], Option[Int]) = (min, max)
    tuple
  }
}
