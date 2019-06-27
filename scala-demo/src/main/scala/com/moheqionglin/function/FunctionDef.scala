package com.moheqionglin.function

/*
*
* */
object FunctionDef {

  def main(args: Array[String]): Unit = {
    var function1 = sum _ ;
    println(function1(1, 2))

    var function2 = (a:Int, b:Int) => {
      a + b
    }

    println(function2(1, 2))

    println(calc(1, 2, '+'))
  }

  //return可以默认省略，最后一句就是return的值
  def sum(a:Int, b:Int):Int = {
    a + b
  }

  def calc(a:Int, b:Int, op:Char) = {
    if(op == '-'){
      a - b
    }else if (op == '+'){
      a + b
    }else {
      null
    }
  }

}
