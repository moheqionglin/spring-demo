package com.moheqionglin.hello

object BitCal {

  def main(args: Array[String]): Unit = {
    //左移 n位 相当于乘以  2^n
    val a = 3;
    print((a << 1) + ", " + (a << 2) + "\n")
    //右移 n位，相当于 除以 2^n
    var byte: Byte = 1;
    print((byte << 7).toByte + ", " + (Byte.MaxValue))
  }

  def sum(a:Int, b: Int): Int = {
    return a + b
  }

}
