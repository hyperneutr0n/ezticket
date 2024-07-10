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
    String userLogged;

    public HandleSocket(Server parent, Socket socket) {
        System.out.println("New handle socket created");
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
            System.out.println(message);
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
        String name;
        int locationID;
        double price;
        int userID;
        int parkingLotID;
        int eventID;
        String reservationDate;
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
                        name = splitMessage.get(0);
                        locationID = Integer.parseInt(splitMessage.get(1));
                        int capacity = Integer.parseInt(splitMessage.get(2));
                        price = Double.parseDouble(splitMessage.get(3));
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
                        name = splitMessage.get(0);
                        String description = splitMessage.get(1);
                        locationID = Integer.parseInt(splitMessage.get(2));
                        price = Double.parseDouble(splitMessage.get(3));
                        String date = splitMessage.get(4);
                        message = parentServer.eventPort.addEvent(name, description, locationID, price, date);
                        SendMessage(message);
                        break;
                    case "getallevent":
                        message = parentServer.eventPort.getAllEvent();
                        SendMessage(message);
                        break;
                    default:
                        message = "Method " + methodName + " in class " + className + " doesn't exist!";
                        SendMessage(message);
                }
                break;
            case "parkingticket":
                switch (methodName) {
                    case "buyparkingticket":
                        userID = Integer.parseInt(splitMessage.get(0));
                        parkingLotID = Integer.parseInt(splitMessage.get(1));
                        String slotNumber = splitMessage.get(2);
                        double ticketPrice = Double.parseDouble(splitMessage.get(3));
                        reservationDate = splitMessage.get(4);
                        message = parentServer.parkingTicketPort.buyParkingTicket(userID, parkingLotID, slotNumber, ticketPrice, reservationDate);
                        SendMessage(message);
                        break;
                    case "getoccupiedslot":
                        reservationDate = splitMessage.get(0);
                        parkingLotID = Integer.parseInt(splitMessage.get(1));
                        message = parentServer.parkingTicketPort.getOccupiedSlot(reservationDate, parkingLotID);
                        SendMessage(message);
                        break;
                    case "getalluserticket":
                        userID = Integer.parseInt(splitMessage.get(0));
                        message = parentServer.parkingTicketPort.getAllUserTicket(userID);
                        SendMessage(message);
                        break;
                    default:
                        SendMessage("Method " + methodName + " in class " + className + " doesn't exist!");
                }
                break;
            case "eventreservation":
                switch (methodName) {
                    case "buyeventreservation":
                        userID = Integer.parseInt(splitMessage.get(0));
                        eventID = Integer.parseInt(splitMessage.get(1));
                        reservationDate = splitMessage.get(2);
                        message = parentServer.eventReservationPort.buyEventReservation(userID, eventID, reservationDate);
                        SendMessage(message);
                        break;
                    case "getalleventreservation":
                        message = parentServer.eventReservationPort.getAllEventReservation();
                        SendMessage(message);
                        break;
                    case "getalluserreservation":
                        userID = Integer.parseInt(splitMessage.get(0));
                        message = parentServer.eventReservationPort.getAllUserReservation(userID);
                        SendMessage(message);
                        break;
                    case "claimreservation":
                        userID = Integer.parseInt(splitMessage.get(0));
                        eventID = Integer.parseInt(splitMessage.get(1));
                        String claimDate = splitMessage.get(2);
                        message = parentServer.eventReservationPort.claimReservation(userID, eventID, claimDate);
                        SendMessage(message);
                        break;
                    default:
                        SendMessage("Method " + methodName + " in class " + className + " doesn't exist!");
                }
                break;
            default:
                SendMessage("Class " + className + " doesn't exist!");
        }
    }

    @Override
    public void run() {
        while (true) {
            if (!socket.isClosed()) {
                RetrieveMessage();
            } else {
                try {
                    socket.close();
                    parentServer.listClients.remove(this);
                } catch (IOException ex) {
                    Logger.getLogger(HandleSocket.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
