package com.moheqionglin.function
/*
*
* 递归
* */
object recurisve {

  def main(args: Array[String]): Unit = {

    rec1(4)
    println("====")
    rec2(4)
    println("===")
    println(fun(3))
    println("====")
    println(houzi(1))

    println(add(0, 0))
  }

  def add(start: Int, sum: Long): Long = {
    if (start >= 999999) return sum
    add(start + 1, sum + start)
  }

  def rec1(n: Int){
    if(n > 2){
      rec1(n - 1)
    }
    println(n)
  }

  def rec2(n: Int){
    if(n > 2){
      rec2(n - 1)
    }else{
      println(n)
    }
  }

  /*
  *
  * 斐波那契数
  * 递归实现 求出 斐波那契数 1， 1， 3， 5， 8， 13...
  * 求第n个斐波那契数是多少
  * */

  def feibo(n:Int):Int = {
    if(n == 1 || n ==2){
      return 1;
    }
    feibo(n - 1) + feibo(n - 2)

  }

  /*
  * f(1) = 3; f(n) = 2*f(n-1) + 1
  * 递归求 f(n)
  * */
  def fun(n :Int): Int = {
    if(n == 1){
      return 3
    }
    2 * fun(n -1) + 1
  }

  /*
  * 猴子吃桃子： 第一天吃了一半多吃一个， 后面都是这样， 第十天还没有吃的时候，发现只有一个桃子，问之前多少桃子
  * day(10) = 1
  * day(9) = (day(10) + 1) * 2
  * ...
  * day(1) = (day(2) + 1) * 2
  * */

  def houzi(day :Int): Int = {
    if(day == 10){
      return 1;
    }
     (houzi(day + 1 ) + 1) * 2


  }
}
