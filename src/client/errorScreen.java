package client;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class errorScreen implements Initializable {

    private String errMsg;

    @FXML
    Label err;

     public errorScreen(String errMsg){
        this.errMsg = errMsg;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        err.setText(this.errMsg);
    }





}
