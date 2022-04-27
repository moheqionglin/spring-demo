#!/usr/bin/env bash

#javac -sourcepath src/main/java -cp  ~/.m2/repository/org/aspectj/aspectjrt/1.8.9/aspectjrt-1.8.9.jar  -d target/classes src/main/java/com/moheqionglin/HelloWorld.java

ASPECTJ_WEAVER=/Users/zhouwanli/.m2/repository/org/aspectj/aspectjweaver/1.8.13/aspectjweaver-1.8.13.jar
ASPECTJ_RT=/Users/zhouwanli/.m2/repository/org/aspectj/aspectjrt/1.8.9/aspectjrt-1.8.9.jar
ASPECTJ_TOOLS=/Users/zhouwanli/.m2/repository/org/aspectj/aspectjtools/1.8.9/aspectjtools-1.8.9.jar

java -javaagent:$ASPECTJ_WEAVER -cp $ASPECTJ_RT:/Users/zhouwanli/Workspace/github/spring-demo/aspectJ/target/classes/ com.moheqionglin.HelloWorld