package com.moheqionglin.hello

object HelloWorld {

  def main(args: Array[String]): Unit = {
    print("Hello Scala")
    var name:String = "周万里"
    var age:Int = 10
    var sal: Float = 10.67f
    var heigh:Double = 180.1d

    printf("姓名=%s, %d, %.2f, %.3f \n", name, age, sal, heigh)

    println(s"姓名=$name, $age, $sal, $heigh, ${age + 1}")
//    println(f"姓名=%s, %d, %.2f, %.3f \n", name, age, sal, heigh);

    var str: String =
      """
        |adsf
        |asdf
        |asdf
        |asdf
        |asdf
      """;
    println(str)
  }
}
