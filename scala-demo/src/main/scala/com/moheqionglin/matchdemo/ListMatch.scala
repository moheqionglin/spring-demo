package com.moheqionglin.matchdemo

object ListMatch {

  def main(args: Array[String]): Unit = {
    List(1, 2, 3, 4) match {
      case first :: second :: resList => println(first + " " + second + " " + resList)
    }

    List(1, 2, 3, 4) match {
      case 1 :: second :: resList => println(1 + " " + second + " " + resList)
    }

    for(l <- Array(List(1, 2), List(0), List(1,2, 3, 4, 5, 6))){
      l match {
        case 1 :: x :: Nil => println("1 开头的 List长度为2 的 " + x)
        case x:: Nil => println("只有一个元素的 list " + x)
        case _ :: 2 :: _ => println("2是第二个元素的 ")
      }
    }
  }
}
