package com.moheqionglin.collections

object MapTest {

  def main(args: Array[String]): Unit = {

    def p = (name:String, soce:Int) => println(s"$name, $soce");
    var map = Map[String, Int]("小花" -> 100, "小明" -> 99);
    map += ("小狗" -> 20);
    map += ("小狗" -> 30);
    map += ("小猪" -> 40, "小羊" -> 50)
    map.foreach{case (name, soce) => p(name, soce)}
    println()
    for((k, v) <- map){
      p(k, v)
    }
    println()
    //不存在抛异常
//    println(map("aa"))
    println(map.get("小花").orElse(null))
    println(map.get("小花").getOrElse(null))
    println(map.get("xx").getOrElse(null))
    println(map.getOrElse("xx", "xx"))
    println()
    map.map{case (name, soce) => p(name, soce)}
  }
}
