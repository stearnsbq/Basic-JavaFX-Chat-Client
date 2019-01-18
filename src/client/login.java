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
    TextField user;
    @FXML
    Button login;


    public static Controller con;

    public login(){
        instance = this;
    }


    public void connect(javafx.event.ActionEvent e) throws Exception{
        String host = ip.getText();
        int portNum = Integer.parseInt(port.getText());
        String username = user.getText();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("chatWindow.fxml"));
        Parent window = (Pane) fxmlLoader.load();
        con = fxmlLoader.getController();
        listener listener = new listener(host,portNum,username,con);
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
