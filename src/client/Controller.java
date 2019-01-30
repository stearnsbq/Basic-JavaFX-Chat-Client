package client;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import server.user;
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

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controller implements Initializable {
    @FXML
    MenuItem connect;
    @FXML
    ListView<user> listView;
    @FXML
    ScrollPane userPane;
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
        sPane.vvalueProperty().bind(chatBox.heightProperty());

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


    public void openErrorScreen(String error){
        Platform.runLater(()->{
            if (sPane.getScene() != null) {
                sPane.getScene().getWindow().hide();
            }
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("error.fxml"));
                errorScreen errController = new errorScreen(error);
                fxmlLoader.setController(errController);
                Scene scene = new Scene(fxmlLoader.load(), 550, 50);
                Stage stage = new Stage();
                stage.setTitle("ERROR");
                stage.setResizable(false);
                stage.setScene(scene);
                stage.show();

            } catch (IOException event) {
                Logger logger = Logger.getLogger(getClass().getName());
                logger.log(Level.SEVERE, "Failed to create new Window.", event);
            }


        });


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
            chatBox.getChildren().add(msgs.get(index));
            index++;



        });

    }












}