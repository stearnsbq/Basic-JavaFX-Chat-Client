package database;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Base64;

public class Database {

    private static Connection connection;
    private static Statement statement;


    public Database() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/login", "root", "");
            statement = connection.createStatement();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static Statement getStatement() {
        return statement;
    }

    public static Connection getConnection() {
        return connection;
    }

    public void addAccountToDataBase(String cryptUsername, String cryptPassword) throws Exception{
        String sql = String.format("INSERT INTO logininfo (usernames, passwords) VALUES ('%s', '%s')", cryptUsername,cryptPassword);
        statement.executeUpdate(sql);
        System.out.println("User Registered into Database");

    }



    public static void main(String args[]) throws Exception{


    }
}
