package com.moheqionglin.kafka.Serializer.person;


import com.alibaba.fastjson.JSON;
import com.google.common.base.Charsets;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

public class PersonDeserialize implements Deserializer<Person> {

    @Override
    public void configure(Map<String, ?> map, boolean b) {

    }

    @Override
    public Person deserialize(String s, byte[] bytes) {
        String personStr = new String(bytes, Charsets.UTF_8);
        Person person = JSON.parseObject(personStr, Person.class);
        return person;
    }

    @Override
    public void close() {

    }
}
