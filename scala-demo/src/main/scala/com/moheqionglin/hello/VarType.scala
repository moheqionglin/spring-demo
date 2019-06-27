package com.moheqionglin.hello

object VarType {

  /*
  *
  * var 变量
  * val 常量 -- 不存在线程安全问题，scala 推荐做法。
  *
  * 类型：
  *   AnyVal
  *   AnyRef
  * */
  def main(args: Array[String]): Unit = {
    val voidV = Unit;
    val nullV = null;
    val anything = AnyRef;


    //数字
    var byteV: Byte = Byte.MaxValue;
    var shortV: Short = Short.MaxValue;
    var intV: Int = Int.MaxValue;
    var longV: Long = Long.MaxValue;
    var floadV: Float = Float.MaxValue;
    var double: Double = Double.MaxValue;

    //字符串
    var strV: String = "str";

    //字符串
    var charV : Char = '中';

    //bool
    var boolV: Boolean = false;

    var anyValueV: AnyVal = 1;
    var anyRefV : AnyRef = new Animal;

    println(byteV);
    println(shortV);
    println(intV);
    println(longV);
    print(charV + ", "+ charV.toShort)
    println(floadV);
    println(double);
    println(strV.getClass);
    println(boolV);
    println(anyValueV.getClass)
    println(anyRefV)

  }

  class Animal{
    var name: String = "";

    override def toString = s"Animal()"
  }
}
