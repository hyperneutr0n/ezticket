/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ezeventparkingclient;

import java.net.Socket;

/**
 *
 * @author ASUS
 */
public class SocketManager {

    private static SocketManager instance;
    private Socket clientSocket;

    private SocketManager() {
        try {
            clientSocket = new Socket("192.168.180.136", 12345);
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

}
