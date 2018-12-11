使用spring AOP的时候遇到了如下问题：<br>

发现同一个类中调用自己类的被增强的方法，发现没有被spring AOP增强。

为了复现这个问题，我专门写了一个程序。 [源码地址][1]
<br>核心代码我们罗列出来：<br>

### 配置类信息
```

@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)  //使用CG-Lib的可以基于类
@ComponentScan(basePackages = {"com.moheqionglin.aop.warnning2"})
public class JavaConfig {


}

```
### spring bean
```
@Component
public class SimpleBean{

    @SimpleValid("void valid")
    public void print(){
        System.out.println("SimpleBean --> ");
    }

}

```

### Aspect 类

```
@Aspect
@Component
public class CustomAspect {

    @Around("execution(public * com.moheqionglin.aop.warnning2.*.*(..)) && @annotation(simpleValid)")
    public void process(SimpleValid simpleValid, ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("Before>>>>");
        joinPoint.proceed();
        System.out.println("After<<<<<");
    }
}

```

### 主类执行

```
    public static void main(String[] args) {
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(JavaConfig.class);

        SimpleBean simpleBean = (SimpleBean) annotationConfigApplicationContext.getBean("simpleBean");
        simpleBean.print();


    }

```

### 原因剖析
spring Around增强方式必须要求被@Around注解的方法有如下要求
1. 有返回值
2. 第一个参数必须是 ProceedingJoinPoint joinPoint


### 结论
想要解决本文提到的这个异常,  正确代码如下：

```
    @Around("execution(public * com.moheqionglin.aop.warnning2.*.*(..) ) && @annotation(simpleValid)")
    public Object process(ProceedingJoinPoint joinPoint, SimpleValid simpleValid) throws Throwable {
        System.out.println("Before>>>>");
        Object obj = joinPoint.proceed();
        System.out.println("After<<<<<");

        return obj;
    }

```

[1]: https://github.com/moheqionglin/spring-demo/tree/master/spring-test/src/main/java/com/moheqionglin/aop/warnning2

