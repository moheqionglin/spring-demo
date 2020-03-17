package com.moheqionglin.classLoader.hotreload;

import java.util.concurrent.TimeUnit;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-12-02 17:34
 *
 *  # 方法1：使用 springloaded
 * -javaagent:/Users/wanli.zhou/.m2/repository/org/springframework/springloaded/1.2.6.RELEASE/springloaded-1.2.6.RELEASE.jar -noverify
 *
 *  # 方法2：使用自定义的classLoader
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {

        BizService bizService = new BizService();
        while (true){
            bizService.doSth();
            TimeUnit.SECONDS.sleep(3);
        }
    }
}