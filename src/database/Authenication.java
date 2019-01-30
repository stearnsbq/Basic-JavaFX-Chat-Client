package database;

import Registration.Crypt;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Base64;

public class Authenication {


    private SecretKey key;
    private String cUser;
    private String cPWord;
    private Database base;

    public Authenication(String cUser, String cPWord) throws Exception{
        base = new Database();
        Statement statement = base.getStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM encryption");
        resultSet.first();
        byte[] decodedKey = Base64.getDecoder().decode(resultSet.getString("Key"));
        this.key =  new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");;
        this.cPWord = cPWord;
        this.cUser = cUser;
    }




    public boolean Auth(){
        Statement statement = base.getStatement();
        try {
            Crypt crypt = new Crypt();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM logininfo");
            while (resultSet.next()) {
                if (crypt.decrypt(resultSet.getString("username"),key).equals(cUser) && crypt.decrypt(resultSet.getString("password"),key).equals(cPWord)) {
                    return true;
                }
            }
        }catch (Exception e){
            e.printStackTrace();

        }
        return false;

    }







}
