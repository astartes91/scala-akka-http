package homework3

/**
  * @author Vladimir Nizamutdinov (astartes91@gmail.com)
  */
object HomeWork3Part3 {

  def main(args: Array[String]) {

    var seq: Seq[(Int) => Option[Int]] = Seq(div2IfNotOdd, add1IfOdd)
    println(applyTransformations(10, seq))

    seq = Seq(add1IfOdd, returnNone, div2IfNotOdd)
    println(applyTransformations(7, seq))
  }

  def div2IfNotOdd(i: Int): Option[Int] = if(i % 2 > 0) None else Some(i / 2)

  def add1IfOdd(i: Int): Option[Int] = if(i % 2 > 0) Some(i + 1) else Some(i)

  def returnNone(i: Int): Option[Int] = None

  def applyTransformations(i: Int, transformations: Seq[(Int) => Option[Int]]): Int = {
    transformations.reduceRight(
      (funcRight, funcLeft) =>
        {
          number => funcLeft.apply(funcRight.apply(number).getOrElse(number))
        }
    )
      .apply(i)
      .getOrElse(i)
    /*transformations match {
      case Nil => i
      case head :: tail => applyTransformations(head.apply(i).getOrElse(i), tail)
    }*/
  }
}
