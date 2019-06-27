package com.moheqionglin.function

object 控制抽象 {


  /**
    *
    * 在用 breakAble的时候发现 scala支持把 代码块 传到一个函数中。
    *
    * 抽象控制： 对于没有入参的匿名函数，可以直接用 代码块传入。
    *
    **/
  def main(args: Array[String]): Unit = {
    def fu1 = (f1: () => Unit) => {
      new Thread{
        override def run(){
          f1()
        }
      }.start()
    }

    fu1(() => println(Thread.currentThread().getName + " 我是 匿名函数"))


    //控制抽象
    def fu2(f2: => Unit) = {
      new Thread{
        override def run(){
          f2
        }
      }.start()
    }

    fu2{
      println(Thread.currentThread().getName + " 我是 代码块， 本质上也是匿名函数")
    }


    /**
      * 自己实现while循环
      *
      * var x = 10
      * while(x > 0){
      *   println(s"x=$x")
      *   x -= 1
      * }
      *
      * */

    Thread.sleep(1000)

    var x = 10
    while(x > 0){
       println(s"x=$x")
       x -= 1
     }

    def whileUtil(condition : => Boolean)(codeBlock: => Unit): Unit ={
      if(condition){
        codeBlock;
        whileUtil(condition)(codeBlock)
      }
    }
    println("=====")

    x = 10
    whileUtil(x > 0){
      println(s"self code: x=$x")
      x -= 1
    }

  }

}
