package com.moheqionglin.implicitDemo

object Yinshihanshu {

  def main(args: Array[String]): Unit = {
    //-- demo
    implicit val str1: String = "万里~" //这个就是隐式值
    //implicit name: String :name 就是隐式参数
    def hello(implicit name: String): Unit = {
      println(name + " hello")
    }
    hello //底层 hello$1(str1);

    // 当一个隐式参数匹配不到隐式值，仍然会使用默认值
    def hello0(implicit content: Int = 3): Unit = {
      println("Hello " + content)
    } //调用 hello
    hello0

    //隐士值 + 默认值
    def hello2(implicit content: String = "jack"): Unit = {
      println("Hello " + content) } //调用 hello
    hello2

//    当没有隐式值，没有默认值，又没有传值，就会报错
    def hello4(implicit content: Double): Unit = {
      println("Hello4 " + content)
    } //调用 hello
//    hello4 // hello3 jack

  }
}
