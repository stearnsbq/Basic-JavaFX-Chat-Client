package server;

import client.Controller;

import server.database.Authenication;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class server {
    private static HashSet<ObjectOutputStream> writers = new HashSet<>();
    private static ArrayList<user> userLists = new ArrayList<>();
    private static HashMap<String,user> names = new HashMap<>();
    private  int maxUsers;
    private int port;


    public server(int port, int maxUsers){
        this.maxUsers = maxUsers;
        this.port = port;
    }


    public void startServer() throws Exception{
        System.out.println("Chat Server is running.....");
        ServerSocket listener = new ServerSocket(port);
        try {
            while (true) {
                new Handler(listener.accept(),maxUsers).start();
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
        private int maxUsers;

        public Handler(Socket socket, int maxUsers) throws IOException {
            this.socket = socket;
            this.maxUsers = maxUsers;
        }
        @Override
        public void run(){
            System.out.println("ATTEMPTING TO CONNECT A USER.....");
            try {
                is = socket.getInputStream();
                inputStream = new ObjectInputStream(is);
                os = socket.getOutputStream();
                outputStream = new ObjectOutputStream(os);

                message first =(message) inputStream.readObject();
                System.out.println(first.getName());
                checkForExistingUser(first);
                checkServerCapacity();
                writers.add(outputStream);
                login();


                while (socket.isConnected()){
                    message in = (message) inputStream.readObject();
                    if (in != null){
                        System.out.println(in.getName() + "-" + in.getMsg());
                        write(in);
                    }
                }

            }catch (Exception e){
                System.out.println(e.toString());

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

        //private Authenication authenication(){
          //  Authenication authenication = new Authenication();

       // }

        private message leaving() throws IOException{
            message msg = new message();
            if (!name.equals("null")) {
                msg.setMsg(name + " has left the server");
                msg.setName("SERVER");
                msg.setUsers(userLists);
                write(msg);
            }
            return msg;
        }



        private synchronized void checkServerCapacity() throws Exception{
            if (userLists.size() > maxUsers){
                throw new ServerIsFullException("The server is full");
            }
        }


        private synchronized void checkForExistingUser(message firstMessage) throws UserExistsException{
            System.out.println(firstMessage.getName() + " Is trying to connect...");
            if (!names.containsKey(firstMessage.getName())){
                this.name = firstMessage.getName();
                user = new user();
                user.setName(firstMessage.getName());
                userLists.add(user);
                names.put(name,user);

                System.out.println(name + " Has been added to userlist");

            }else{
                throw new UserExistsException(firstMessage.getName() + " is already connected");
            }


        }


        private void write(message msg) throws IOException {
            for (ObjectOutputStream writer : writers) {
                msg.setUsers(userLists);
                msg.setList(names);
                writer.writeObject(msg);
                writer.reset();
            }
        }



        private synchronized void closeConnections(){
            System.out.println("Closing Connection to server");
            if (name != null){
                names.remove(name);
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
                    e.printStackTrace();

                }

            }
            if (os != null){
                try {
                    os.close();
                }catch (Exception e){
                    e.printStackTrace();

                }

            }
            if (is != null){
                try {
                    is.close();
                }catch (Exception e){
                    e.printStackTrace();

                }

            }
            try {
                leaving();
            }catch (Exception e){
                e.printStackTrace();

            }


        }


    }










}