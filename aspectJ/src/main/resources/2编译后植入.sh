
#!/usr/bin/env bash
#javac -sourcepath src/main/java -cp  ~/.m2/repository/org/aspectj/aspectjrt/1.8.9/aspectjrt-1.8.9.jar  -d target/classes src/main/java/com/moheqionglin/HelloWorld.java

ASPECTJ_WEAVER=/Users/zhouwanli/.m2/repository/org/aspectj/aspectjweaver/1.8.13/aspectjweaver-1.8.13.jar
ASPECTJ_RT=/Users/zhouwanli/.m2/repository/org/aspectj/aspectjrt/1.8.9/aspectjrt-1.8.9.jar
ASPECTJ_TOOLS=/Users/zhouwanli/.m2/repository/org/aspectj/aspectjtools/1.8.9/aspectjtools-1.8.9.jar

# 跟1的区别是， 1是直接用java源码调用 $ASPECTJ_RT 生产 最终的class字节码， 这个是通过已有的class字节码生产class字节码
java -jar $ASPECTJ_TOOLS -cp $ASPECTJ_RT -source 1.8 -inpath target/classes -d target/classes

#java -cp /Users/zhouwanli/.m2/repository/org/aspectj/aspectjrt/1.8.9/aspectjrt-1.8.9.jar:/Users/zhouwanli/Workspace/github/spring-demo/aspectJ/target/classes com.moheqionglin.HelloWorld