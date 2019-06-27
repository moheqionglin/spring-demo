package com.moheqionglin.oop

object WithTest {

  def main(args: Array[String]): Unit = {

    val clazz2 = new Class1
    val clazz1 = new Class1 with Trait1
    val clazz3 = new Class1 with Trait2
    val clazz4 = new Class1 with Trait1 with Trait2

    println("---")
    clazz2.class1();
    println("---")
    clazz1.trait1();
    println("---")
    clazz3.trait2();
    println("---")
    clazz4.class1()
    clazz4.trait2()
    clazz4.trait1()

  }

  trait Trait1{
    def trait1(): Unit ={
      println(this.getClass + "trait1 - > trait1")
    }
  }

  trait Trait2{
    def trait2(): Unit ={
      println(this.getClass +  "trait2 - > trait2")
    }
  }

  class Class1{
    def class1(): Unit ={
      println(this.getClass +  "class1 - > class1")
    }
  }



  class Class2 extends Trait1 with Trait2{

  }


}
