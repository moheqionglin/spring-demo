package com.moheqionglin.kafka.common.Address;


import com.alibaba.fastjson.JSON;
import com.google.common.base.Charsets;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

public class AddressDeserialize implements Deserializer<Address> {

    @Override
    public void configure(Map<String, ?> map, boolean b) {

    }

    @Override
    public Address deserialize(String s, byte[] bytes) {
        String personStr = new String(bytes, Charsets.UTF_8);
        Address person = JSON.parseObject(personStr, Address.class);
        return person;
    }

    @Override
    public void close() {

    }
}
