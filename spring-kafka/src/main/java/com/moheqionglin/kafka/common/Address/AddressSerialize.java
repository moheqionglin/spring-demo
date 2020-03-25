package com.moheqionglin.kafka.common.Address;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Charsets;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

public class AddressSerialize implements Serializer<Address>  {

    @Override
    public void configure(Map<String, ?> map, boolean b) {

    }

    @Override
    public byte[] serialize(String s, Address person) {
        return JSON.toJSONString(person).getBytes(Charsets.UTF_8);
    }

    @Override
    public void close() {

    }
}
