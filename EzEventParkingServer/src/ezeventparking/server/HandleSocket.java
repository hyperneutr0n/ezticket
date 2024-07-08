/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ezeventparking.server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Arrays;
import java.util.ArrayList;
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
            out = new DataOutputStream(this.socket.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(HandleSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void SendMessage(String message) {
        try {
            out.writeBytes(message + "\n");
        } catch (IOException ex) {
            Logger.getLogger(HandleSocket.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void RetrieveMessage() {
        try {
            String message = in.readLine();

            HandleMessage(message);
        } catch (IOException ex) {
            Logger.getLogger(HandleSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void HandleMessage(String message) {
        ArrayList<String> splitMessage = new ArrayList<>(Arrays.asList(message.split("/", 0)));
        String className = splitMessage.get(0);
        String methodName = splitMessage.get(1);
        splitMessage.remove(0);
        splitMessage.remove(0);
        switch (className) {
            case "user":
                switch (methodName) {
                    case "register":
                        String status = parentServer.userPort.register(splitMessage.get(0), splitMessage.get(0), splitMessage.get(0), splitMessage.get(0));
                        SendMessage(status);
                        break;
                    case "checklogin":
                        break;
                    default:
                        SendMessage("Method " + methodName + " in class " + className + " doesn't exist!");
                }
                break;
            case "location":

                break;
            case "parkinglot":

                break;
            case "event":

                break;
            case "parkingticket":

                break;
            case "eventreservation":

                break;
            default:
                SendMessage("Class " + className + " doesn't exist!");
        }
    }

    @Override
    public void run() {
        while (true) {
            RetrieveMessage();
        }
    }
}
