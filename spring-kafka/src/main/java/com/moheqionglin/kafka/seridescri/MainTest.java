package com.moheqionglin.kafka.seridescri;

import com.google.protobuf.InvalidProtocolBufferException;
import com.moheqionglin.kafka.seridescri.avro.AvroPerson;
import com.moheqionglin.kafka.seridescri.protobuf.compile.PersonFactory;
import com.moheqionglin.kafka.seridescri.protostuff.ProStuffPerson;
import com.twitter.bijection.Injection;
import com.twitter.bijection.avro.GenericAvroCodecs;
import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.runtime.RuntimeSchema;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;

import java.io.*;
import java.util.Random;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-05-29 15:32
 */
public class MainTest {
    Random random = new Random();
    static RuntimeSchema<ProStuffPerson> poSchema = RuntimeSchema.createFrom(ProStuffPerson.class);

    public static void main(String[] args) throws IOException {
        MainTest mainTest = new MainTest();
    }

    public byte[] protobufSeri(){
        PersonFactory.Person.Builder person = PersonFactory.Person.newBuilder();
        PersonFactory.Person personOb = person.setId(random.nextInt(1000000))
                .setAge(random.nextLong())
                .setName("xxxxx")
                .setLon(10.5f)
                .setSuccess(random.nextBoolean()).build();
        return personOb.toByteArray();

    }

    public PersonFactory.Person protobufDeSeri(byte[] bytes) throws InvalidProtocolBufferException {
        return PersonFactory.Person.parseFrom(bytes);
    }


    public byte[] protoStuffSeri(){
        ProStuffPerson person = new ProStuffPerson(random.nextInt(1000000), "xxxxx", random.nextLong(), 10.5f, random.nextBoolean());
        return ProtostuffIOUtil.toByteArray(person, poSchema, LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
    }

    public ProStuffPerson protoStuffDeSeri(byte[] bytes){
        ProStuffPerson person = poSchema.newMessage();
        ProtostuffIOUtil.mergeFrom(bytes, person, poSchema);
        return person;
    }


}