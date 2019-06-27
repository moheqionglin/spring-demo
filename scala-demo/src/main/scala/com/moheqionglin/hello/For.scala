package com.moheqionglin.hello

import util.control.Breaks._
object For {

  def main(args: Array[String]): Unit = {

    {//break 底层实现是 直接抛出异常

      print("Break\t")
      for (i <- 1 to 10){
        if(i > 3){
//          break()
        }
        print(i + "\t")
      }

      println()
      print("Break + breakable\t")
      breakable(
        for (i <- 1 to 10){
          if(i > 3){
            break()
          }
          print(i + "\t")
        }
      )

    }
    {//用if-else或者循环守卫实现 continue
      print("continue 循环守卫 \t")
      for (i <- 1 to 10 if i != 1){
        print(i + "\t")
      }


      print("\n continue if-else\t")
      for (i <- 1 to 10 ){
        if(i != 1)
          print(i + "\t")
      }

    }

    {

      print("\n左右闭合 for循环， [1, 10]\t")
      for (i <- 1 to 10){
        print(i + "\t")
      }
      println()
      print("左闭合, 右开 for循环, [1, 10)\t")
      for (i <- 1 until 10){
        print(i + "\t")
      }
      println()
      print("左闭合, 右开 for循环, [1, 10)\t")
      for (i <- 1 to 10 reverse){
        print(i + "\t")
      }
    }
    {//循环守卫
      println("\n循环守卫")
      for (i <- 1 to 10 if i > 3){
        print(i + "\t")
      }

      println("\n等价于")
      for (i <- 1 to 10){
        if (i > 3)
        print(i + "\t")
      }
    }
    {//引入变量
      println("\n引入变量")
      for (i <- 1 to 10; j = i - 2){
        print(i + ", " + j + "\t")
      }
    }
    {//嵌套循环
      println("\n嵌套循环")
      for (i <- 1 to 4; j <- 5 to 7){
        print(i + ", " + j + "\t")
      }
      println("\n等价于")
      for (i <- 1 to 4){
        for(j <- 5 to 7)
        print(i + ", " + j + "\t")
      }
    }

    {//循环返回值
      println("\n循环返回值")
      val res = for (i <- 1 to 4) yield i;
      print(res)
      println("\n 相当定义一个 vector，然后逐个元素加入vector")
      println("应用")
      val res1 = for ( i <- 1 to 4) yield i * 2
      print(res1)
    }

    {//乘法
      println()
      for(i <- 1 to 9; j <- 1 to i){
        print(s"$i * $j = ${i * j} \t" )
        if(i == j)
          println()
      }

    }
  }
}
