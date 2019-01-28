package server;

public class ServerIsFullException extends Exception {


    public ServerIsFullException(String message){
        super(message);
    }
}
