package server.Registration;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

public class Hash {
     private int iterations = 10;
     private int memory = 65536;
     private int parallelism = 1;

    public String hash(String pw){
        String hash;
        Argon2 argon2 = Argon2Factory.create();
        char[] password = pw.toCharArray();
        try {
           hash = argon2.hash(iterations, memory, parallelism, password);
        } finally {
            argon2.wipeArray(password);
        }
        return hash;
    }


}
