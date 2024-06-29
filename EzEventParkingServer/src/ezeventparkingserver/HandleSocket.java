/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ezeventparkingserver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author user
 */
public class HandleSocket extends Thread {
    Server parentServer;
    Socket socket;
    BufferedReader in;
    DataOutputStream out;
    
     public HandleSocket(Server parent, Socket socket) {
        try {
            this.parentServer = parent;
            this.socket = socket;
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(HandleSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     
    public void SendMessage(String message)
    {
        try {
            out.writeBytes(message+"\n");
        } catch (IOException ex) {
            Logger.getLogger(HandleSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void RetrieveMessage()
    {
        try {
            String message = in.readLine();
            
            HandleMessage(message);
        } catch (IOException ex) {
            Logger.getLogger(HandleSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void HandleMessage(String message) {
        String[] splitMessage = message.split("/", 0);
        String className = splitMessage[0];
        String methodName = splitMessage[1];
        
    }
    
    @Override
    public void run() {
        while(true)
        {
            RetrieveMessage();
        }
    }
}
