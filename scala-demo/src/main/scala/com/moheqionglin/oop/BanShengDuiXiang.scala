package com.moheqionglin.oop

import scala.beans.BeanProperty



  class BanClass{
    private var privateName = "[C] private name";
    var defaultName = "[C] default name";
    @BeanProperty
    var beanProperty = "[C] bean property name";

    def print = () => println(s"privateName = $privateName \n defaultName=$defaultName\n beanProperty=$beanProperty")

    def apply: Unit = println("[C] apply");
  }

  object BanClass{
    private var privateOName = "[O] private name";
    var defaultOName = "[O] default name";
    @BeanProperty
    var beanOProperty = "[O] bean property name";

    def apply: BanClass = {
      println("[O]BanClass apply")
      new BanClass()
    }

    def printName() = println(s"privateOName= $privateOName, defaultOName=$defaultOName, beanOProperty=$beanOProperty")

  }



  class TClass {
    var tclassVar = "TClass var";

    def printN = println(s"> tclassVar = $tclassVar");
  }


  object OT extends TClass {
    def ot = println(s"---")
  }

object mainClass{
  def main(args: Array[String]): Unit = {
    val bClass = new BanClass();
    println(bClass.defaultName);

    val oClass = BanClass;
    oClass;

    BanClass.printName()

    println("------")
    println(OT.tclassVar)
    OT.printN
  }




}