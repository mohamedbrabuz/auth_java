package org.example;

import java.io.IOException;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server  {
    public static void main(String[] args) {

        ArrayList<Auth> users= new ArrayList<>();
        users.add(new Auth("ali","123456"));
        users.add(new Auth("mohamed","123456"));
        users.add(new Auth("fghjkl","123456"));
        users.add(new Auth("ssofasa","123456"));
        users.add(new Auth("halal","123456"));

        int port = 19000;

        try {
            ServerSocket server = new ServerSocket(8000);
            System.out.println("**************  server on  **************");
            while(true){
                Socket socket = server.accept();
                System.out.println("**************  Client connected successfully  **************");
                myThread thread = new myThread(socket,users);
                thread.start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}