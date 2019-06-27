package com.moheqionglin.oop


//class Cat(inName:String, insex:Boolean, inage:Int) {
object Cat{
  //默认都是private的属性， 底层会自动生成 public getter/setter方法
  var name: String = "小花"
  var sex : Boolean = true
  var age : Int = 1

}

class Cat1(inname:String, insex:Boolean, inage:Int){
  //默认都是private的属性， 底层会自动生成 public getter/setter方法
  var name: String = "小花"
  var sex : Boolean = true
  var age : Int = 1

}
object oop{
  def main(args: Array[String]): Unit = {
    type selfType = (Int, Int);

    val a :selfType = (1, 2);
    val (x, y) = a;
    println(a.getClass)
    println(s"x = $x, y = $y")
    //这里为什么cat可以访问private属性， 是因为 这段代码底层会编译生成 setter
    //    cat.name = "小花"
    //    cat.sex = true;
    //    cat.age = 1;


  }
}