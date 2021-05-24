package com.example.fulldev.Socket;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.springframework.util.StringUtils;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.nio.charset.Charset;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class FullSocket {

    public static void main(String[] args) throws Exception {
        File file = new File("d:/socket.text");
        InputStream inputStream = new FileInputStream(file);

        OutputStream outputStream = new FileOutputStream("d:/socket.sql");

        StringBuffer stringBuffer = new StringBuffer();

        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
        int sheetNum = workbook.getNumberOfSheets();
        for (int i = 0; i < sheetNum; i++) {
            XSSFSheet xssfSheet = workbook.getSheetAt(i);
            String sheetName = xssfSheet.getSheetName();
            int rowNum = xssfSheet.getLastRowNum() + 1;
            for (int j = 0; j < rowNum; j++) {
                XSSFRow row = xssfSheet.getRow(j);
                XSSFCell firstCell = row.getCell(0);
                XSSFCell lastCell = row.getCell(3);
                if (null != firstCell && null != lastCell) {
                    String firstName = firstCell.getStringCellValue();
                    String lastName = lastCell.getStringCellValue();
                    if (!StringUtils.isEmpty(firstName) && !StringUtils.isEmpty(lastName)) {
                        stringBuffer.append(sheetName + firstName + lastName);

                    }
                }
            }
        }

        try {
            byte[] bytes = stringBuffer.toString().getBytes();
            int len = bytes.length;
            outputStream.write(bytes, 0, len);
        } catch (Exception e) {

            e.printStackTrace();
        } finally {
            inputStream.close();
            outputStream.close();
        }


    }

    private static final Integer SIZE = 1024;
    private static final ThreadPoolExecutor pool = new ThreadPoolExecutor(50, 50, 10l, TimeUnit.DAYS, new LinkedBlockingQueue(400));

    @Test
    public void test() throws InterruptedException {
        for (int i = 0; i < 6; i++) {
            pool.submit(() -> {
                send("localhost", 7007, "jianlong");

            });

        }

        Thread.sleep(10000000);

    }

    public static String send(String domainName, int port, String content) {

        Socket socket = null;
        OutputStream outputStream = null;
        InputStream inputStream = null;
        BufferedReader bufferedReader = null;
        InputStreamReader inputStreamReader = null;
        StringBuffer response = null;
        try {
            if (StringUtils.isEmpty(domainName)) {
                return null;
            }
            socket = new Socket();
            socket.setReuseAddress(true);

            socket.connect(new InetSocketAddress(domainName, port), 10000);

            outputStream = socket.getOutputStream();
            byte[] bytes = content.getBytes(Charset.forName("UTF-8"));

            segmentWrite(bytes, outputStream);
            socket.shutdownOutput();

            socket.setSoTimeout(50000);

            inputStream = socket.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream);
            bufferedReader = new BufferedReader(inputStreamReader);

            response = segmentRead(bufferedReader);

            socket.shutdownInput();

            close(socket, outputStream, inputStreamReader, bufferedReader, inputStream);

            return response.toString();

        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                close(socket, outputStream, inputStreamReader, bufferedReader, inputStream);
            } catch (IOException e) {
                //e.printStackTrace();
            }

        }
        return null;
    }


    public static StringBuffer segmentRead(BufferedReader br) throws IOException {
        StringBuffer sb = new StringBuffer();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        return sb;
    }


    /**
     * 分段写
     *
     * @param bytes
     * @param outputStream
     * @throws IOException
     */
    public static void segmentWrite(byte[] bytes, OutputStream outputStream) throws IOException {
        int length = bytes.length;
        int start, end = 0;
        for (int i = 0; end != bytes.length; i++) {
            start = i == 0 ? 0 : i * SIZE;
            end = length > SIZE ? start + SIZE : bytes.length;
            length -= SIZE;
            outputStream.write(bytes, start, end - start);
            outputStream.flush();
        }
    }

    /**
     * 关闭各种流
     *
     * @param socket
     * @param outputStream
     * @param isr
     * @param br
     * @param is
     * @throws IOException
     */
    public static void close(Socket socket, OutputStream outputStream, InputStreamReader isr,
                             BufferedReader br, InputStream is) throws IOException {
        if (null != socket && !socket.isClosed()) {
            try {
                socket.shutdownOutput();
            } catch (Exception e) {
            }
            try {
                socket.shutdownInput();
            } catch (Exception e) {
            }
            try {
                socket.close();
            } catch (Exception e) {
            }
        }
        if (null != outputStream) {
            outputStream.close();
        }
        if (null != br) {
            br.close();
        }
        if (null != isr) {
            isr.close();
        }
        if (null != is) {
            is.close();
        }
    }

}
