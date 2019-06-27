package com.moheqionglin.function

object FunctionDetail {

  def main(args: Array[String]): Unit = {
    def fun2(): Unit ={
      println("FunctionDetail -> fun1")
    }

    fun1(par2="22")

    lazy val res = sum(1, 2);
    println(res)

  }

  def fun1(par1: String = "par1-default", par2: String): Unit ={
    println(s"$par1, $par2")
  }

  def sum(a:Int, b :Int):Int = {
    println("调用sum函数")
    a + b
  }

}
