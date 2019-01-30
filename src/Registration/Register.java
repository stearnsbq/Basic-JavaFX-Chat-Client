package Registration;

import database.Database;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Security;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Base64;

public class Register {




    public static void registerUser(String username, String password) throws Exception{
        Crypt crypt = new Crypt();
        Database database = new Database();
        Statement statement = database.getStatement();
        ResultSet resultSet = statement.executeQuery("");
        resultSet.first();
        byte[] decodedKey = Base64.getDecoder().decode(resultSet.getString(""));
        SecretKey originalKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
        database.addAccountToDataBase(crypt.encrypt(username, originalKey),crypt.encrypt(password, originalKey));
    }























}
