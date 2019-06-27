package com.moheqionglin.oop

object aaa{
  def main(args: Array[String]): Unit = {
    val oc = new OutterClass;
    val ic = new oc.InnerClass;
    ic.printa()

  }
}
class OutterClass {

  myOutter => class InnerClass{
    def printa(): Unit ={
      println(s" outter.a = ${myOutter.a}, outter.b = ${myOutter.b}")
    }
  }

  private var a: String = "a";
  var b = "b"
}
