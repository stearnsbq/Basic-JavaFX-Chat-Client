package server.database;

import de.mkammerer.argon2.Argon2;
import javafx.scene.control.Alert;
import de.mkammerer.argon2.Argon2Factory;
import java.sql.ResultSet;
import java.sql.Statement;

public class Authenication {


    private String cUser;
    private char[] cPWord;
    private Database base;

    public Authenication(String cUser, String cPWord){
        try {
            base = new Database();
            this.cPWord = cPWord.toCharArray();
            this.cUser = cUser;
        }catch (Exception E){
            //Alert box = new Alert(Alert.AlertType.ERROR, E.toString());
            //box.showAndWait();
        }

    }




    public boolean Auth(){
        Argon2 argon2 = Argon2Factory.create();
        Statement statement = base.getStatement();
        try {
            String cmd = String.format("SELECT * FROM logininfo WHERE usernames='%s'",cUser);
            ResultSet resultSet = statement.executeQuery(cmd);
            resultSet.first();
            String hash = resultSet.getString("passwords");
            return argon2.verify(hash, cPWord);
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Database Connection Error");
            e.printStackTrace();

        }
        return false;

    }







}
