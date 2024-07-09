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
import java.sql.Timestamp;
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
    String userLogged;

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

            System.out.println(message);
            HandleMessage(message);
        } catch (IOException ex) {
            Logger.getLogger(HandleSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void HandleMessage(String receivedMessage) {
        ArrayList<String> splitMessage = new ArrayList<>(Arrays.asList(receivedMessage.split("/", 0)));
        String className = splitMessage.get(0);
        String methodName = splitMessage.get(1);
        splitMessage.remove(0);
        splitMessage.remove(0);

        String message;
        switch (className) {
            case "user":
                switch (methodName) {
                    case "register":
                        message = parentServer.userPort.register(splitMessage.get(0), splitMessage.get(1), splitMessage.get(2), splitMessage.get(3));
                        SendMessage(message);
                        break;
                    case "checklogin":
                        message = parentServer.userPort.checkLogin(splitMessage.get(0), splitMessage.get(1));
                        if (!message.equals("null")) {
                            this.userLogged = message.split(",")[3];
                            SendMessage("SUCCESS/" + message);
                        } else {
                            SendMessage("FAILED");
                        }
                        break;
                    default:
                        message = "Method " + methodName + " in class " + className + " doesn't exist!";
                        SendMessage(message);
                }
                break;
            case "location":
                switch (methodName) {
                    case "addlocation":
                        message = parentServer.locationPort.addLocation(splitMessage.get(0), splitMessage.get(1));
                        SendMessage(message);
                        break;
                    case "getalllocation":
                        message = parentServer.locationPort.getAllLocation();
                        SendMessage(message);
                    default:
                        message = "Method " + methodName + " in class " + className + " doesn't exist!";
                        SendMessage(message);
                }
                break;
            case "parkinglot":
                switch (methodName) {
                    case "addparkinglot":
                        String name = splitMessage.get(0);
                        int locationID = Integer.parseInt(splitMessage.get(1));
                        int capacity = Integer.parseInt(splitMessage.get(2));
                        double price = Double.parseDouble(splitMessage.get(3));
                        message = parentServer.parkingLotPort.addParkingLot(name, locationID, capacity, price);
                        SendMessage(message);
                        break;
                    case "getallparkinglot":
                        message = parentServer.parkingLotPort.getAllParkingLot();
                        SendMessage(message);
                        break;
                    default:
                        message = "Method " + methodName + " in class " + className + " doesn't exist!";
                        SendMessage(message);
                }
                break;
            case "event":
                switch (methodName) {
                    case "addevent":

                        break;
                    case "getallevent":

                        break;
                    default:
                        throw new AssertionError();
                }
                break;
            case "parkingticket":
                switch (methodName) {
                    case "buyparkingticket":
                        int userID = Integer.parseInt(splitMessage.get(0));
                        int parkingLotID = Integer.parseInt(splitMessage.get(1));
                        String slotNumber = splitMessage.get(2);
                        double ticketPrice = Double.parseDouble(splitMessage.get(3));
                        String reservationDate = splitMessage.get(4);
                        message = parentServer.parkingTicketPort.buyParkingTicket(userID, parkingLotID, slotNumber, ticketPrice, reservationDate);
                        SendMessage(message);
                        break;
                    case "getoccupiedslot":
                        String reservDate = splitMessage.get(0);
                        int plID = Integer.parseInt(splitMessage.get(1));
                        message = parentServer.parkingTicketPort.getOccupiedSlot(reservDate, plID);
                        SendMessage(message);
                        System.out.println(message + "\n");
                        break;
                    default:
                        SendMessage("Method " + methodName + " in class " + className + " doesn't exist!");
                }
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
