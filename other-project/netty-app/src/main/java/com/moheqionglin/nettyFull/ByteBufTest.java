package com.moheqionglin.nettyFull;

import com.moheqionglin.nettyCustomrProtocol.FileSaveHandler;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.Unpooled;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author wanli.zhou
 * @description
 * @time 02/11/2018 1:37 PM
 */
public class ByteBufTest {

    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger();
    }

    public void nioBufferTest() {
        ByteBuf byteBuf = Unpooled.buffer(6);
        System.out.println("readableBytes : " + byteBuf.readableBytes() + ", writableBytes = " + byteBuf.writableBytes());

        byteBuf.writeByte((int) 'A');
        byteBuf.writeByte((int) 'B');
        byteBuf.writeByte((int) 'C');
        System.out.println("readableBytes : " + byteBuf.readableBytes() + ", writableBytes = " + byteBuf.writableBytes());
        System.out.println("nioBuffer " + byteBuf.nioBuffer());

        System.out.println((char) byteBuf.readByte());
        System.out.println("readableBytes : " + byteBuf.readableBytes() + ", writableBytes = " + byteBuf.writableBytes());
        System.out.println("nioBuffer " + byteBuf.nioBuffer());

        System.out.println((char) byteBuf.readByte());
        System.out.println("readableBytes : " + byteBuf.readableBytes() + ", writableBytes = " + byteBuf.writableBytes());
        System.out.println("nioBuffer " + byteBuf.nioBuffer());
        System.out.println("nioBuffer " + (char) byteBuf.readByte());
    }


    public void arrayTest() {
        ByteBuf byteBuf = Unpooled.buffer(6);

        System.out.println("readableBytes : " + byteBuf.readableBytes() + ", writableBytes = " + byteBuf.writableBytes());

        byteBuf.writeByte((int) 'A');
        byteBuf.writeByte((int) 'B');
        byteBuf.writeByte((int) 'C');
        System.out.println("readableBytes : " + byteBuf.readableBytes() + ", writableBytes = " + byteBuf.writableBytes());
        System.out.println("array " + byteBuf.array().length);

        System.out.println((char) byteBuf.readByte());
        System.out.println("readableBytes : " + byteBuf.readableBytes() + ", writableBytes = " + byteBuf.writableBytes());
        System.out.println("array " + byteBuf.array().length);


    }
}