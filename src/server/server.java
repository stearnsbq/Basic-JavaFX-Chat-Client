package server;

import client.Controller;

import java.awt.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;

public class server {
 private static final int PORT = 666;
 private static HashSet<ObjectOutputStream> writers = new HashSet<>();

public static void main(String[] args) throws Exception{
    System.out.println("Chat Server is running.....");
        ServerSocket listener = new ServerSocket(PORT);
        try {
            while (true) {
                new Handler(listener.accept()).start();
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            listener.close();
        }

}




    private static class Handler extends Thread{
        private Socket socket;
        private ObjectOutputStream outputStream;
        private ObjectInputStream inputStream;
        private OutputStream os;
        private InputStream is;
        private message msg;

        public Handler(Socket socket) throws IOException {
            this.socket = socket;
        }

        public void run(){
            System.out.println("ATTEMPTING TO CONNECT A USER.....");
            try {
             is = socket.getInputStream();
             inputStream = new ObjectInputStream(is);
             os = socket.getOutputStream();
             outputStream = new ObjectOutputStream(os);
             writers.add(outputStream);

             while (socket.isConnected()){
                 message in = (message) inputStream.readObject();
                 if (in != null){
                     System.out.println(in.getName() + "-" + in.getMsg());
                     write(in);
                 }
             }

            }catch (Exception e){

            }
        }


        private void write(message msg) throws IOException {
            for (ObjectOutputStream writer : writers) {
                writer.writeObject(msg);
                writer.reset();
            }
        }


    }










}
