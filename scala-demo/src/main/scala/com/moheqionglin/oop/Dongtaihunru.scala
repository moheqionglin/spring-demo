package com.moheqionglin.oop

object Dongtaihunru{
  def main(args: Array[String]): Unit = {

    //创建动态混入时候， 构造方法执行顺序 从左到右
    val mysql = new MySQL with DB with File;
    println("--------")

    //方法执行顺序，  从右往左
    //             1. 遇到 方法中调用super， 那么super就是with左边的特质， 如果左边没有特质，才会轮到父类执行
    mysql.insert(1L);

    println("------")
    println("------")
    val mysql1= new MySQL with File with DB ;
    println("------")
    mysql1.insert(1)
  }

}
trait LoggedException extends Exception{

}
class BB extends AA with LoggedException{

}
//AA 必须是 Exception的子类， 否则违背了单继承
class AA extends Exception{

}
trait Operate{
  println("Operate...")

  def insert(id: Long)
}

trait Data extends Operate{
  println("Data ...")

  override def insert(id: Long) = {
    println("insert into Data ...")
  }
}


trait File extends Data{
  println("File...")

  override def insert(id: Long)= {
    println("insert into File ....")
    super.insert(id)
  }
}

trait DB extends Data{
  println("Db ....")
  override def insert (id: Long) = {
    println("insert into DB ... ")
    super.insert(id)
  }
}

trait MySQL {

}
