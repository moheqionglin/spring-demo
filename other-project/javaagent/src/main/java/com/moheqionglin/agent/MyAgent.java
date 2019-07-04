package com.moheqionglin.agent;

import java.lang.instrument.Instrumentation;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-07-04 11:18
 */
public class MyAgent {

    public static void premain(String agentArgs, Instrumentation inst){
        System.out.println("Invoke preadmin " + agentArgs + ", inst " + inst);
        inst.addTransformer(new MyTransformer());
    }

    public static void premain(String agentArgs){
        System.out.println("Invoke preadmin " + agentArgs);
    }
}