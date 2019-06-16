package client;

import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;
import sun.rmi.runtime.Log;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class login {

    private static login instance;
    private  Scene scene;

    private static Stage stage;

    public void setStage(Stage stage) {
        client.login.stage = stage;
    }

    public static Stage getStage() {
        return stage;
    }

    @FXML
    TextField ip;
    @FXML
    TextField port;
    @FXML
    Button login;



    public static Controller con;

    public login(){
        instance = this;
    }


    private static int portNumber;
    private static String host;



    public void loginToServer(javafx.event.ActionEvent e) throws Exception{
        this.portNumber = Integer.parseInt(port.getText());
        this.host = ip.getText();
        loginArea();
    }


    public void loginArea(){
        if (checkServer(host,portNumber)) {
            Platform.runLater(() -> {
                ip.getScene().getWindow().hide();
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("loginBox.fxml"));
                    Scene scene = new Scene(fxmlLoader.load(), 800, 600);
                    Stage stage = new Stage();
                    stage.setTitle("Login");
                    stage.setResizable(false);
                    stage.setScene(scene);
                    stage.show();

                } catch (IOException event) {
                    Logger logger = Logger.getLogger(getClass().getName());
                    logger.log(Level.SEVERE, "Failed to create new Window.", event);
                }


            });
        }


    }


    public boolean checkServer(String ip, int port){
        try {
            Socket s = new Socket(ip, port);
            return true;
        }catch (Exception e){
            Alert s = new Alert(Alert.AlertType.ERROR, String.format("Failed to connect to server %s:%d",ip,port));
            s.showAndWait();
        }

        return false;
    }

    public int getPortNumber() {
        return portNumber;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPortNumber(int portNumber) {
        this.portNumber = portNumber;
    }

    public void connect(String username, Controller con) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("chatWindow.fxml"));
        Parent window = (Pane) fxmlLoader.load();
        listener listener = new listener(host,portNumber,username,con);
        Thread x = new Thread(listener);
        x.start();
        this.scene = new Scene(window);

    }









    public static client.login getInstance() {
        return instance;
    }
}
