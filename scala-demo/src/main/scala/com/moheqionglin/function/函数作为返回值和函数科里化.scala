package com.moheqionglin.function

object 函数作为返回值和函数科里化 {

  def main(args: Array[String]): Unit = {

    def calcFun = (x : Char) => {
      x match {
        case '+' => (x: Double, y : Double) => x + y
        case '-' => (x: Double, y : Double) => x - y
        case '*' => (x: Double, y : Double) => x * y
        case '/' => (x: Double, y : Double) => x / y
      }
    }

    def fun1 = calcFun('-')
    println(fun1(3, 2))
    /**
      * 函数柯里化
      * 1） 函数编程过程中， 多参函数一定可以转换成多个 单个参数的函数。
      * 2)  目的是一个函数只干一件事情。
      *
      * */
    println(calcFun('+')(3, 2))

    def add (x: Int)(y: Int): Int = {
      x + y
    }
    println(add(1)(2))
  }
}
