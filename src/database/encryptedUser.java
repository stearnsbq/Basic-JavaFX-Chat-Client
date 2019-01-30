package database;

import javax.crypto.SecretKey;

public final class encryptedUser {

    private String username;
    private String password;
    private SecretKey key;


    public void setKey(SecretKey key) {
        this.key = key;
    }


    public void setPassword(String password) {
        this.password = password;
    }


    public void setUsername(String username) {
        this.username = username;
    }


    public SecretKey getKey() {
        return key;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

}
