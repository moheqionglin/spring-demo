package com.moheqionglin.matchdemo

object TypeMatch {
  def main(args: Array[String]): Unit = {
    val a = 2

    val obj = if(a == 0){
      0
    }else if(a == 1) {
      "a"
    }else if (a == 2){
      Map[String, Int]("x" -> 2)
    }else if (a == 3){
      Map[Int, String](2 -> "x")
    }
    obj match {
      case v: Int => println("Int = " + v)
      case v: Map[String, Int] => println("Map[String, Int]  = " + v)
      case v: Map[Int, String] => println("Map[Int, String] = " + v)
      case v: String => println("String = " + v)
      case v: Any => println("any =" + v)
    }
  }

}
