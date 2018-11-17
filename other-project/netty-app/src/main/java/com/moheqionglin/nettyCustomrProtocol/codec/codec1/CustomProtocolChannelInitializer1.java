package com.moheqionglin.nettyCustomrProtocol.codec.codec1;

import com.moheqionglin.nettyCustomrProtocol.FileSaveHandler;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * @author wanli.zhou
 * @description
 * @time 31/10/2018 5:46 PM
 */
public class CustomProtocolChannelInitializer1 extends ChannelInitializer<SocketChannel> {
    private final int ONE_M = 1024 * 1024 * 1024;

    /**
     * @param channel
     * @throws Exception fileNameLength + fileLength | FileNameLength | FileName | FileContent
     */
    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        channel.pipeline()
                .addLast(new LengthFieldBasedFrameDecoder(ONE_M, 0, 4, -4, 0))
                .addLast(new SimpleChannelInboundHandler<ByteBuf>() {

                    @Override
                    public void channelActive(ChannelHandlerContext ctx) throws Exception {
                        System.out.println("Active");
                        ctx.fireChannelActive();

                    }

                    @Override
                    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
                        convertByteToFile(byteBuf);
                        System.out.println(">>>>>" + byteBuf);
                    }


                    private void convertByteToFile(ByteBuf byteBuf) {
                        int messageLength = byteBuf.readInt() - 8;
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

                })
        ;


    }
}