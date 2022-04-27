package com.moheqionglin.invocation;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Invocation {
    private Object obj;
    private Method method;
    private Object[] args;

    public Invocation(Object obj, Method method, Object[] args) {
        this.obj = obj;
        this.method = method;
        this.args = args;
    }

    public Object doInvocation() throws InvocationTargetException, IllegalAccessException {
        return method.invoke(obj, args);
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }
}
