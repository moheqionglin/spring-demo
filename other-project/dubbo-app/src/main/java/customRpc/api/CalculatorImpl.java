package customRpc.api;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author wanli.zhou
 * @description
 * @time 09/11/2018 3:59 PM
 */
@Component
public class CalculatorImpl implements Calculator {

    @PostConstruct
    public void init(){
        System.out.println("-CalculatorImpl->");
    }

    @Override
    public int add(int a, int b) {
        new AClass().print();
        new BClass().print();
        new BClass().print();
        new BClass().print();
        new BClass().print();
        return a + b;
    }

    @Override
    public int sub(int a, int b) {
        return a - b;
    }
}