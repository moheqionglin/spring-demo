package com.moheiqonglin;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @ClassName : TxtCodec
 * @Description :
 * @Author : wanli.zhou
 * @Date: 2020-03-29 22:35
 */
public class TxtCodec {


    public static void main(String[] args) {
//        String filePath = "/Users/zhouwanli/Workspace/github/spring-demo/mohe-flink/pom.xml.s";
        //加密
//        codec(filePath, true);
        //解密
        codec("/Users/zhouwanli/Workspace/github/spring-demo/mohe-flink/src/main/scala/com/moheqionglin/flink/FlinkStatistics.scala.s", false);
    }

    private static void codec(String filePath, boolean jiami) {
        try {
            FileInputStream fileInputStream = new FileInputStream(filePath);
            byte[] buf = new byte[fileInputStream.available()];

            int k = fileInputStream.read(buf);

            for (int i=0;i<k;i++) {
                if(jiami){
                    buf[i]+=1;
                }else{
                    buf[i]-=1;
                }

            }
            FileOutputStream fo = new FileOutputStream(filePath+".s");

            fo.write(buf);
            fo.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}