package com.moheqionglin.nioVsTrandionalIo.zeroCopy;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wanli.zhou
 * @description
 * @time 27/11/2018 10:25 AM
 */
public class TestDirectMemory {
    public static void main(String[] args) throws InterruptedException {
        List<ByteBuffer> list = new ArrayList<>();
        for(int i = 0 ; i < 1000; i ++){
            if(list.size() >= 9){
                list = new ArrayList<>();
            }
            ByteBuffer directBuffer = ByteBuffer.allocateDirect(1024 * 1024);
            list.add(directBuffer);
            Thread.sleep(300);

        }
    }
}