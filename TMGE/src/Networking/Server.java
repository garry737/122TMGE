package Networking;

import java.net.*;
import java.io.*;

public class Server extends Multiplayer{
    private ServerSocket    server   = null;
    boolean isConnected = false;
    // constructor with port

    class ServerThread extends Thread{
        public void run() {
            try {
                System.out.println("Waiting for a client ...");

                socket = server.accept();
                System.out.println("Client accepted");

                // takes input from the client socket
                input = new DataInputStream(
                        new BufferedInputStream(socket.getInputStream()));
                out = new DataOutputStream(socket.getOutputStream());
                callSuperReady();
            } catch (IOException i) {
                System.out.println(i);
            }
        }
    }
    public Server(int port)
    {
        super(true);
        // starts server and waits for a connection
        try
        {
            server = new ServerSocket(port);
            System.out.println("Server started");
        }
        catch(IOException i)
        {
            System.out.println(i);
        }
    }

    public void callSuperReady(){
        listener.opponentConnected();
        super.ready();
    }
    @Override
    public void ready() {
        ServerThread t = new ServerThread();
        t.start();
    }
}
