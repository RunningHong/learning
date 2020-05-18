object Hello {
  def main(args: Array[String]): Unit = {

    def name: String="hongzh.zhang";
    println(name)
    print_hello(11);
  }

  def print_hello(x:Int): Unit = {
    println("hello")

    if (x > 10) {
      println(x + " is larger 10")
    } else {
      println(x + " is lower 10 or equal 10")
    }

    val numSet = Set(1,3,5,7)
    for (i <- numSet) {

      println(i)
    }
  }
}
