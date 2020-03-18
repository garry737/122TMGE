package Networking;

import BejeweledGUI.BejeweledUI;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Multiplayer {
    protected Socket socket            = null;
    protected DataInputStream input   = null;
    protected DataOutputStream out     = null;
    protected boolean isConnected = false;
    protected boolean isServer = false;
    public int opponentScore = 0;
    public boolean opponentReady = false;
    public boolean isReady = false;
    BejeweledUI.ScoreListener listener = null;

    public Multiplayer(boolean isServer){
        this.isServer = false;
    }

    protected class InputThread extends Thread{
        public void run() {
            while (isConnected){
                try {
                    //System.out.println("Waiting for msg");
                    System.out.println("Waiting for input");
                    var data = input.readUTF();
                    var inputs = data.split(" ");
                    if (inputs[0].equals("Ready")){
                        opponentReady = true;
                        listener.opponentIsReady();
                        if (opponentReady && isReady){
                            sendStart();
                            listener.bothAreReady();
                        }
                    } else if (inputs[0].equals("Start")){
                        listener.bothAreReady();
                    } else if(inputs[0].equals("Score")){
                        int ops = Integer.parseInt(inputs[1]);
                        System.out.printf("Score is %d\n", ops);
                        opponentScore = ops;
                        listener.opponentScoreUpdated();
                    }
                    else if(inputs[0].equals("Done")){
                        int ops = Integer.parseInt(inputs[1]);
                        opponentScore = ops;
                        listener.opponentDone();
                    }
                    System.out.println(data);
                } catch (IOException e) {
                    closeConnection();
                    e.printStackTrace();
                }
            }
        }
    }

    public void setListener(BejeweledGUI.BejeweledUI.MultiplayerButton listener){
        this.listener = listener;
    }

    public void ready(){
        isConnected = true;
        InputThread t = new InputThread();
        t.setDaemon(true);
        t.start();
    }

    public void closeConnection(){
        // close the connection
        try
        {
            isConnected = false;
            input.close();
            out.close();
            socket.close();
        }
        catch(IOException i)
        {
            System.out.println(i);
        }
    }

    public boolean sendDone(int score){
        try
        {
            out.writeUTF(String.format("Done %d", score));
            return true;
        }
        catch(IOException i)
        {
            System.out.println(i);
            return false;
        }
    }

    public boolean sendScore(int score){
        try
        {
            out.writeUTF(String.format("Score %d", score));
            return true;
        }
        catch(IOException i)
        {
            System.out.println(i);
            return false;
        }
    }

    public boolean sendStart(){
        try
        {
            out.writeUTF(String.format("Start"));
            return true;
        }
        catch(IOException i)
        {
            System.out.println(i);
            return false;
        }
    }

    public boolean sendReady(){
        try
        {
            out.writeUTF(String.format("Ready"));
            isReady = true;
            return true;
        }
        catch(IOException i)
        {
            System.out.println(i);
            return false;
        }
    }

}
