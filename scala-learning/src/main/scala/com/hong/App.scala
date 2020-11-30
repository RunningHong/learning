package com.hong

/**
  *
  * 创建一个Scala的Object
  * New -> Scala Class -> kind中选择Object
  */
object App {
	def main(args: Array[String]): Unit = {
		println( "Hello World!" )

		val z = Array("aaaaa", "bbbbb", "ccccc")

		// 依次赋值变量
		val Array(name1, name2, name3) = z

		println(name1)
		println(name2)
		println(name3)


		// 类似python中的一种赋值方式
		val bbb = s"${name1}abc"
		println(bbb)


	}



}
