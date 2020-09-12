package com.moheqionglin.com;

import org.kie.api.KieServices;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import java.util.HashMap;
import java.util.Map;

public class DroolsTest {
    public static final void main(String[] args) {
        String rule = "rule \"baseMessage1\"\n" +
                "\tno-loop true\n" +
                "\tlock-on-active true\n" +
                "\tsalience 1\n" +
                "\twhen\n" +
                "\t\t$re:Refuse(age<60&&age>=22)\n" +
                "\tthen\n" +
                "\t    $re.setAge(80);\n" +
                "\t    update($re);\n" +
                "\t    \n" +
                "\t\trefuseDate.put(\"name\",\"0\");\n" +
                "\t\tSystem.out.println(\"hello=======\");\n" +
                "end";

    	try {
    		Map<String,String> refuseDate=new HashMap<>();
	        KieServices ks = KieServices.Factory.get();
    	    KieContainer kContainer = ks.getKieClasspathContainer();

        	KieSession kSession = kContainer.newKieSession("session-base");

        	Refuse refuse=new Refuse();
            refuse.setAge(80);

            kSession.setGlobal("refuseDate",refuseDate);
            kSession.insert(refuse);

            int count=kSession.fireAllRules();
            System.out.println("规则执行条数：--------"+count);
            System.out.println("规则执行完成--------"+refuse.toString());
            System.out.println(kSession.getGlobals().toString());
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}