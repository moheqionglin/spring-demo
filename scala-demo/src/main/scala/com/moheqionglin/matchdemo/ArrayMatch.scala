package com.moheqionglin.matchdemo

object ArrayMatch {

  def main(args: Array[String]): Unit = {

    for (a <- Array(Array(0), Array(1, 0), Array(0, 1, 0), Array(1, 1, 0), Array(0, 1, 0, 1))){
      a match {
        case Array(0) => println ("只有一个元素为0的数组")
//        case Array(0, _*) => println "0开头的数组"
        case Array(x, y) => println (s"只有两个元素 $x $y")
        case Array(1 , _*) => println("1开头的其他list")
        case _ => println("不处理")
      }
    }
  }

}
