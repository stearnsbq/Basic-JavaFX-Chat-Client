package client;

import java.io.*;
import java.net.Socket;

import javafx.scene.control.Alert;
import server.message;

public class listener implements Runnable {

    private Socket socket;
    private String hostname;
    private int port;
    private Controller controller;
    private static String username;
    private static ObjectOutputStream oos;
    private InputStream is;
    private ObjectInputStream input;
    private OutputStream outputStream;
    private boolean isAuth;


    public listener (String hostname, int port, String username, Controller controller){
        this.hostname = hostname;
        this.port = port;
        this.username = username;
        this.controller = controller;
        isAuth = false;
    }


    @Override
    public void run() {
        try {
            socket = new Socket(hostname, port);
            loginBox.getInstance().showScene();
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
                    if (message.getMsg().isEmpty()){
                        controller.addImageToChatBox(message);
                    }else {
                        controller.addToUserList(message);
                        controller.addToChatBox(message);
                    }

                }
            }
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.showAndWait();
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
        createMessage.setImg(null);
        oos.writeObject(createMessage);
        oos.flush();
    }

    public static void send(String img, String msg) throws IOException {
        message createMessage = new message();
        createMessage.setName(username);
        createMessage.setImg(img);
        createMessage.setMsg(msg);
        oos.writeObject(createMessage);
        oos.flush();
    }





}