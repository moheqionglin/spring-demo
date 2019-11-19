package com.moheqionglin.dubboSingleTcp;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-10-29 11:23
 */
public class Main {

    public static void main(String[] args) throws InterruptedException {
        //保存 requestId -> ResponseFuture的关系
        CustomDubboRemoveInvoke customDubboRemoveInvoke = new CustomDubboRemoveInvoke();
        //保存 requestId -> Response的关系
        DubboNettySocketHandler dubboNettySocketHandler = new DubboNettySocketHandler();
        //封装 invocation，调用 DubboNettySocketHandler 发送请求
        DubboXXXServiceInterface dubboXXXServiceInterface = new DubboXXXServiceInterface(customDubboRemoveInvoke);


        new Thread(()->{
            try {
                dubboNettySocketHandler.select();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        Object doxxx = dubboXXXServiceInterface.doxxx();
        System.out.println("Main.class " + doxxx);
    }

}