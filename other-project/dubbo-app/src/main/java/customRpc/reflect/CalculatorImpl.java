package customRpc.reflect;

import customRpc.protocol.AClass;
import customRpc.protocol.BClass;

/**
 * @author wanli.zhou
 * @description
 * @time 09/11/2018 3:59 PM
 */
public class CalculatorImpl implements Calculator{
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