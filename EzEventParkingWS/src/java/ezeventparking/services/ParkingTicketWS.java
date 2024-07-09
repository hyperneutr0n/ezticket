/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/WebServices/WebService.java to edit this template
 */
package ezeventparking.services;

import ezeventparking.model.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.StringJoiner;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author user
 */
@WebService(serviceName = "ParkingTicketWS")
public class ParkingTicketWS {

    /**
     * Web service operation
     *
     * @param userID the value of userID
     * @param parkingLotID the value of parkingLotID
     * @param slotNumber the value of slotNumber
     * @param ticketPrice the value of ticketPrice
     * @param reservationDate the value of reservationDate
     * @return String message
     */
    @WebMethod(operationName = "BuyParkingTicket")
    public String BuyParkingTicket(@WebParam(name = "userID") int userID,
            @WebParam(name = "parkingLotID") int parkingLotID,
            @WebParam(name = "slotNumber") String slotNumber,
            @WebParam(name = "ticketPrice") double ticketPrice,
            @WebParam(name = "reservationDate") String reservationDate
    ) {
        //TODO write your implementation code here:
        String message;
        User user = new User(userID);
        ParkingLot parkingLot = new ParkingLot(parkingLotID);
        ParkingTicket parkingTicket = new ParkingTicket(user, parkingLot, slotNumber, ticketPrice, Timestamp.valueOf(reservationDate));
        int rowEffected = parkingTicket.InsertData();

        if (rowEffected == 0 || rowEffected > 1) {
            message = "Failed to buy parking ticket.";
        } else {
            message = "Parking ticket bought successfully.";
        }
        return message;
    }

    /**
     * Web service operation
     *
     * @return String message
     */
    @WebMethod(operationName = "GetAllParkingTicket")
    public String GetAllParkingTicket() {
        //TODO write your implementation code here:
        StringJoiner message = new StringJoiner("/");
        ParkingTicket objParkingTicket = new ParkingTicket();
        ArrayList<ParkingTicket> parkingTicketList = objParkingTicket.SelectData();

        if (parkingTicketList.isEmpty()) {
            message.add("FAILED");
        } else {
            message.add("SUCCESS");
            for (ParkingTicket parkingTicket : parkingTicketList) {
                message.add(parkingTicket.toString());
            }
        }
        return message.toString();
    }

    /**
     * Web service operation
     *
     * @param reservationDate the value of reservationDate
     * @param parkingLotID the value of parkingLotID
     * @return String message
     */
    @WebMethod(operationName = "GetOccupiedSlot")
    public String GetOccupiedSlot(String reservationDate, int parkingLotID) {
        //TODO write your implementation code here:
        Timestamp reservDate = Timestamp.valueOf(reservationDate);
        ParkingTicket objParkingTicket = new ParkingTicket();
        ArrayList<Object> result = objParkingTicket.FindOccupiedSlot(reservDate, parkingLotID);

        StringJoiner message = new StringJoiner("/");

        if (result.isEmpty()) {
            message.add("FAILED");
        } else {
            message.add("SUCCESS");
            message.add(String.valueOf(result.get(0)));
            result.remove(0);

            for (Object slot : result) {
                message.add(String.valueOf(slot));
            }
        }

        return message.toString();
    }

}
