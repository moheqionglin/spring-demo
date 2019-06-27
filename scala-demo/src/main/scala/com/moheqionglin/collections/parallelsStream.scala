package com.moheqionglin.collections

object parallelsStream {
  def main(args: Array[String]): Unit = {

    val list1 = 1 to 100000
    var start = System.currentTimeMillis()
    var a :Int= 0
    for(i <- 1 to 10000){
      a = list1.reduce(_+_)
    }
    println(a + " " + (System.currentTimeMillis() - start))

    start = System.currentTimeMillis()
    for(i <- 1 to 10000){
      a = list1.par.reduce(_+_)
    }
    println(a + " " + (System.currentTimeMillis() - start))
  }

}
