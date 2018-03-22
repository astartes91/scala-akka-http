object HomeWork1 {

  def main(args: Array[String]) {
    val n = -10
    val fibonacciNumber : Int = calculateFibonacciNumber(n)
    System.out.println(s"$n Fibonacci number = $fibonacciNumber")
    System.out.println("-----------------------------------------")
    quadraticEquation(3,4,1)
  }

  def calculateFibonacciNumber(n: Int): Int = {
    if (n < 0) calculateFibonacciNumber(n+2) - calculateFibonacciNumber(n+1)
    else if (n > -1 && n < 2) n
    else calculateFibonacciNumber(n-1) + calculateFibonacciNumber(n-2)
  }

  def quadraticEquation(a: Int, b: Int, c: Int){
    System.out.println(s"Solving quadratic equation with a = $a, b = $b, c = $c")
    val discriminant = b * b - 4*a*c
    System.out.println(s"Discriminant = $discriminant")

    val rootsNumber: Int =
      if (discriminant < 0) 0
      else if (discriminant == 0) 1
      else 2
    System.out.println(s"Roots number = $rootsNumber")

    val direction: String =
      if (a > 0) "Up"
      else if (a < 0) "Down"
      else null
    System.out.println(s"Direction = $direction")

    if (rootsNumber == 0) System.out.print("No roots")
    else if (rootsNumber == 1) {
      val root: Double = b/(2.0*a)*(-1)
      System.out.println(s"Single root = $root")
    } else {
      val root1: Double = (-1)*b+Math.sqrt(discriminant)/(2*a)
      val root2: Double = (-1)*b-Math.sqrt(discriminant)/(2*a)
      System.out.println(s"Root1 = $root1")
      System.out.println(s"Root2 = $root2")
    }
  }
}
