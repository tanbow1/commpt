package com.tb.test;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 */
public class TestPcClientAdb2 {

    public static void main(String[] args) throws IOException {
        try {
            Runtime.getRuntime().exec("adb forward tcp:8000 tcp:9000"); // 端口转换
        } catch (Exception e) {
            e.printStackTrace();

        }
        System.out.println("回车键发送至手机端===>");
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String msg = scanner.next();
            sendMsg(msg);
        }
    }

    public static void sendMsg(String msg) throws IOException {
        Socket socket = new Socket("127.0.0.1", 8000);
//        DataInputStream dis = new DataInputStream(socket.getInputStream());
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        dos.writeUTF(msg);
        socket.close();
        System.out.println("已发送===>");
    }
}
