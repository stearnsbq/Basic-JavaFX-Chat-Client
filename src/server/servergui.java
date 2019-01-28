package server;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

import java.awt.*;
import java.sql.*;
import java.net.URL;
import java.util.ResourceBundle;

public class servergui implements Initializable {

    @FXML
    javafx.scene.control.TextField port;
    @FXML
    javafx.scene.control.TextField maxUsers;
    @FXML
    javafx.scene.control.Button startServer;
    @FXML
    Label serverStatus;


    @Override
    public void initialize(URL location, ResourceBundle resources) {




    }


    public void startServer(ActionEvent e){
        serverStatus.setText("Server is Currently Running");
        serverStatus.setTextFill(Color.GREEN);
        new Thread(()->{
            server server = new server(Integer.parseInt(port.getText()),Integer.parseInt(maxUsers.getText()));
            try {
                server.startServer();
            }catch (Exception E){
            E.printStackTrace();
            }
        }).start();

    }







}
