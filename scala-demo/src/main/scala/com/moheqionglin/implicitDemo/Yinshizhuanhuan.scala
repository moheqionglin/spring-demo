package com.moheqionglin.implicitDemo

object Yinshizhuanhuan {

  def main(args: Array[String]): Unit = {


    implicit def f1(double: Double):Int = {
      double.toInt
    }

    implicit def f2(dog: Dog): Cat = {
      new Cat
    }

    val a:Int = 4.5;

    val dog = new Dog;
    dog.funCat();

  }

}

class Dog {
  def funDog(): Unit ={
    println("----dog----")
  }
}

class Cat {
  def funCat(): Unit ={
    println("----cat----")
  }
}
