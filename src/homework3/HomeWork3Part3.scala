package homework3

/**
  * @author Vladimir Nizamutdinov (astartes91@gmail.com)
  */
object HomeWork3Part3 {

  def main(args: Array[String]) {

    var seq: Seq[(Int) => Option[Int]] = Seq(div2IfNotOdd, add1IfOdd)
    /*println(applyTransformationsByReducing(10, seq))
    println(applyTransformationsByPatternMatching(10, seq))*/

    seq = Seq(add1IfOdd, returnNone, div2IfNotOdd)
    //println(applyTransformationsByReducing(7, seq))
    println(applyTransformationsByPatternMatching(7, seq))
  }

  /*def applyTransformationsByReducing(i: Int, transformations: Seq[(Int) => Option[Int]]): Int = {
    if(transformations.isEmpty) i
    else transformations.reduce(
      (funcLeft, funcRight) =>
      {
        number =>
          println(s"new function arg: $number")
          val funcLeftRes: Int = funcLeft(number).getOrElse(number)
          println(s"funcLeftRes: $funcLeftRes")
          val funcRightRes: Option[Int] = funcRight(funcLeftRes)
          println(s"funcRightRes: $funcRightRes")
            funcRightRes
        //number => funcLeft.andThen(funcLeftResult => funcRight(funcLeftResult.getOrElse(number))).apply(number)
      }
    )
      .apply(i)
      .getOrElse(i)
  }*/

  def applyTransformationsByPatternMatching(i: Int, transformations: Seq[(Int) => Option[Int]]): Int =
    transformations match {
      case Nil => i
      case head :: tail => applyTransformationsByPatternMatching(head(i).getOrElse(i), tail)
    }

  def div2IfNotOdd(i: Int): Option[Int] = {
    println(s"div2IfNotOdd called with arg $i")
    val res: Option[Int] = if(i % 2 > 0) None else Some(i / 2)
    println(s"div2IfNotOdd return $res")
    res
  }

  def add1IfOdd(i: Int): Option[Int] = {
    println(s"add1IfOdd called with arg $i")
    val res: Some[Int] = if(i % 2 > 0) Some(i + 1) else Some(i)
    println(s"add1IfOdd return $res")
    res
  }

  def returnNone(i: Int): Option[Int] = {
    println(s"returnNone called with arg $i")
    val res: None.type = None
    println(s"returnNone return $res")
    res
  }
}
