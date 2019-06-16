package server.Registration;

import server.database.Database;

public class Register {

    public void registerUser(String username, String password) throws Exception{
            Hash crypt = new Hash();
            Database database = new Database();
            database.addAccountToDataBase(username,crypt.hash(password));
    }






















}
