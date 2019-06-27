package com.moheqionglin.function

object 高阶函数 {

  def main(args: Array[String]): Unit = {

    def add = (x: Double, y : Double) => x + y
    def min = (x: Double, y : Double) => x - y

    def calc = (fun: (Double, Double) => Double, x: Double, y: Double) => {
      fun(x, y)
    }

    println(calc(add, 3, 2))
    println(calc(min, 3, 2))
  }
}
