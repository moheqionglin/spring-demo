package com.moheqionglin.oop

object ScalaConstructor {

  def main(args: Array[String]): Unit = {
    var sp = new ScalaParent;
    println("<<<<<--->>>>>")
    var scalaChild = new ScalaChild(">-<");
    println("<<<<<--->>>>>")
    var sc2 = new ScalaChild("--;;", 1)


  }
}

class ScalaParent{
  println("scala parent main cons");

  var name: String = _

}

class ScalaChild(name: String) extends ScalaParent{
  println("scala child main cons");
  var age:Int = _;
  def this(inname:String, inage: Int){
    this(inname)
    this.age = inage;
    println("scala child not main cons");

  }
}