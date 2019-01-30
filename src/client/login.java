package client;

import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class login {

    private static login instance;
    private Scene scene;

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


    private int portNumber;
    private String host;



    public void loginToServer(javafx.event.ActionEvent e){
        this.portNumber = Integer.parseInt(port.getText());
        this.host = ip.getText();
        Platform.runLater(()->{
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


    public void saveData(){

    }


    public void connect(String username) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("chatWindow.fxml"));
        Parent window = (Pane) fxmlLoader.load();
        con = fxmlLoader.getController();
        listener listener = new listener(host,portNumber,username,con);
        Thread x = new Thread(listener);
        x.start();
        this.scene = new Scene(window);
        //login.getScene().getWindow().hide();
    }



    public void showScene() throws IOException {
        Platform.runLater(() -> {
            Stage stage = (Stage) login.getScene().getWindow();
            stage.setResizable(false);
            stage.setTitle("Chat");
            stage.setWidth(1040);
            stage.setHeight(620);

            stage.setOnCloseRequest((javafx.stage.WindowEvent e) -> {
                Platform.exit();
                System.exit(0);
            });

            stage.setScene(this.scene);
            stage.setMinWidth(800);
            stage.setMinHeight(300);

            stage.centerOnScreen();

        });

    }

    public static client.login getInstance() {
        return instance;
    }
}
