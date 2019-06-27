package com.moheqionglin.matchdemo

object CaseClass {

  def main(args: Array[String]): Unit = {
    val cc = new CaseClass;
    cc.print(People("万里", 29))
    cc.print(Dog("小花", 2))

  }

}

case class People(name: String, age:Int);
case class Dog(name: String, age:Int);


class CaseClass{

  def print(ob: Any){
    ob match {
      case People(name: String, age:Int) => println(s"我是人， 名字 = $name, age= $age")
      case Dog(name: String, age:Int) => println(s"我是狗， 名字 = $name, age= $age")
    }
  }

}
