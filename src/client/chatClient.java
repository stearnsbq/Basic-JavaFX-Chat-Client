package client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class chatClient {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public void startConnection(String ip, int port) throws Exception{
        clientSocket = new Socket(ip,port);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }



    public void stopConnection()throws Exception {
        clientSocket.close();
        in.close();
        out.close();
    }


    public void sendMessage(String msg){
        out.println(msg);
    }

    public String getMessage() throws Exception{
        return in.readLine();
    }


}
