package com.fejkathegame.client;

import java.net.*;
import java.io.*;

/**
 * Created by Swartt on 2015-04-30.
 */
public class Client implements Runnable{

    public static void main(String[] args) {
        String serverName = "localhost";
        int port = 6112;
        try {
            System.out.println("Connecting to " + serverName
                    + " on port " + port);
            
            Socket client = new Socket(serverName, port);
            
            System.out.println("Just connected to "
                    + client.getRemoteSocketAddress());
            
            OutputStream outToServer = client.getOutputStream();
            
            DataOutputStream out
                    = new DataOutputStream(outToServer);

            out.writeUTF("Hello from "
                    + client.getLocalSocketAddress());
            
            InputStream inFromServer = client.getInputStream();
            
            DataInputStream in
                    = new DataInputStream(inFromServer);
            
            System.out.println("Server says " + in.readUTF());
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        String serverName = "localhost";
        int port = 6112;
        try {
            System.out.println("Connecting to " + serverName
                    + " on port " + port);
            
            Socket client = new Socket(serverName, port);
            
            System.out.println("Just connected to "
                    + client.getRemoteSocketAddress());
            
            OutputStream outToServer = client.getOutputStream();
            
            DataOutputStream out
                    = new DataOutputStream(outToServer);

            out.writeUTF("Hello from "
                    + client.getLocalSocketAddress());
            
            InputStream inFromServer = client.getInputStream();
            
            DataInputStream in
                    = new DataInputStream(inFromServer);
            
            System.out.println("Server says " + in.readUTF());
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
