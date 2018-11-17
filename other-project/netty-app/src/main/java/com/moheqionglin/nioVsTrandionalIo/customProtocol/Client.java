package com.moheqionglin.nioVsTrandionalIo.customProtocol;

import com.sun.javafx.image.ByteToBytePixelConverter;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.ByteChannel;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * @author wanli.zhou
 * @description
 * @time 25/10/2018 10:54 AM
 */
public class Client {
    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress(9988));
        ByteBuffer socketBuffer = ByteBuffer.allocate(102);
        socketChannel.configureBlocking(true);

        //协议的Header 部分
        ByteBuffer fileLengthBytes = ByteBuffer.allocate(4);
        ByteBuffer fileNameLengthBytes = ByteBuffer.allocate(4);
        //协议的body内容
        ByteBuffer fileNameBytes = null;
        ByteBuffer fileBytes = null;

        for( ;socketChannel.read(socketBuffer) > 0; ){
            socketBuffer.flip();

            for(;socketBuffer.hasRemaining();){

                //读取前4个字节，直到读取完成。生成文件长度
                if(fileLengthBytes.remaining() > 0){
                    fileLengthBytes.put(socketBuffer.get());
                    if(!fileLengthBytes.hasRemaining()){
                        fileLengthBytes.flip();
                        fileBytes = ByteBuffer.allocate(fileLengthBytes.getInt());
                    }

                }else if (fileNameLengthBytes.remaining() > 0 ){
                    //读取后面4个字节，直到读取完成，生成文件名长度
                    fileNameLengthBytes.put(socketBuffer.get());

                    if(!fileNameLengthBytes.hasRemaining()){
                        fileNameLengthBytes.flip();
                        fileNameBytes = ByteBuffer.allocate(fileNameLengthBytes.getInt());
                    }
                }else if(fileNameBytes.remaining() > 0){
                    //读取文件名，直到读取完成。
                    fileNameBytes.put(socketBuffer.get());
                }else if(fileBytes.remaining() > 0) {
                    fileBytes.put(socketBuffer.get());
                }else{
                    createFile(socketChannel, fileNameBytes, fileBytes);

                    fileLengthBytes.clear();
                    fileNameLengthBytes.clear();
                    fileNameBytes.clear();
                    fileBytes.clear();

                }

            }
            socketBuffer.clear();
        }

        //最后一个文件，特殊处理，如果所有buffer都装满了，没有clear证明还有最后一个文件没有创建。
        if(!fileLengthBytes.hasRemaining() && !fileNameLengthBytes.hasRemaining() && !fileNameBytes.hasRemaining() && !fileBytes.hasRemaining()){
            createFile(socketChannel, fileNameBytes, fileBytes);
        }

        socketChannel.close();

    }

    private static void createFile(SocketChannel socketChannel, ByteBuffer fileNameBytes, ByteBuffer fileBytes) throws IOException {
        fileNameBytes.flip();
        fileBytes.flip();
        String fileName = StandardCharsets.UTF_8.decode(fileNameBytes).toString();
        Path path = Paths.get("/Users/wanli.zhou/Desktop/aaaaa/").resolve(fileName);
        Path file = Files.createFile(path);
        System.out.println("->" + fileName);
        FileChannel fileChannel = FileChannel.open(file, StandardOpenOption.WRITE);
        fileChannel.write(fileBytes);
        fileChannel.close();
    }

}
