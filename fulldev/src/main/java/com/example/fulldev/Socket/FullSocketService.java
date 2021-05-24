package com.example.fulldev.Socket;

import org.junit.Test;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class FullSocketService {
    private static final ThreadPoolExecutor pool = new ThreadPoolExecutor(4, 4, 10l, TimeUnit.DAYS, new LinkedBlockingQueue<>(1));

    @Test
    public void test() {
        start();

    }

    /**
     * 启动服务端
     */
    public static final void start() {
        try {
            ServerSocket serverSocket = new ServerSocket();
            serverSocket.setReuseAddress(true);

            serverSocket.bind(new InetSocketAddress("localhost", 7007));
            System.out.println("服务端启动成功");
            while (true) {
                Socket socket = serverSocket.accept();
                if (pool.getQueue().size() > 1) {
                    //返回处理结果给客户端
                    rejectRequest(socket);


                    continue;
                }

                try {
                    pool.submit(new SocketService(socket));
                } catch (Exception e) {
                    socket.close();
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void rejectRequest(Socket socket) throws IOException {
        OutputStream outputStream = null;
        try {
            outputStream = socket.getOutputStream();
            byte[] bytes = "服务器太忙了，请稍后重试~".getBytes(Charset.forName("UTF-8"));
            FullSocket.segmentWrite(bytes, outputStream);
            socket.shutdownOutput();
        } finally {
            //关闭流
            FullSocket.close(socket, outputStream, null, null, null);
        }
    }


}
