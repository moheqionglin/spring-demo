## scala 理念
- 任何一个函数 尽量不使用外部变量，因此推崇 for循环代替while循环， 因为while循环要在while外部定义外部变量
## scala 跟java 不一样的
- 基本
    - scala不支持三目运算， java中  ? : 在 scala中用 if else
    - scala 不支持switch， 被替换成模式匹配
    - scala for循环中  for(i <- array ) 相当于java中的 for( i : array)
    - scala for循环没有 continue 和 break
    - scala for 引入循环守卫 for(i <- 1 to 3 if i !=2) 等价于 for ( i <- 1 to 3){ if (i != 2){...} }
    - scala while 循环用的比较少
- 函数
    - 支持高阶函数： 入参是函数的 函数。 比如 breakable
- scala 没有static关键词， 但是引入了伴生对象 object， 伴生对象中的都是java中的static的东西。
- scala class中 属性默认是private的， 方法默认是public的。 如果scala 显示的在属性前面加上private， 那么 scala就不会编译的时候生成public的getter setter方法。
    