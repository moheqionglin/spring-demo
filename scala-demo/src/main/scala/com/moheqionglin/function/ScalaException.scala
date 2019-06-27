package com.moheqionglin.function

object ScalaException {

  def main(args: Array[String]): Unit = {
    try{
      val a = 1 / 0;
    }catch {
      case ex : Exception => ex.printStackTrace()
    }finally{
      println("--finally")
    }
    println("-- after try-catch")
  }

}
