/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ezeventparking.server;

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

    ezeventparking.services.UserWS_Service userService = new ezeventparking.services.UserWS_Service();
    ezeventparking.services.UserWS userPort = userService.getUserWSPort();

    ezeventparking.services.LocationWS_Service locationService = new ezeventparking.services.LocationWS_Service();
    ezeventparking.services.LocationWS locationPort = locationService.getLocationWSPort();

    ezeventparking.services.ParkingLotWS_Service parkingLotService = new ezeventparking.services.ParkingLotWS_Service();
    ezeventparking.services.ParkingLotWS parkingLotPort = parkingLotService.getParkingLotWSPort();

    ezeventparking.services.EventWS_Service eventService = new ezeventparking.services.EventWS_Service();
    ezeventparking.services.EventWS eventPort = eventService.getEventWSPort();

    ezeventparking.services.ParkingTicketWS_Service parkingTicketService = new ezeventparking.services.ParkingTicketWS_Service();
    ezeventparking.services.ParkingTicketWS parkingTicketPort = parkingTicketService.getParkingTicketWSPort();

    ezeventparking.services.EventReservationWS_Service eventReservationService = new ezeventparking.services.EventReservationWS_Service();
    ezeventparking.services.EventReservationWS eventReservationPort = eventReservationService.getEventReservationWSPort();

    public Server() {
        try {
            listClients = new ArrayList<>();
            serverSocket = new ServerSocket(12345);
            System.out.println("Server started.");
            while (true) {
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
