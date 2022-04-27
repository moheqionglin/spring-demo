package com.moheqionglin.rpc.dynamicproxy;

import com.moheqionglin.rpc.rpcInterface.Calculator;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Date;

public class Reflex {
    static class SelfCalculator implements Calculator{

        @Override
        public int add(int a, int b) {
            return a + b;
        }

        @Override
        public double multiple(int a, double b) {
            return a * b;
        }
    }
    public static Date addDayDate(Date date, int day) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, day);
        date = cal.getTime();
        return date;
    }

    public static void main(String[] args) {
        System.out.println(addDayDate(new Date(), -21));
        System.out.println(BigDecimal.valueOf(2.455).setScale(2, RoundingMode.UP));
        System.out.println(BigDecimal.valueOf(2.456).setScale(2, RoundingMode.UP));

        System.out.println(Double.valueOf(2.455));
        System.out.println(BigDecimal.valueOf(2.456).setScale(2, RoundingMode.UP));
    }
    public static void main1(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        Class<SelfCalculator> SelfCalculatorClass = SelfCalculator.class;
        Method add = SelfCalculatorClass.getMethod("add", new Class[]{int.class, int.class});
        SelfCalculator selfCalculator = new SelfCalculator();
        Object invoke = add.invoke(selfCalculator, 1, 2);
        System.out.println(invoke);
    }
}
