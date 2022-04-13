package ru.geekbrains.java;

import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NioClient {
    public static void main(String[] args) {
        new NioClient().start();
    }

    public void start(){
        try {
            Socket socket = new Socket("localhost", 9000);
            DataInputStream reader = new DataInputStream(socket.getInputStream());
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            Scanner in = new Scanner(System.in);
            new Thread(() -> {
                try {
                    while (true) {
                        byte c;
                        while ((c = reader.readByte()) != -1) {
                            System.out.print((char) c);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
            String s;
            while (true) {
                System.out.println("Введите сообщение:");
                s = in.next();
                writer.write(s);
                writer.flush();
                Thread.sleep(3000);
                writer.newLine();
                writer.flush();
                if (s.equals("0")) {
                    writer.close();
                    reader.close();
                    socket.close();
                    break;
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

    }

}
