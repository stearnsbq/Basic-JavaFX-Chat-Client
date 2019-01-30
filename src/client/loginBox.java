package client;

import database.Authenication;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class loginBox {

    @FXML
    Button login;
    @FXML
    Button register;
    @FXML
    TextField username;
    @FXML
    TextField password;

    private login controller;

    private Scene scene;

    public void login(ActionEvent event) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login.fxml"));
        Parent window = (Pane) fxmlLoader.load();
        controller = fxmlLoader.getController();
        this.scene = new Scene(window);
            Authenication auth = new Authenication(username.getText(), password.getText());
            if (auth.Auth()){
                System.out.println("Logging in.....");
                controller.connect(username.getText());
                register.getScene().getWindow().hide();
            }else{
                System.out.println("Username or Password is invalid");
            }


    }




    public void registerAccount(){
        Platform.runLater(()->{
            register.getScene().getWindow().hide();
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("Registration.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 550, 50);
                Stage stage = new Stage();
                stage.setTitle("Register");
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
