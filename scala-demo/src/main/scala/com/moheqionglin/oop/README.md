- object scala 底层会生成两个java class字节码。 class 修饰的dcala 底层编译生成一个java class
- scala 的class 里面属性默认是private的， 一个class Cat{ var name:String = _}, 其实对应java class会变成 class Cat{private String name; public Getter/Setter} 。
- class 默认是public的class， 而且不能写 public class 因为 public 不是关键字
- 属性在定义的时候可以使用 _ 来让scala编译的时候自动赋初始值。 数字类型的默认值时0， 浮点类型默认值 0.0， String等引用类型默认是 null， bool默认是false。
- 对象创建流程
    - 加载class字节码， 把class的方法和属性加载在方法区（持久代）
    - 内存中（堆）开辟空间
    - 主构造器 对属性初始化
    - 使用辅助构造器初始化
    
