package com.moheqionglin

object Fuhao {

  def main(args: Array[String]): Unit = {
    //::
    val l:List[String]  = "A"::"B"::"C"::"D"::Nil;
    l.foreach(x => print(x))

    println()
    //+:
    val strings = "E" +: l
    strings.foreach(x => print(x))
    println()
    l.foreach(x => print(x))
    println()
    //:+
    val strings1 =  l :+ "Z"
    strings1.foreach(x => print(x))
    println()
    l.foreach(x => print(x))

    // ++
    println()
    (strings ++ strings1).foreach(x => print(x))

    //:::
    println()
    (strings ::: strings1).foreach(x => print(x))
    println()
      trait Animal{
        def name:String
      }
      class Dog extends Animal{
        override def name: String = "dog"
      }
      class Cat extends Animal{
        override def name: String = "cat"
      }
      class Pig extends Animal{
        override def name: String = "pig"
      }

      def printName[T <: Animal](a : T){
        println(a.name)
      }

      printName(new Dog)






  }


}
