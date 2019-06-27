# 函数 VS 方法
- scala 函数式编程： 就是把函数地位提升到一等公民， （类似于面向对象编程中的对象一样）。 函数可以像普通变量一样 创建， 也可以当做一个方法参数进行传递。
- 和JAVA的函数式变成不一样地方： java函数式编程中， 函数底层本质是一个接口。但是 scala函数就是和普通变量一样的东西。
- 面向对象编程是以对象为基础的编程，  函数式编程是面向对象编程中融入了 函数是编程
- 为什么需要函数
```

输入两个数，再输入一个运算符号，得到结果。
java实现
int a, b;
String oper;

if(oper == "+"){
    return a + b
}else if (oper == "-"){
    ..
}

这样太多if-else 所以引入了 function


```

## 函数注意细节
- scala 空参函数， 被调用的时候可以缺省()， 比如 def fun = "abcd"， 因为fun是空参省略(), 函数体只有一行省略{}, 等价于 def fun(): String = {"abcd"}
- scala 函数中可以不用return， 那么scala会自己腿短返回值。 但是吐过明确使用了 return那么定义函数时候必须写明白返回值类型。
- scala 函数行医的时候 没有写 = 那么证明没返回值，比如 def fun (a: Int, b:int){ return a + b} ， 写了return
也是空返回
- scala 函数返回值写成了 Unit， 那么有没有return 都表明无返回值。
- 如果函数返回至不确定类型， 那么不谢返回值类型， 或者写 Any 比如： def (n : Int) = {if (n == 1) "str else 1}
- scala 函数是一等功明， 所以 函数中可以声明函数。
- scala 函数默认值 def fun(a: String = "name){}
- def fun(par1: String= "default", par2 : String = "default"), 如果调用时指向覆盖 par2， 那么调用时候可以 fun(par2="---")
- 可变参数： def fun(a: Int*){} , 可变参数只能放到最右边，跟java一样
- 惰性函数： lazy val a = fun(), 那么fun直到用a的时候才执行， lazy不能修饰 var
- scala 复用了java的exception 类，但是没有编译异常， scala try-catch-finally 只有一个catch， catch中有case区分不同异常
- scala 引入 @throws注解 ，标示这个函数有可能抛异常 @throws(classOf[Exception])