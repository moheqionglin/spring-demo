package com.moheqionglin.collections

import java.util
import java.util.Date

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer


object seq{

  def main(args: Array[String]): Unit = {
    scalaTransferJava
  }

  //定长数组
  def arrayTets: Unit = {
    //方法-1
    val array = new Array[Int](3)
    array(0) = 0
    array(1) = 1
    array(2) = 2
    array.map(println)
    println
    //方法-2
    val array1 = Array(3, 4, 5);
    array1.map(println)

    println
    for( el <- array){
      println(el)
    }
    println

    for(i <- 0 until array.length){
      println(array(i))
    }



  }

  //变长数组
  def arrayBuffer: Unit = {
    val arrayBuffer = new ArrayBuffer[Int](6);
    //增加
    arrayBuffer.append(1, 2, 3);
    println(arrayBuffer.hashCode())

    //修改
    arrayBuffer.append(1, 2, 3);
    arrayBuffer += 4;
    println(arrayBuffer)

    arrayBuffer -= 4;
    println(arrayBuffer.hashCode())
    println(arrayBuffer)
    println()

    arrayBuffer(0) = 0;
    println(arrayBuffer.hashCode())
    println(arrayBuffer)
    println()

    //删除
    arrayBuffer.remove(0);
    println(arrayBuffer.hashCode())
    println(arrayBuffer)
    println()
  }

  //array 和 arraybuffer转换， 也就是定长和变长转换
  def arrayToArrayBuffer: Unit ={
    val array = Array('A', 'B', 'C')
    //不可变数组转 可变数组
    val arrayBuffer = array.toBuffer;
    println(arrayBuffer)

    //可变转不可变
    val array1 = arrayBuffer.toArray
    println(arrayBuffer)
    println()
  }

  //多维数组
  def dimArray: Unit ={
    // a b c
    // d e f
    // g h i
    //3 行 4列
    val arr = Array.ofDim[Char](3, 4);
    arr(0)(0) = 'a'
    arr(0)(1) = 'b'
    arr(0)(2) = 'c'
    arr(1)(0) = 'd'
    arr(1)(1) = 'e'
    arr(1)(2) = 'f'
    arr(2)(0) = 'g'
    arr(2)(1) = 'h'
    arr(2)(2) = 'i'

    arr.foreach( ar => {
      print("第一行 \t")
      ar.foreach(a => {
        print(a + "\t")
      })
      println
    })

    arr.last.map(a => print(a + "\t"))
    println()
    arr.head.map(a => print(a + "\t"))
  }

  //scala 和 java list 互转
  def scalaTransferJava(): Unit ={
    //scala ArrayBuffer -> java List
    val arrBuffer = ArrayBuffer("1", "2", "3");
    import scala.collection.JavaConversions.bufferAsJavaList
    println(arrBuffer)
    val builder = new ProcessBuilder(arrBuffer)

    val strings = builder.command()
    println(strings);

    //java List -> scala ArrayBuffer
    val javalist: util.List[Integer]  = new util.ArrayList[Integer]();
    javalist.add(1)
    javalist.add(2)
    javalist.add(3)

    import scala.collection.JavaConversions.asScalaBuffer
    val scalaArray: mutable.Buffer[Integer] = javalist;
    println(scalaArray)
  }

  //Tuple 最多22个元素,  函数想返回两个元素，又不想单独定义个临时对象， 用tuple很好
  //底层Tuple 反编译成java的一个 Tuple1 Tuple2 ... Tuple20的对象
  def tupleDemo: Unit ={
    //创建
    val tuple1 = (1, "AAA", new Date());

    //访问 _1
    println(tuple1._1)
    println(tuple1.productElement(0)) //用到了模式匹配

    //编列
    for(item <- tuple1.productIterator){
      print(item + "\t")
    }
    println
  }
}

