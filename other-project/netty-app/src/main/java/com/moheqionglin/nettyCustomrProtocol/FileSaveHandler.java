package com.moheqionglin.nettyCustomrProtocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.*;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;

/**
 * @author wanli.zhou
 * @description
 * @time 01/11/2018 11:28 AM
 */
public class FileSaveHandler extends SimpleChannelInboundHandler<ByteBuf> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Active");
        ctx.fireChannelActive();

    }


    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
//        convertByteToFile(byteBuf);
        System.out.println(">>>>>" + byteBuf);
    }


    private void convertByteToFile(ByteBuf byteBuf) {
        int messageLength = byteBuf.readInt();
        int fileLength = byteBuf.readInt();
        int fileNameLength = messageLength - fileLength;

        //FileName
        byte[] fileNameByte = new byte[fileNameLength];
        byteBuf.readBytes(fileNameByte);
        String fileName = new String(fileNameByte, StandardCharsets.UTF_8);
        System.out.println();

        Path directory = Paths.get("/Users/wanli.zhou/Desktop/bbb");
        Path filePath = directory.resolve(fileName);
        try {
            Path file = Files.createFile(filePath);

            FileChannel fileChannel = FileChannel.open(file, StandardOpenOption.WRITE);
            ByteBuffer byteBuffer = byteBuf.nioBuffer();
            System.out.println(byteBuffer);
            fileChannel.write(byteBuffer);
            fileChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}