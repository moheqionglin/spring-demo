package com.moheqionglin.zeroCopy;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * @author wanli.zhou
 * @description
 * @time 22/11/2018 11:52 PM
 */
public class CopyFileToSocketTest {

    private static final String FILE_PATH = "/Users/wanli.zhou/Downloads/dump.o2hprof.index";


    public static void traditionalCopyToSocket(ServerSocketChannel serverSocketChannel) throws IOException {
        SocketChannel socketChannel = serverSocketChannel.accept();
        socketChannel.configureBlocking(true);
        long start = System.currentTimeMillis();

        //Opend file
        FileInputStream fileInputStream = new FileInputStream(FILE_PATH);

        //copy file to jvm
        byte[] jvmBuffer = new byte[fileInputStream.available()];
        fileInputStream.read(jvmBuffer);

        //send to socket
        ByteBuffer byteBuffer = ByteBuffer.wrap(jvmBuffer);
        byteBuffer.flip();
        socketChannel.write(byteBuffer);

        fileInputStream.close();
        socketChannel.close();
        long end = System.currentTimeMillis() - start;
        System.out.println("traditionalCopyToSocket " + end);
    }

    public static void traditionalCopyByShardToSocket(ServerSocketChannel serverSocketChannel) throws IOException {
        SocketChannel socketChannel = serverSocketChannel.accept();
        socketChannel.configureBlocking(true);
        long start = System.currentTimeMillis();

        //Opend file
        FileInputStream fileInputStream = new FileInputStream(FILE_PATH);

        int available = fileInputStream.available();

        //copy file to jvm
        byte[] jvmBuffer = new byte[200000];
        for(int length = 0; (length = fileInputStream.read(jvmBuffer)) > 0;){
            //send to socket
            ByteBuffer byteBuffer = ByteBuffer.wrap(jvmBuffer);
            byteBuffer.flip();
            socketChannel.write(byteBuffer);
        }

        fileInputStream.close();
        socketChannel.close();
        long end = System.currentTimeMillis() - start;
        System.out.println("traditionalCopyByShardToSocket " + end);
    }

    public static void traditionalCopyByShardToSocketNIO(ServerSocketChannel serverSocketChannel) throws IOException {
        SocketChannel socketChannel = serverSocketChannel.accept();
        socketChannel.configureBlocking(true);
        long start = System.currentTimeMillis();

        FileChannel fileChannel = FileChannel.open(Paths.get(FILE_PATH), StandardOpenOption.READ);

        ByteBuffer byteBuffer = ByteBuffer.allocate(200000);
        for(int length = 0; (length = fileChannel.read(byteBuffer)) > 0;){
            byteBuffer.flip();
            socketChannel.write(byteBuffer);
        }

        fileChannel.close();
        socketChannel.close();
        long end = System.currentTimeMillis() - start;
        System.out.println("traditionalCopyByShardToSocketNIO " + end);
    }

    public static void directMappedCopyToSocket(ServerSocketChannel serverSocketChannel) throws IOException {
        SocketChannel socketChannel = serverSocketChannel.accept();
        socketChannel.configureBlocking(true);

        long start = System.currentTimeMillis();
        // Open
        FileChannel fileInChannel = FileChannel.open(Paths.get(FILE_PATH));
        ByteBuffer directByteBuffer = ByteBuffer.allocateDirect((int) fileInChannel.size());

        directByteBuffer.flip();
        socketChannel.write(directByteBuffer);

        fileInChannel.close();
        socketChannel.close();

        long end = System.currentTimeMillis() - start;
        System.out.println("directMappedCopyToSocket cost " + end);
    }

    public static void directMappedCopyByShardToSocket(ServerSocketChannel serverSocketChannel) throws IOException {
        SocketChannel socketChannel = serverSocketChannel.accept();
        socketChannel.configureBlocking(true);

        long start = System.currentTimeMillis();
        // Open
        FileChannel fileInChannel = FileChannel.open(Paths.get(FILE_PATH));
        int size = (int) fileInChannel.size();
        ByteBuffer directByteBuffer = ByteBuffer.allocateDirect(200000);
        for(int length = 0 ; (length = fileInChannel.read(directByteBuffer)) > 0;){
            directByteBuffer.flip();
            socketChannel.write(directByteBuffer);
        }

        fileInChannel.close();
        socketChannel.close();

        long end = System.currentTimeMillis() - start;
        System.out.println("directMappedCopyByShardToSocket cost " + end);
    }

    public static void mappedMemoryCopyToSocket(ServerSocketChannel serverSocketChannel) throws IOException {
        SocketChannel socketChannel = serverSocketChannel.accept();
        socketChannel.configureBlocking(true);

        long start = System.currentTimeMillis();

        //Open file
        FileChannel fileChannel = FileChannel.open(Paths.get(FILE_PATH));

        //mapped memory system call
        MappedByteBuffer mapedBuffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, 0, fileChannel.size());
        mapedBuffer.flip();
        //write to socket
        socketChannel.write(mapedBuffer);

        socketChannel.close();
        fileChannel.close();
        long end = System.currentTimeMillis() - start;
        System.out.println("mappedMemoryCopyToSocket " + end);

    }

    public static void transferToCopyToSocket(ServerSocketChannel serverSocketChannel) throws IOException {
        SocketChannel socketChannel = serverSocketChannel.accept();
        socketChannel.configureBlocking(true);

        long start = System.currentTimeMillis();

        //open file
        FileChannel fileChannel = FileChannel.open(Paths.get(FILE_PATH));

        fileChannel.transferTo(0, fileChannel.size(), socketChannel);

        long end = System.currentTimeMillis() - start;
        fileChannel.close();
        socketChannel.close();
        System.out.println("transferToCopyToSocket " + end);

    }

    public static void main(String[] args) throws IOException, InterruptedException {
        ServerSocketChannel nioSocketChannel = getNIOSocketChannel();

        for(int i = 0 ; i < 10 ; i ++)
        //566
//        traditionalCopyToSocket(nioSocketChannel);
         //10000 >215  200 , 200000 > 165, 116
//        traditionalCopyByShardToSocket(nioSocketChannel);
           //13 0
          traditionalCopyByShardToSocketNIO(nioSocketChannel);
            //300  171
//        directMappedCopyToSocket(nioSocketChannel);

            //16 0
//        directMappedCopyByShardToSocket(nioSocketChannel);

        //16  0
//        mappedMemoryCopyToSocket(nioSocketChannel) ;

        //16  10
//        transferToCopyToSocket(nioSocketChannel);

    }

    private static ServerSocketChannel getNIOSocketChannel() throws IOException {
        ServerSocketChannel serverSocket = ServerSocketChannel.open();
        serverSocket.bind(new InetSocketAddress(8798));
        serverSocket.configureBlocking(true);
        return serverSocket;
    }
}