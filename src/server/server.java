package server;

import client.Controller;

import java.awt.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class server {
 private static final int PORT = 666;
 private static HashSet<ObjectOutputStream> writers = new HashSet<>();
 private static ArrayList<user> userLists = new ArrayList<>();
 private static HashMap<String,user> names = new HashMap<>();

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




    private static class Handler extends Thread {
        private Socket socket;
        private ObjectOutputStream outputStream;
        private ObjectInputStream inputStream;
        private OutputStream os;
        private InputStream is;
        private message msg;
        private String name;
        private user user;

        public Handler(Socket socket) throws IOException {
            this.socket = socket;
        }
        @Override
        public void run(){
            System.out.println("ATTEMPTING TO CONNECT A USER.....");
            try {
             is = socket.getInputStream();
             inputStream = new ObjectInputStream(is);
             os = socket.getOutputStream();
             outputStream = new ObjectOutputStream(os);
             writers.add(outputStream);

             message first =(message) inputStream.readObject();
             checkForExistingUser(first);
             login();


             while (socket.isConnected()){
                 message in = (message) inputStream.readObject();
                 if (in != null){
                     System.out.println(in.getName() + "-" + in.getMsg());
                     write(in);
                 }
             }

            }catch (Exception e){

            }finally {
                closeConnections();
            }
        }



        private message login() throws IOException{
            message msg = new message();
            msg.setMsg(String.format("Welcome to the server %s, Have fun!" , name));
            msg.setName("SERVER");
            write(msg);
            return msg;

        }

        private message leaving() throws IOException{
            message msg = new message();
            msg.setMsg(name + " has left the server");
            msg.setName("SERVER");
            msg.setUsers(userLists);
            write(msg);
            return msg;
        }


        private synchronized void checkForExistingUser(message firstMessage) throws IllegalAccessException{
            System.out.println(firstMessage.getName() + " Is trying to connect...");
            if (!names.containsKey(firstMessage.getName())){
                this.name = firstMessage.getName();
                user = new user();
                user.setName(firstMessage.getName());
                userLists.add(user);
                names.put(name,user);

                System.out.println(name + " Has been added to userlist");


            }else{
                throw new IllegalAccessException("User already connected");
            }


        }


        private void write(message msg) throws IOException {
            for (ObjectOutputStream writer : writers) {
                writer.writeObject(msg);
                writer.reset();
            }
        }



        private synchronized void closeConnections(){
            System.out.println("Closing Connection to server");
            if (name != null){
                names.remove(names);
            }
            if (user !=null){
                userLists.remove(user);
            }
            if (outputStream != null){
                writers.remove(outputStream);
            }
            if (inputStream != null){
                try {
                    inputStream.close();
                }catch (Exception e){

                }

            }
            if (os != null){
                try {
                    os.close();
                }catch (Exception e){

                }

            }
            if (is != null){
                try {
                    is.close();
                }catch (Exception e){

                }

            }
            try {
                leaving();
            }catch (Exception e){

            }


        }


    }










}
