/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ezeventparkingclient;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ASUS
 */
public class SocketManager {

    private static SocketManager instance;
    private Socket clientSocket;

    private SocketManager() {
        try {
            openConnection();
        } catch (Exception Ex) {
            System.out.println("Error di SocketManager");
        }
    }

    public static SocketManager getInstance() {
        if (instance == null) {
            instance = new SocketManager();
        }
        return instance;
    }

    public Socket getClientSocket() {
        return clientSocket;
    }

    public void openConnection() {
        try {
            clientSocket = new Socket("192.168.43.136", 12345);
        } catch (IOException ex) {
            Logger.getLogger(SocketManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void closeConnection() {
        try {
            if (clientSocket != null && !clientSocket.isClosed()) {
                this.clientSocket.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(SocketManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
