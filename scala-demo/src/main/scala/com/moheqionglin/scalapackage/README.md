- scala 的包名和 源代码的目录可以不一致， 但是编译器sbt会把class文件放到 对应包的目录中
- scala 一个源码文件可以创建并列的package。 package parentPackage1{ childpackage{ class ChileClass {}}} package parentPackage1{ childpackage{ class ChileClass {}}}
- scala 子包使用父package的源码可以不用import父包， 可以直接引入子包后直接使用父包的类。有重名的类默认从子包往外找。
- scala 父包使用子包的类必须要 import
- scala import语句可以随便放在任何一个位置，不用像java一样 放在最前面。
- 代码中使用@在代码中直接使用scala完整路径。  @com.scala.BeanProperties
- 包方法，包属性， scala允许在包中定义方法和属性。 
    - 每个包在子包中只能有且仅有一个 包对象
    - 保对象要和子包名字一样，如下 scalaDemo和 scalaDemo要一样
    - 包对象中可以定义方法和属性，同时可以再包里面直接使用属性和方法。
    - ** 包对象 底层实现 ** 
        - sbt编译以后会生成 一个 public final class package 和 public final class package$两个类
```aidl
package com.moheqionglin{
    package object scalaDemo{
    
    }
    package scalaDemo{
        class Demo{
        
        }
    }
}

```
    - 包可见性 