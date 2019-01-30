package client;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;

import server.message;

public class listener implements Runnable {

    private Socket socket;
    public String hostname;
    public int port;
    public Controller controller;
    public static String username;
    private static ObjectOutputStream oos;
    private InputStream is;
    private ObjectInputStream input;
    private OutputStream outputStream;


    public listener (String hostname, int port, String username, Controller controller){
        this.hostname = hostname;
        this.port = port;
        this.username = username;
        this.controller = controller;

    }


    @Override
    public void run() {
        try {
            socket = new Socket(hostname, port);
            login.getInstance().showScene();
            outputStream = socket.getOutputStream();
            oos = new ObjectOutputStream(outputStream);
            is = socket.getInputStream();
            input = new ObjectInputStream(is);
        }catch (Exception e){
            e.printStackTrace();

        }


        try {
            connect();
            System.out.println("Sockets in and out Intialized");
            while (socket.isConnected()){
                message message = null;
                message = (message) input.readObject();
                if (message != null) {
                    controller.addToUserList(message);
                    controller.addToChatBox(message);
                }
            }
        }catch (Exception e){
            controller.openErrorScreen(e.toString());
        }

    }


    public static void connect() throws IOException {
        message createMessage = new message();
        createMessage.setName(username);
        createMessage.setMsg("HAS CONNECTED!!!!");
        oos.writeObject(createMessage);
    }

    public static void send(String msg) throws IOException {
        message createMessage = new message();
        createMessage.setName(username);
        createMessage.setMsg(msg);
        oos.writeObject(createMessage);
        oos.flush();
    }





}