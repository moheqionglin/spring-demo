package com.moheqionglin.protobuf;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * @author wanli.zhou
 * @description
 * @time 23/11/2018 2:28 PM
 */
public class CodecTest {


    private static final String path = "/Users/wanli.zhou/Desktop/proto/";

    public static void main(String[] args) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(path + "protobuf.pro");

        PointDatasOuterClass.PointDatas.Builder builder = PointDatasOuterClass.PointDatas.newBuilder();
        for(int i = 0 ; i < 1300000; i ++){
            PointDatasOuterClass.PointData pointData = PointDatasOuterClass.PointData.newBuilder()
            .setGnsstime(1541944448000l)
            .setLat(31.341626)
            .setLon(121.497552)
            .setSpeed(0)
            .build();

            builder.addPoint(pointData);
        }

        builder.build().writeTo(fileOutputStream);
    }


    private static void jsonStr() throws IOException {
        FileChannel fileChannel = FileChannel.open(Paths.get(path), StandardOpenOption.CREATE, StandardOpenOption.WRITE);
        JSONArray jsonArray = new JSONArray();
        for(int i = 0 ; i < 1300000; i ++){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("gnssTime", 1541944448000l);
            jsonObject.put("lat", 31.341626);
            jsonObject.put("lon", 121.497552);
            jsonObject.put("speed", 0);
            jsonArray.add(jsonObject);
        }
        fileChannel.write(ByteBuffer.wrap(jsonArray.toJSONString().getBytes(StandardCharsets.UTF_8)));
        fileChannel.close();
    }
}