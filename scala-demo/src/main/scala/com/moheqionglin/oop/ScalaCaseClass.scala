package com.moheqionglin.oop


/*
*
* */
case class DogC(inName:String, insex:Boolean, inage:Int);


object oopxx{
  def main(args: Array[String]): Unit = {
    var cat:DogC = new DogC("小花", true, 1);

    val DogC(x, y, z) = cat;

    print(s"x= $x, y = $y, z= $z")
    //这里为什么cat可以访问private属性， 是因为 这段代码底层会编译生成 setter
    //    cat.name = "小花"
    //    cat.sex = true;
    //    cat.age = 1;

  }
}