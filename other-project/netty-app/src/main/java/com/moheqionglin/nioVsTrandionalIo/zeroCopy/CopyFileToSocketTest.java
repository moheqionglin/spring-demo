package com.moheqionglin.nioVsTrandionalIo.zeroCopy;

import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;

/**
 * @author wanli.zhou
 * @description
 * @time 22/11/2018 11:52 PM
 */
public class CopyFileToSocketTest {

    private static final String FILE_PATH = "/Users/wanli.zhou/Downloads/dump.o2hprof.index";


    public static void traditionalCopyToSocket(Socket socket) throws IOException {
        long start = System.currentTimeMillis();

        //Opend file
        FileInputStream fileInputStream = new FileInputStream(FILE_PATH);

        //copy file to jvm
        byte[] jvmBuffer = new byte[fileInputStream.available()];
        fileInputStream.read(jvmBuffer);

        //send to socket
        socket.getOutputStream().close();
        fileInputStream.close();

        long end = System.currentTimeMillis() - start;
        System.out.println("traditionalCopyToSocket " + end);
    }

    public static void mappedMemoryCopyToSocket(SocketChannel socketChannel) throws IOException {

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

    public static void transferToCopyToSocket(SocketChannel socketChannel) throws IOException {

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

        //566
//        traditionalCopyToSocket(getSocket());
        //16
        mappedMemoryCopyToSocket(getNIOSocketChannel()) ;
        //16
//        transferToCopyToSocket(getNIOSocketChannel());

    }



    private static Socket getSocket() throws IOException {
        ServerSocket serverSocket = new ServerSocket(8798);
        return serverSocket.accept();
    }

    private static SocketChannel getNIOSocketChannel() throws IOException {
        ServerSocketChannel serverSocket = ServerSocketChannel.open();
        serverSocket.bind(new InetSocketAddress(8798));
        serverSocket.configureBlocking(true);
        SocketChannel accept = serverSocket.accept();
//        accept.configureBlocking(true);
        return accept;
    }
}