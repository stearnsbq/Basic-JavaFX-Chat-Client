package client;

import server.database.Authenication;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class loginBox {

    private static loginBox instance;
    @FXML
    Button login;
    @FXML
    Button register;
    @FXML
    TextField username;
    @FXML
    PasswordField password;
    private Scene scene;

    public loginBox() {
        instance = this;
    }

    public static loginBox getInstance() {
        return instance;
    }

    @FXML
    public void login(ActionEvent event) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login.fxml"));
        fxmlLoader.load();
        login controller = fxmlLoader.getController();
        Authenication auth = new Authenication(username.getText(), password.getText());
        if (auth.Auth()) {
            System.out.println("Logging in.....");
            FXMLLoader Loader = new FXMLLoader(getClass().getResource("chatWindow.fxml"));
            Parent window = (Pane) Loader.load();
            Controller c = Loader.getController();
            controller.connect(username.getText(),c);
            this.scene = new Scene(window);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Username or Password is invalid");
            alert.showAndWait();
        }
      //  register.getScene().getWindow().hide();
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

    public void registerAccount(ActionEvent e) {
        Platform.runLater(() -> {
            register.getScene().getWindow().hide();
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("./register.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 550, 550);
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
