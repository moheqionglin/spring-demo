package com.moheqionglin.nioVsTrandionalIo.customProtocol;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;

/**
 * @author wanli.zhou
 * @description
 * @time 25/10/2018 10:20 AM
 */

public class Server {
    public static void main(String[] args) throws IOException {
        new Server().startServer();

    }


    public void startServer() throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(9988));
        serverSocketChannel.configureBlocking(true);
        System.out.println("Start Listener port : 9988");
        for(;;){
            SocketChannel socketChannel = serverSocketChannel.accept();
            new Thread(new FileProcessor(socketChannel)).start();
        }

    }

    /**
     * 协议 [头四个字节 文件名长度]+[后四个字节 文件长度]+[文件名]+[文件内容]
     *
     */
    class FileProcessor implements Runnable{
        SocketChannel socketChannel ;

        public FileProcessor(SocketChannel socketChannel) {
            this.socketChannel = socketChannel;
        }

        @Override
        public void run() {
            try {
                Files.newDirectoryStream(Paths.get(getFilePath())).forEach(path -> {
                    String fileName = path.toFile().getName();
                    try {
                        FileChannel fileChannel = FileChannel.open(path, StandardOpenOption.READ);
                        ByteBuffer socketBuffer = ByteBuffer.allocate(100);


                        int fileSize = (int) fileChannel.size();
                        byte[] fileNameBytes = fileName.getBytes(StandardCharsets.UTF_8);
                        int fileNameLength = fileNameBytes.length;

                        socketBuffer.putInt(fileSize);
                        socketBuffer.putInt(fileNameLength);

                        System.out.println(">>>>开始发送 " + fileName + " 大小 " + fileSize);
                        //发送文件名
                        for(int index = 0; index < fileNameLength; index++){
                            if(socketBuffer.remaining() > 0){
                                socketBuffer.put(fileNameBytes[index]);
                            }else{
                                socketBuffer.flip();
                                socketChannel.write(socketBuffer);
                                socketBuffer.clear();
                                socketBuffer.put(fileNameBytes[index]);
                            }
                        }
                        //发送文件正文
                        for( ;fileChannel.read(socketBuffer) > 0; ){
                            socketBuffer.flip();
                            if(socketBuffer.hasRemaining()){
                                socketChannel.write(socketBuffer);
                            }
                            socketBuffer.clear();
                        }
                        System.out.println("||>>结束发送 " + fileName + " 大小 " + fileSize + "-> " + socketBuffer.remaining());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                socketChannel.shutdownOutput();

            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }

    private String getFilePath(){
        return this.getClass().getClassLoader().getResource("./images").getPath();
    }
}