package com.moheqionglin.nioVsTrandionalIo.ByteBufferVsByteArrays;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.util.Arrays;

/**
 * @author wanli.zhou
 * @description
 * @time 25/10/2018 11:23 AM
 */
public class ByteBufferDemo {
    public static void main(String[] args) throws IOException {
        ByteBufferDemo byteBufferDemo = new ByteBufferDemo();
        byteBufferDemo.forReadByteBuffer();
    }

    public void forReadByteBuffer(){
        ByteBuffer byteBuffer = ByteBuffer.allocate(5);
        byteBuffer.put((byte) 'A');
        byteBuffer.put((byte) 'B');
        byteBuffer.put((byte) 'C');
        byteBuffer.put((byte) 'D');
        byteBuffer.put((byte) 'E');

        byteBuffer.flip();
        for(; byteBuffer.remaining() >= 0;){
            System.out.println((char)byteBuffer.get());
        }
    }

    public void arrayTest(){
        ByteBuffer byteBuffer = ByteBuffer.allocate(6);

        byteBuffer.putInt(10);
        byteBuffer.put((byte) 'A');
        byteBuffer.put((byte) 'B');

        System.out.println("==> 还剩余" + byteBuffer.remaining());
        byteBuffer.flip();
        System.out.println("==>第一个int值 " + byteBuffer.getInt());
        //limit了
        byteBuffer.position(4);
        byteBuffer.limit(6);
        System.out.println(byteBuffer.remaining());
        System.out.println((char)byteBuffer.get());
        System.out.println(byteBuffer.array().length);
        System.out.println(Arrays.copyOfRange(byteBuffer.array(), byteBuffer.position() + byteBuffer.arrayOffset(), byteBuffer.limit() + byteBuffer.arrayOffset()));

    }

    /**
     * 这个方法目的演示 byteBuffer1.put(一个超过容量的byteBuffer的情况)
     *
     * */
    public void append(){
        ByteBuffer byteBuffer = ByteBuffer.allocate(6);

        ByteBuffer byteBuffer2 = ByteBuffer.allocate(5);
        byteBuffer2.put("ABCDE".getBytes());

        byteBuffer.putInt(10);
        //报异常
//        byteBuffer.put(byteBuffer2);
        byteBuffer.put(byteBuffer2.array(), 0, byteBuffer.remaining());
        System.out.println("==> " + byteBuffer.remaining());
        byteBuffer.flip();
        System.out.println(byteBuffer.getInt());
        System.out.println((char)byteBuffer.get());
        System.out.println((char)byteBuffer.get());

    }


    public void appendReadFileTest() throws IOException {
        System.out.println("初始化一个 8个字节容量的bytebuffer");
        ByteBuffer byteBuffer = ByteBuffer.allocate(8);
        System.out.println("byteBuffer put 一个int，占四个字节");
        byteBuffer.putInt(1);
        System.out.println("此时 byteBuffer 还有多少可以写入的空间"  + byteBuffer.remaining());
        FileChannel fileChannel = FileChannel.open(Paths.get(this.getClass().getClassLoader().getResource("./byteBufferTest.txt").getPath()));
        fileChannel.read(byteBuffer);
        System.out.println("完成读取ABCDE的文件。 一个字符一个byte");

        System.out.println("此时 byteBuffer 还有多少可以写入的空间 " + byteBuffer.remaining());
        System.out.println("flip 开始读取");
        byteBuffer.flip();
        System.out.println("此时 byteBuffer 还有多少可以读取的空间"  + byteBuffer.remaining());
        System.out.println("读取： " + byteBuffer.getInt());
        System.out.println("此时 byteBuffer 还有多少可以读取的空间"  + byteBuffer.remaining());
        System.out.println("读取： " + (char)byteBuffer.get());
        System.out.println("此时 byteBuffer 还有多少可以读取的空间"  + byteBuffer.remaining());
        System.out.println("读取： " + (char)byteBuffer.get());
        System.out.println("此时 byteBuffer 还有多少可以读取的空间"  + byteBuffer.remaining());
        System.out.println("读取： " + (char)byteBuffer.get());
        System.out.println("此时 byteBuffer 还有多少可以读取的空间"  + byteBuffer.remaining());
        System.out.println("读取： " + (char)byteBuffer.get());
        System.out.println("此时 byteBuffer 还有多少可以读取的空间"  + byteBuffer.remaining());
    }

    public void remainTest(){
        ByteBuffer byteBuffer = ByteBuffer.allocate(9);

        byteBuffer.putInt(12);
        System.out.println(byteBuffer.remaining());
        byteBuffer.putInt(12);
        System.out.println(byteBuffer.remaining());
        byteBuffer.putInt(1);
        System.out.println(byteBuffer.remaining());
    }
}