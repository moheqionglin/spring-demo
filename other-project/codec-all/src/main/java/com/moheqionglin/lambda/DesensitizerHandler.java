package com.moheqionglin.lambda;

import java.util.function.Predicate;
import java.util.stream.Stream;

public class DesensitizerHandler {
    public static void main(String[] args) {
//        Stream.of("公孙无忌").map(SensitiveStrategy.USERNAME.getDesensitizer()).forEach(System.out::println);
//        Stream.of("411098189801012345").map(SensitiveStrategy.ID_CARD.getDesensitizer()).forEach(System.out::println);
//        Stream.of("19023412341").map(SensitiveStrategy.PHONE.getDesensitizer()).forEach(System.out::println);
//        Stream.of("北京市东城区东西路南门街493号109").map(SensitiveStrategy.ADDRESS.getDesensitizer()).forEach(System.out::println);

        String abc = "01232545";
        System.out.println(abc.indexOf(0) == '0');

//        System.out.println(SelfSelfBiPredicateStrategy.USERNAME.desensitizer.test("1", "1"));


    }
}
