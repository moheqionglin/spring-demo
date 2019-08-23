package com.moheqionglin.agent;

import javassist.*;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.reflect.Method;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-07-04 11:20
 */
public class MyTransformer implements ClassFileTransformer {

    // 被处理的方法列表
    private final static Map<String, List<String>> methodMap = new HashMap<>();
    public MyTransformer() {
        //对指定方法监控
        add("com.moheqioinglin.agent.test.AgentTest.queryUserAge");
        add("com.moheqioinglin.agent.test.AgentTest.queryUserName");
    }
    private void add(String methodString) {
        String className = methodString.substring(0, methodString.lastIndexOf("."));
        String methodName = methodString.substring(methodString.lastIndexOf(".") + 1);
        List<String> list = methodMap.get(className);
        if (list == null) {
            list = new ArrayList<String>();
            methodMap.put(className, list);
        }
        list.add(methodName);
    }
    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                            ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        className = className.replace("/", ".");
        // 判断加载的class的包路径是不是需要监控的类
        if (methodMap.containsKey(className)) {
            CtClass ctclass = null;
            try {
                // 使用全称,用于取得字节码类<使用javassist>
                ctclass = ClassPool.getDefault().get(className);
                for (String methodName : methodMap.get(className)) {
                    String outputStr = " System.out.println(\"监控信息(执行耗时)：" + className + "." + methodName + " => \" +(endTime - startTime) +\"毫秒\");";

//                    String outputStr = " System.out.println(\"监控信息(执行耗时)：" + className + "." + methodName + " =>  (endTime - startTime) 毫秒\");";
                    // 得到这方法实例
                    CtMethod ctmethod = ctclass.getDeclaredMethod(methodName);
                    javassistGetInfo(ctmethod);
                    System.out.println();
                    // 新定义一个方法叫做比如queryUserAge$new
                    String newMethodName = methodName + "$new";
                    // 将原来的方法名字修改
                    ctmethod.setName(newMethodName);
                    // 复制原来的方法，创建新的方法，名字为原来的名字
                    CtMethod newMethod = CtNewMethod.copy(ctmethod, methodName, ctclass, null);
                    // 构建新的方法体
                    StringBuilder methodBodyStr = new StringBuilder();
                    methodBodyStr.append("{");
                    methodBodyStr.append("long startTime = System.currentTimeMillis();");
                    methodBodyStr.append(newMethodName + "($$);");// 调用原有代码，类似于method();($$)表示所有的参数
                    methodBodyStr.append("long endTime = System.currentTimeMillis();");
                    methodBodyStr.append(outputStr);
                    methodBodyStr.append("}");
                    newMethod.setBody(methodBodyStr.toString());// 替换新方法
                    System.out.println(" 方法 => " + methodBodyStr.toString());

                    ctclass.addMethod(newMethod); // 增加新方法
                }
                return ctclass.toBytecode();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private void javassistGetInfo(CtMethod ctm ) throws Exception{

        MethodInfo methodInfo = ctm.getMethodInfo();
        CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
        LocalVariableAttribute attribute = (LocalVariableAttribute)codeAttribute.getAttribute(LocalVariableAttribute.tag);

        int pos = Modifier.isStatic(ctm.getModifiers()) ? 0 : 1;
        for (int i=0;i<ctm.getParameterTypes().length;i++) {
            System.out.print(ctm.getParameterTypes()[i]+" "+attribute.variableName(i+pos));
            if (i<ctm.getParameterTypes().length-1) {
                System.out.print(",");
            }
        }

        System.out.print(")");

        CtClass[] exceptionTypes = ctm.getExceptionTypes();
        if (exceptionTypes.length>0) {
            System.out.print(" throws ");
            int j=0;
            for (CtClass cl : exceptionTypes) {
                System.out.print(cl.getName());
                if (j<exceptionTypes.length-1) {
                    System.out.print(",");
                }
                j++;
            }
        }

    }
}