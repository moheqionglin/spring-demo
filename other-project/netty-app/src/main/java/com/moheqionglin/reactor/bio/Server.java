package com.moheqionglin.reactor.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.ServerSocketChannel;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-05-29 09:53
 */
public class Server implements Runnable{

    public static void main(String[] args) {
        new Thread(new Server()).start();
    }


    @Override
    public void run() {
        try {
            ServerSocket ss = new ServerSocket(9908);
            while (!Thread.interrupted()){
                Socket accept = ss.accept();
                new Thread(new Handler(accept)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class Handler implements Runnable {
        private Socket socket;
        public Handler(Socket accept) {
            this.socket = accept;
        }

        @Override
        public void run() {
            try {
                InputStream inputStream = socket.getInputStream();
                byte b[] = new byte[100];
                inputStream.read(b);
                String s = process(b);
                System.out.println(s);
                socket.getOutputStream().write(("=>" + s).getBytes());
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private String process(byte[] b) {
            return new String(b);
        }
    }
}