package com.moheqionglin

object _Example {

  def main(args: Array[String]): Unit = {
    //1. 将函数赋值给一个变量
    def a = (x:Int, y:Int) => x + y;
    val b = a
    println(b(1, 3));

    val s = Seq(1, 2, 3, 4, 5);
    val res = s.map(x => x ).reduce(_ + _);

    println(res)
    //2.
//    def sum = (x:Int*) => x.foreach()
    val range = 1 to 5;
//    val seq = (1 to 5: _*)
    println(range.getClass)
//    println(seq.getClass)
  }
}
