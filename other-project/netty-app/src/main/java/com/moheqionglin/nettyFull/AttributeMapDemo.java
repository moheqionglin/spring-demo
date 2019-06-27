package com.moheqionglin.nettyFull;

import io.netty.util.AttributeKey;
import io.netty.util.AttributeMap;
import io.netty.util.DefaultAttributeMap;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-06-12 10:33
 */
public class AttributeMapDemo {
    public static void main(String[] args) {
        DefaultAttributeMap defaultAttributeMap = new DefaultAttributeMap();

        AttributeKey<Integer> intKey = AttributeKey.valueOf("intKey");
        AttributeKey<Boolean> boolKey = AttributeKey.valueOf("boolKey");
        AttributeKey<Long> longKey = AttributeKey.valueOf("longKey");
        AttributeKey<Double> doubleKey = AttributeKey.valueOf("doubleKey");
        AttributeKey<Float> floatKey = AttributeKey.valueOf("floatKey");

        defaultAttributeMap.attr(intKey).set(10);
        defaultAttributeMap.attr(boolKey).set(false);
        defaultAttributeMap.attr(longKey).set(1L);
        defaultAttributeMap.attr(doubleKey).set(4d);
        defaultAttributeMap.attr(floatKey).set(4f);

        System.out.println(intKey.id() + "\t" + intKey.name() + "\t" + defaultAttributeMap.attr(intKey).get());
        System.out.println(boolKey.id() + "\t" + boolKey.name() + "\t" + defaultAttributeMap.attr(boolKey).get());
        System.out.println(longKey.id() + "\t" + longKey.name() + "\t" + defaultAttributeMap.attr(longKey).get());
        System.out.println(doubleKey.id() + "\t" + doubleKey.name() + "\t" + defaultAttributeMap.attr(doubleKey).get());
        System.out.println(floatKey.id() + "\t" + floatKey.name() + "\t" + defaultAttributeMap.attr(floatKey).get());


    }
}