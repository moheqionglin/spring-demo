package com.moheqionglin.test

object SeqTest {


  def main(args: Array[String]): Unit = {
    val add = (x:String) => x + 1;
    val list = List("a", "b", "c");

    //scala中 {} 和() 可以相互替换
    val strings = list.map{add}
    val strings1 = list.map(add)

    println(strings);
    println(list)
    print1("aaaa")
    print1{"----"}


    Seq(
      Array("alice",   20,   10f, "2019-01-05",   "2019-01-05"),
      Array("bill",    21,   11f, "2019-01-05",    "2019-01-05"),
      Array("bob",     30,   12f, "2019-01-05",     "2019-01-05"),
      Array("charles", null, 12f, "2019-01-05", "2019-01-05")
    ).map { entry =>{
        println(entry.head.toString + "\t" + entry.last);

//        println(entry: _*)
      }
    }

  }


  def print1(name:String):Unit = {
    println(name)
  }
}
