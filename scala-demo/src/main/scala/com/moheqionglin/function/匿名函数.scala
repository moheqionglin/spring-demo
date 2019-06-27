package com.moheqionglin.function

object 匿名函数 {

  def main(args: Array[String]): Unit = {
    //() => {} 是匿名函数，  注意 匿名函数不需要返回值。 返回值自动推倒
    def funName = (x: Double) => {
      x * 3
    }
    println(funName(2))
  }


}
