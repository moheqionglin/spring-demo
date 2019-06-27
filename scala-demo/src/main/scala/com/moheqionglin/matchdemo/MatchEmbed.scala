package com.moheqionglin.matchdemo

/**
  * 现在有一些商品，请使用 Scala 设计相关的样例类，完成商品捆绑打折出售。
  * 要求 1) 商品捆绑可以是单个商品，也可以是多个商品。
  * 2) 打折时按照折扣 x 元进行设计.
  * 3) 能够统计出所有捆绑商品打折后的最终价格
  *
  * */
object MatchEmbed {

  abstract class Item
  case class Book(description: String, price: Float) extends Item
  case class Food(description: String, price: Float) extends Item

  case class Bundle(description: String, discount: Float, item : Item*) extends Item

  def main(args: Array[String]): Unit = {
    //
    println(47F * 0.1F )
    println((( ((50F + 50F) * 0.1F )+ ((50F + 50F) * 0.1F ) + 50F )* 0.1F + 40F ) * 0.1F)
    val saleItem = Bundle("书籍", 0.1F,
      Book("三国", 40F),
      Bundle("自然科学", 0.1F,
        Book("xxx权威指南", 50F),
        Bundle("计算机", 0.1F,
          Book("JAVA权威指南", 50F),
          Book("SCALA权威指南", 50F)
        ),
        Bundle("机械", 0.1F,
          Book("机械权威指南", 50F),
          Book("机械2权威指南", 50F)
        )
      ))

    //取出属性
    saleItem match {
      case Bundle(_, _, Book(des, price), _*) => println("book des = " + saleItem + ", price = " + price)
    }
    //取出对象
    saleItem match {
      case Bundle(_, _, book @Book(_, _), _*) => println("book book = " + book.toString)
    }
    //取出余下的
    saleItem match {
      case Bundle(_, _, book @Book(_, _), res) => println("book book = " + book.toString + ", res=" + res)
    }

    //能够统计出所有捆绑商品打折后的最终价格
    println(calcPrice(saleItem))

  }

  def calcPrice(item: Item): Float ={
    item match {
      case Book(_, price) => price
      case Food(_, price) => price
      case Bundle(_, discount, items @_*) => items.map(calcPrice).sum * discount
    }
  }


}
