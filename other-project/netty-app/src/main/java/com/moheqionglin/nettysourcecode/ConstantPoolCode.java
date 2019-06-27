package com.moheqionglin.nettysourcecode;

import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelOption;
import io.netty.channel.MessageSizeEstimator;
import io.netty.channel.RecvByteBufAllocator;
import io.netty.channel.WriteBufferWaterMark;
import io.netty.util.AbstractConstant;
import io.netty.util.Constant;
import io.netty.util.ConstantPool;
import io.netty.util.internal.PlatformDependent;

import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.concurrent.ConcurrentMap;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-05-31 13:37
 */
public class ConstantPoolCode {

    class ConstantPool1<T extends Constant<T>>{
        private final ConcurrentMap<String, T> constants = PlatformDependent.newConcurrentHashMap();
    }

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        ChannelOption channelOption = ChannelOption.AUTO_READ;
        Field poolField = ChannelOption.class.getDeclaredField("pool");
        poolField.setAccessible(true);

        ConstantPool<ChannelOption<Object>> o1 = (ConstantPool<ChannelOption<Object>>) poolField.get(channelOption);
        Field constantsField = ConstantPool.class.getDeclaredField("constants");
        constantsField.setAccessible(true);

        ConcurrentMap o = (ConcurrentMap) constantsField.get(o1);
        System.out.println(o);
    }

}