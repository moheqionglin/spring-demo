使用spring AOP的时候遇到了如下问题：<br>

```
Exception in thread "main" org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'org.springframework.context.event.internalEventListenerProcessor': Initialization of bean failed; nested exception is java.lang.IllegalArgumentException: error at ::0 formal unbound in pointcut  
```

为了复现这个问题，我专门写了一个程序。 [源码地址][1]
<br>核心代码我们罗列出来：<br>

### 配置类信息
```

@Configuration
@EnableAspectJAutoProxy
@ComponentScan(basePackages = {"com.moheqionglin.aop.warnning"})
public class JavaConfig {

}

```
### 两个 spring bean
```
@Component
public class Children extends Parent implements IInterface{
    protected void print(){
        System.out.println("Children --> ");
    }

    public void otherPublicMethod(){
        System.out.println("otherPublicMethod ->");
    }

    @Override
    public void iprint() {
        System.out.println("Children -> iprint >>");
    }
}


public interface IInterface {
    void iprint();
}

```

### Aspect 类

```

@Aspect
@Component
public class CustomAspect {

    @Around("execution(public * com.moheqionglin.aop.warnning.*.*(..))")
    public Object process(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("Before>>>>");
        
        Object proceed = joinPoint.proceed();
        
        System.out.println("After<<<<<");
        
        return proceed;
    }
}

```

### 主类执行

```
public class Main {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(JavaConfig.class);

        Children children = (Children) annotationConfigApplicationContext.getBean("children");
        children.print();


    }
}

```

### 原因剖析

spring的的AOP底层原理使用的是 代理模式。 spring会根据 @Aspect配置的过滤信息拦截对应的类，然后通过代理模式生成被拦截类的代理类。代理类的代理方法如下:<br>

```

    System.out.println("Before>>>>");
    
    Object proceed = joinPoint.proceed();
    
    System.out.println("After<<<<<");
    
    return proceed;
  
```

<br>
但是 代理模式有两种：

### JDK 动态代理实现 spring AOP (@EnableAspectJAutoProxy)

缺点是 被拦截的代理类必须要实现一个借口，同时在调用目标类的时候必须要用接口去强转，不能用当前类强转。 文字过于抽象。我们用代码说话。
<br>
####  我们先说 使用JDK动态代理实现 spring AOP的时候，被增强类必须要继承一个Interface，否则 springAOP无效

<br>注意 @EnableAspectJAutoProxy 这个注解默认使用的Java JDK的动态代理<br>

```
//被增强类Children 我们不然他继承接口IInterface ，然后其他代码不动的情况下。

@Component
public class Children{
    protected void print(){
        System.out.println("Children --> ");
    }

    public void otherPublicMethod(){
        System.out.println("otherPublicMethod ->");
    }

    public void iprint() {
        System.out.println("Children -> iprint >>");
    }
}

```

<br>执行结果如下：<br>

```
Children -->
```

<br>

证明 被增强类如果是一个没有继承接口的类，那么 使用jdk 动态代理不会被spring aop增强。
<br>

####  调用目标类的时候必须要用接口去强转，不能用当前类强转，否则会出现本文开始提到的那个异常。

```
// 我们修改main方法
    public static void main(String[] args) {
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(JavaConfig.class);

        IInterface children = (IInterface) annotationConfigApplicationContext.getBean("children");
        children.iprint();


    }

```

<br>执行结果如下<br>

```
Before>>>>
Children -> iprint >>
After<<<<<

```
<br>不会再出现如下异常： <br>
```
Exception in thread "main" java.lang.ClassCastException: com.sun.proxy.$Proxy16 cannot be cast to 
```

<br> 但是缺点是，不能再访问Children的其他方法了。

### 第二种是 CG lib动态代理方法 （@EnableAspectJAutoProxy(proxyTargetClass = true) ）
这种方法实现的spring AOP没有任何限制，可以对普通类进行增强。



### 结论
想要解决本文提到的这个异常有如下两种方法：
1. 方法一、@EnableAspectJAutoProxy(proxyTargetClass = true)  显示指定使用 cglib动态代理实现springAOP
2. 方法二、修改main方法使用 IInterface children = (IInterface) annotationConfigApplicationContext.getBean("children");获取Children对象，但是这种方法只能访问接口方法了。


[1]: https://github.com/moheqionglin/spring-demo/tree/master/spring-test/src/main/java/com/moheqionglin/aop/warnning

