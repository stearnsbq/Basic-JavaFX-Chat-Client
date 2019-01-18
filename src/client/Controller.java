package client;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import server.user;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import server.message;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controller implements Initializable {
    @FXML
    MenuItem connect;
    @FXML
    ScrollPane sPane;
    @FXML
    ListView<user> userListView;
    @FXML
    javafx.scene.control.TextField message;

    private List<Label> msgs = new ArrayList<>();
    private VBox chatBox = new VBox(5);
    private int index = 0;



    @Override
    public void initialize(URL location, ResourceBundle resources){
        sPane.setContent(chatBox);
    }




    public void openConnect(ActionEvent e){
        sPane.getScene().getWindow().hide();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("login.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 550, 50);
            Stage stage = new Stage();
            stage.setTitle("Login");
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();

        } catch (IOException event) {
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Failed to create new Window.", e);
        }


    }






    public void sendMessage(ActionEvent E) throws Exception{
        String msg = message.getText();
        if (!message.getText().isEmpty()){
            System.out.println("INSTANCE DEBUG: " + this);
            listener.send(msg);
            message.clear();
        }

    }

    public void addToUserList(message message){
        Platform.runLater(()-> {
            ObservableList<user> users = FXCollections.observableList(message.getUsers());
            userListView.setItems(users);
            

        });
    }


    public void addToChatBox(message msg){
        Platform.runLater(()->{
            String in = msg.getName() + ": " + msg.getMsg();
            msgs.add(new Label(in));
            System.out.println("DEBUG: " + in);
            if(index%2==0){
                msgs.get(index).setAlignment(Pos.CENTER_LEFT);
                System.out.println("1");
            }else{
                msgs.get(index).setAlignment(Pos.CENTER_RIGHT);
                System.out.println("2");
            }
            chatBox.getChildren().add(msgs.get(index));
            index++;



        });

    }












}
