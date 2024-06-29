/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ezeventparkingserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author user
 */
public class Server {
    ServerSocket serverSocket;
    Socket socket;
    ArrayList<HandleSocket> listClients;

    public Server() {
        try {
            listClients = new ArrayList<>();
            serverSocket = new ServerSocket(12345);
            
            while(true)
            {
                socket = serverSocket.accept();
                HandleSocket hs = new HandleSocket(this, socket);
                hs.start();
                listClients.add(hs);
            }
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
