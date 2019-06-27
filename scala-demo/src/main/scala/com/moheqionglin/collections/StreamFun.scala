package com.moheqionglin.collections

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

object StreamFun {

  def main(args: Array[String]): Unit = {
    val text = Array[String]("I am a boy", "Just a moment");

//    text.flatMap(_.toLowerCase().split("\\W+")).map(println)
//
//    text.flatMap(_.toLowerCase().split("\\W+")).map((_, 1)).map(println)
//
//    text.flatMap(_.toLowerCase().split("\\W+")).map((_, 1)).groupBy(_._1).map(println)
    //word count map.groupby.map
    text.flatMap(_.toLowerCase().split("\\W+")).map((_, 1)).groupBy(_._1).map(a => (a._1, a._2.length)).map(println);
    //foldleft
    val map = new mutable.HashMap[String, Int]()
    text.flatMap(_.toLowerCase().split("\\W+")).foldLeft(map)((m, ele) => {
      m.put(ele, m.getOrElse(ele, 0) + 1)
      m
    })
    println(map)
    //==========flatMap==========
    //递归 text, 直到完全扁平
    println("==>" + text.flatMap(_.toLowerCase).map(x => x.toString).filter(!_.equalsIgnoreCase(" ")).reduce((x, y) => x + "-" + y))


    println()
    //==========reduce========== 遍历集合每个元素 => 返回一个数
    val list: List[Int] = List(1, 2, 3, 4, 5)
    //递归 (((1 - 2) - 3) - 4) - 5 = -13   i1是结果， i2是list元素
    println("==>" + list.reduceLeft((i1, i2) => {println(i1 + " " + i2); i1 - i2}))
    //遍历 1 - (2 - (3 - (4 - 5)))  i1是元素  i2是结果
    println("==>" + list.reduceRight((i1, i2) => {println(i1 + " " + i2); i1 - i2}))
    //递归 (min(min(min(1, 2), 3), 4), 5)
    println("最小值: " + list.reduce((x, y) => Math.min(x, y)))

    println()
    //==========fold==========
    //相当于 (6, 1, 2, 3, 4, 5).reduceLeft  (((6 - 1) - 2) - 3) - 4) - 5 = -9
    println("=foldLeft=>" + list.foldLeft(6)((x, y) => { x - y}))
    // 相当于 (1, 2, 3, 4, 5, 6).reduceRight   1 - (2 - (3 - (4 - (5 - 6)))) = -3
    println("=foldRight=>" + list.foldRight(6)((x, y) => { x - y}))

    val charList = ArrayBuffer[String]()
    list.foldLeft(charList)((cl: ArrayBuffer[String], ele: Int) => {
      cl.append(ele.toString)
      cl
    })
    println(charList)

    //折叠简写 左折叠/:    右折叠:\
    println("==>" + (5 /: list)(_ - _))
    println("==>" + text.flatMap(_.toLowerCase).map(x => x.toString).filter(!_.equalsIgnoreCase(" ")).fold(5)((x, y) => x + "-" + y))

    println()
    //==========scan==========
    // (6, 1, 2, 3, 4, 5).fold 每一步的结果放入集合中返回
    println("=scanLeft=>" + list.scanLeft(6)(_ - _))
    // 1 - (2 - (3 - (4 - (5 - 5)))) = -2
    println("=scanRight=>" + list.scanRight(6)(_ - _))


    //zip
    val list1 = List(1, 2, 3)
    val list2 = List(4, 5, 6)
    println(list1.zip(list2))


    //stream
    def numStream(n : Int): Stream[Int] = n #:: numStream(n + 1)
    val stream = numStream(1);

    println(stream)
    println(stream.head)
    println(stream.tail)

    //view 输出能被3整除的数
    val list3 = 1 to 5;
    def filter1(n: Int) :Boolean =  {
      println("被调用")
      n % 3 == 0
    }
    val var1 = list3.filter(filter1)
    val var2 = list3.view.filter(filter1)
    println(var1)
    println(var2)

    for(i <- var2){
      println(i)
    }
    println()
    for(i <- var2){
      println(i)
    }

    //并行化

    val list4 = 1 to 16;
    list4.foreach(x => println(Thread.currentThread().getId))
    println()
    list4.par.foreach(x => println(Thread.currentThread().getId))
  }


}
