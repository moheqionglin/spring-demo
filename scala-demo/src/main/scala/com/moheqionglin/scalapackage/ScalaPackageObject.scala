package com.moheqionglin{

  //包对象
  package object scalapackage{
    var packageNameP:String = "scalapackage 包对象"
    def sum(a:Int, b:Int) :Int ={
      a + b
    }
  }

  
  package scalapackage{



    class ScalaPackageObject(inName:String, inAge:Int) {
      import scala.beans.BeanProperty
      @BeanProperty
      var name = inName;
      var age = inAge;

      def sum1(a:Int, b:Int): Int ={
        sum(a, b);
      }

      def printPackageObjectPro(): Unit ={
        println(packageNameP)
      }
    }

    object main{
      def main(args: Array[String]): Unit = {
        var scalaPackageObject = new ScalaPackageObject("周万里", 29);
        println(scalaPackageObject.sum1(1, 2))
        scalaPackageObject.printPackageObjectPro()
      }
    }


  }
}

