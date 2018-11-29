package com.moheqionglin.nioVsTrandionalIo.zeroCopy;

import sun.nio.ch.DirectBuffer;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wanli.zhou
 * @description
 * @time 22/11/2018 10:13 PM
 */
public class ReadTest {
    private static final String FILE_PATH = "/Users/wanli.zhou/Downloads/dump.o2hprof.index";

    /**
     *
     * @throws IOException
     * 每次很平均，都是410ms左右
     */
    public static void jvmBufferIO() throws IOException {
        //在JVM内部有一块缓存
        long start = System.currentTimeMillis() ;
        FileInputStream in = new FileInputStream(FILE_PATH);
        byte jvmBuffer[] = new byte[in.available()];
        in.read(jvmBuffer);
        long end = System.currentTimeMillis() - start;
        System.out.println("jvmBufferIO Costs " + end);
    }

    /**
     *
     * @throws IOException
     * 不平均，第一次是 370ms左右， 后面基本上250ms左右
     */
    public static void directBufferIO() throws IOException {
        long start = System.currentTimeMillis();
        FileChannel fileChannel = FileChannel.open(Paths.get(FILE_PATH), StandardOpenOption.READ);
        ByteBuffer directBuffer = ByteBuffer.allocateDirect((int)fileChannel.size());
        fileChannel.read(directBuffer);
        long end = System.currentTimeMillis() - start;
        System.out.println("directBufferIO costs " + end);

    }

    //22 ms
    public static void mappedMemoryIO() throws IOException {
        long start = System.currentTimeMillis();
        FileChannel fileChannel = FileChannel.open(Paths.get(FILE_PATH), StandardOpenOption.READ);
        MappedByteBuffer map = fileChannel.map(FileChannel.MapMode.READ_ONLY, 0, fileChannel.size());
        long end = System.currentTimeMillis() - start;
        System.out.println("mappedMemoryIO costs " + end);

    }

    public static void main(String[] args) throws IOException, InterruptedException {
//        jvmBufferIO();
//        directBufferIO();
        mappedMemoryIO();


    }
}