package client;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import server.user;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import server.message;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
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
    private List<ImageView> imgs = new ArrayList<>();
    private VBox chatBox = new VBox(5);
    private int mgsindex = 0;
    private  int imgindex = 0;



    @Override
    public void initialize(URL location, ResourceBundle resources){
        sPane.setContent(chatBox);
        sPane.vvalueProperty().bind(chatBox.heightProperty());

    }




    public void openConnect(ActionEvent e){
        Platform.runLater(() ->{

            sPane.getScene().getWindow().hide();
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("login.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 550, 50);
                Stage stage = new Stage();

                stage.setOnCloseRequest((javafx.stage.WindowEvent E) -> {
                    Platform.exit();
                    System.exit(0);
                });

                stage.setTitle("Login");
                stage.setResizable(false);
                stage.setScene(scene);
                stage.show();

            } catch (IOException event) {
                Logger logger = Logger.getLogger(getClass().getName());
                logger.log(Level.SEVERE, "Failed to create new Window.", e);
            }


        });



    }




    public void sendMessage(ActionEvent E) throws Exception{
        String msg = message.getText();
        if (!message.getText().isEmpty()){
            listener.send(msg);
            message.clear();
        }

    }

    public void addToUserList(message message){
        Platform.runLater(()-> {
            ObservableList<user> users = FXCollections.observableList(message.getUsers());
            userListView.setItems(users);
            userListView.setPrefHeight(sPane.getPrefHeight());
            userListView.setPrefWidth(sPane.getPrefWidth());
        });
    }






    public void addToChatBox(message msg){
        Platform.runLater(()->{
            String in = msg.getName() + ": " + msg.getMsg();
            Label label = new Label(in);
            String path = String.format("./resources/%s/profile.jpg",msg.getName());
            File tmp = new File(path);
            Image image;
            try {
                image = new Image(tmp.toURL().toString());
                if (image.isError()){
                    throw new Exception();
                }
                ImageView imageView = new ImageView(image);
                imageView.setFitHeight(25);
                imageView.setFitWidth(25);
                label.setGraphic(imageView);
                label.getGraphic();
                msgs.add(label);
                chatBox.getChildren().add(msgs.get(mgsindex));
                mgsindex++;
            }catch (Exception e){
                msgs.add(label);
                chatBox.getChildren().add(msgs.get(mgsindex));
                mgsindex++;
            }



        });

    }

    public void addImageToChatBox( message message){
        Platform.runLater(()->{
            String image = message.getImg();
            String in = message.getName() + ": ";
            Label label = new Label(in);
            if (image != null) {
                Image test = new Image(image);
                ImageView imageView = new ImageView(test);
                imageView.fitHeightProperty().bind(javafx.beans.binding.Bindings.when(imageView.hoverProperty()).then(250).otherwise(100));
                imageView.fitWidthProperty().bind(javafx.beans.binding.Bindings.when(imageView.hoverProperty()).then(250).otherwise(100));
                label.setGraphic(imageView);
                label.setContentDisplay(ContentDisplay.RIGHT);
                msgs.add(label);
                chatBox.getChildren().add(msgs.get(mgsindex));
                mgsindex++;
            }
        });

    }


    public void addImage(ActionEvent e) throws Exception{
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Images");
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image files", "*.jpg","*.png"));
        File file = chooser.showOpenDialog(sPane.getScene().getWindow());
        if (file != null) {
            String image = file.toURL().toString();
            listener.send(image, "");
        }
    }












}