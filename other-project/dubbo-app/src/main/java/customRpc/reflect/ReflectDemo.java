package customRpc.reflect;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wanli.zhou
 * @description
 * @time 09/11/2018 3:57 PM
 */
public class ReflectDemo {

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        Map<String, Invoker> stringInvokerMap = reportInterfaceMethod();
        Object result = invokeDubbo("customRpc.reflect.Calculator.add?params=int,int", stringInvokerMap);

        System.out.println("===>" + result);
    }

    private static Object invokeDubbo(String s,  Map<String, Invoker> methodMap) throws InvocationTargetException, IllegalAccessException {
        Invoker  invoker = (Invoker) methodMap.get("customRpc.reflect.Calculator.add?params=int,int");

        int count = (int) invoker.getMethod().invoke(invoker.getObject(), 1, 2);
        return count;
    }

    public static Map<String, Invoker> reportInterfaceMethod() throws ClassNotFoundException, NoSuchMethodException {
        Map<String, Invoker> objMap = new HashMap<>();

        Calculator calculator = new CalculatorImpl();
        Class<?> clazz = Class.forName("customRpc.reflect.CalculatorImpl");
        Method add = clazz.getMethod("add", int.class, int.class);

        objMap.put("customRpc.reflect.Calculator.add?params=int,int", new Invoker(add, calculator));
        return objMap;

    }
}

class Invoker{
    private Method method;
    private Object object;

    public Invoker(Method method, Object object) {
        this.method = method;
        this.object = object;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}