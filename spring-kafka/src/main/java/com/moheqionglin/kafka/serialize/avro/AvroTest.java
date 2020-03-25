package com.moheqionglin.kafka.serialize.avro;

import com.twitter.bijection.Injection;
import com.twitter.bijection.avro.GenericAvroCodecs;
import org.apache.avro.Schema;
import org.apache.avro.file.DataFileReader;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;

import java.io.*;
import java.util.Random;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-05-29 16:56
 */

public class AvroTest {
    Random random = new Random();
    public static void main(String[] args) throws IOException {
    }

    public void serializeWithSchema() throws IOException {
        AvroPerson person1 = new AvroPerson();
        person1.setId(1);
        person1.setAge(2L);
        person1.setLon(10.5f);
        person1.setName("xx1");
        person1.setSuccess(true);

        AvroPerson person2 = new AvroPerson(2, "xx3", 2L, 10.5f,true);


        AvroPerson person3 = AvroPerson.newBuilder()
                .setId(3)
                .setAge(4L)
                .setLon(10.6f)
                .setName("xx4")
                .setSuccess(false)
                .build();

        DatumWriter<AvroPerson> userDatumWriter = new SpecificDatumWriter<AvroPerson>(AvroPerson.class);
        DataFileWriter<AvroPerson> dataFileWriter = new DataFileWriter<AvroPerson>(userDatumWriter);
        dataFileWriter.create(person1.getSchema(), new File("/Users/wanli.zhou/Desktop/users.avro"));
        dataFileWriter.append(person1);
        dataFileWriter.append(person2);
        dataFileWriter.append(person3);
        dataFileWriter.close();
    }

    public void serializeWithOutSchema() throws IOException {
        DatumReader<AvroPerson> reader = new SpecificDatumReader<>(AvroPerson.class);
        DataFileReader<AvroPerson> fileReader = null;
        fileReader = new DataFileReader<AvroPerson>(new File("/Users/wanli.zhou/Desktop/users.avro"), reader);
        AvroPerson user = null;
        while (fileReader.hasNext()) {
            user = fileReader.next(user);
            System.out.println(user);
        }
    }

    public void serializeWithOutSchema1() throws IOException {
        InputStream resourceAsStream = new FileInputStream(ClassLoader.getSystemResource("./").getPath()+"../../src/main/java/com/moheqionglin/kafka/seridescri/avro/AvroPerson.avsc");
        Schema.Parser parser = new Schema.Parser();
        Schema schema = parser.parse(resourceAsStream);

        GenericData.Record record = new GenericData.Record(schema);
        record.put("id", 1);
        record.put("name", "xxxxx");
        record.put("age", 2L);
        record.put("lon", 10.5f);
        record.put("success", true);

        File file = new File("/Users/wanli.zhou/Desktop/users1.avro");
        DatumWriter<GenericRecord> datumWriter = new GenericDatumWriter<GenericRecord>(schema);
        DataFileWriter<GenericRecord> dataFileWriter = new DataFileWriter<GenericRecord>(datumWriter);
        dataFileWriter.create(schema, file);
        dataFileWriter.append(record);
        dataFileWriter.close();
    }


    public void deserializeWithOutSchema1() throws IOException {
        InputStream resourceAsStream = new FileInputStream(ClassLoader.getSystemResource("./").getPath()+"../../src/main/java/com/moheqionglin/kafka/seridescri/avro/AvroPerson.avsc");
        Schema.Parser parser = new Schema.Parser();
        Schema schema = parser.parse(resourceAsStream);

        File file = new File("/Users/wanli.zhou/Desktop/users1.avro");
        DatumReader<GenericRecord> datumReader = new GenericDatumReader<GenericRecord>(schema);
        DataFileReader<GenericRecord> dataFileReader = new DataFileReader<GenericRecord>(file, datumReader);
        GenericRecord user = null;
        while (dataFileReader.hasNext()) {
            user = dataFileReader.next(user);
            System.out.println(user);
        }
    }



    public byte[] avroPersonWithOutSchemaSeri() throws IOException {
        InputStream resourceAsStream = new FileInputStream(ClassLoader.getSystemResource("./").getPath()+"../../src/main/java/com/moheqionglin/kafka/seridescri/avro/AvroPerson.avsc");
        Schema.Parser parser = new Schema.Parser();
        Schema schema = parser.parse(resourceAsStream);

        Injection<GenericRecord,byte[]> injection = GenericAvroCodecs.toBinary(schema);

        //带schema方式
        GenericData.Record record = new GenericData.Record(schema);
        record.put("id", random.nextInt(1000000));
        record.put("name", "xxxxx");
        record.put("age", random.nextLong());
        record.put("lon", 10.5f);
        record.put("success", random.nextBoolean());
        byte[] bytes = injection.apply(record);
        return bytes;
    }

    public AvroPerson avroPersonWithOutSchemaDeSeri(byte[] bytes) throws IOException {
        InputStream resourceAsStream = new FileInputStream(ClassLoader.getSystemResource("./").getPath()+"../../src/main/java/com/moheqionglin/kafka/seridescri/avro/AvroPerson.avsc");
        Schema.Parser parser = new Schema.Parser();
        Schema schema = parser.parse(resourceAsStream);
        Injection<GenericRecord,byte[]> injection = GenericAvroCodecs.toBinary(schema);
        GenericRecord genericRecord = injection.invert(bytes).get();
        return null;
    }
}