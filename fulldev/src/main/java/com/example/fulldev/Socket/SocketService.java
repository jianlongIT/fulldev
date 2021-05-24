package com.example.fulldev.Socket;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.Charset;

public class SocketService implements Runnable {

    private Socket socket;

    public SocketService() {
    }

    public SocketService(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        OutputStream outputStream = null;
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;

        try {
            socket.setSoTimeout(10000);

            inputStream = socket.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            bufferedReader = new BufferedReader(inputStreamReader);
            StringBuffer stringBuffer = FullSocket.segmentRead(bufferedReader);


            //服务端处理 模拟服务端处理耗时
            Thread.sleep(2000);
            String response = stringBuffer.toString();


            outputStream = socket.getOutputStream();
            byte[] bytes = response.getBytes(Charset.forName("UTF-8"));
            FullSocket.segmentWrite(bytes, outputStream);
            socket.shutdownOutput();

            //关闭流
            FullSocket.close(socket, outputStream, inputStreamReader, bufferedReader, inputStream);


        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            try {
                //关闭流
                FullSocket.close(socket, outputStream, inputStreamReader, bufferedReader, inputStream);


            } catch (Exception e) {
                e.printStackTrace();

            }
        }

    }
}
