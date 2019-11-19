package com.moheqionglin.dubboSingleTcp;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-10-29 11:28
 */
public class DubboXXXServiceInterface {

    CustomDubboRemoveInvoke customDubboRemoveInvoke = null;

    public DubboXXXServiceInterface(CustomDubboRemoveInvoke customDubboRemoveInvoke) {
        this.customDubboRemoveInvoke = customDubboRemoveInvoke;
    }

    public Object doxxx() throws InterruptedException {
        String server = "127.0.0.1:/dubbo?par=1";
        Invocation invocation = new Invocation("className", "methodName", new Class[]{int.class, int.class});
        CustomDubboResponseFuture responseFuture = customDubboRemoveInvoke.invoke(server, invocation);

        Object o = responseFuture.get();
        System.out.println("DubboXXXServiceInterface res = " + o);
        return o;
    }

}