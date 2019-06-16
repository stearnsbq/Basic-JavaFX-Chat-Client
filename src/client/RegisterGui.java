package client;

import client.login;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import server.Registration.Register;

import java.net.URL;
import java.util.ResourceBundle;

public class RegisterGui implements Initializable {

    @FXML
    Button register;
    @FXML
    TextField username;
    @FXML
    PasswordField password;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


    public void registerAcc(javafx.event.ActionEvent e){
        Register register = new Register();
        try {
            register.registerUser(username.getText(), password.getText());
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "User Registered");
            alert.showAndWait();
            try {
                login.getInstance().loginArea();
                password.getScene().getWindow().hide();
            }catch (Exception E){
                E.printStackTrace();
            }
        }catch ( Exception E){
            Alert box = new Alert(Alert.AlertType.ERROR, "User already Exists");
            box.showAndWait();
        }


    }







}
