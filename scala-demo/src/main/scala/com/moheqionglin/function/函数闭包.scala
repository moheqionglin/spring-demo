package com.moheqionglin.function

object 函数闭包 {

  def main(args: Array[String]): Unit = {
    /**
      *1) 返回一个匿名函数， 该匿名函数引用到外部函数的x， 那么 x和该匿名函数整体形成一个闭包。
      *2） 函数闭包存在于所有支持函数编程语言中， 也就是凡是把函数当成一等公民的语言的都有闭包。
      *
      *
      * */
    def fun1 = (x : Int) => {
      (y: Int) => {
        x - y
      }
    }

    def f1 = fun1(20)

    println(f1(1))
    println(f1(2))
  }
}
