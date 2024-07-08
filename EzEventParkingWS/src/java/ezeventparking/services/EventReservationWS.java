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
@WebService(serviceName = "EventReservationWS")
public class EventReservationWS {

    /**
     * This is a sample web service operation
     */
    @WebMethod(operationName = "hello")
    public String hello(@WebParam(name = "name") String txt) {
        return "Hello " + txt + " !";
    }

    /**
     * Web service operation
     *
     * @param userID the value of userID
     * @param eventID the value of userID
     * @param reservationDate the value of reservationDate
     * @return String message
     */
    @WebMethod(operationName = "BuyEventReservation")
    public String BuyEventReservation(
            @WebParam(name = "userID") int userID,
            @WebParam(name = "eventID") int eventID,
            @WebParam(name = "reservationDate") String reservationDate
    ) {
        //TODO write your implementation code here:
        String message;
        User user = new User(userID);
        Event event = new Event(eventID);
        EventReservation reservation = new EventReservation(user, event, Timestamp.valueOf(reservationDate));

        int rowEffected = reservation.InsertData();
        if (rowEffected == 0 || rowEffected > 1) {
            message = "Failed to buy event reservation.";
        } else {
            message = "Event reservation bought successfully.";
        }
        return message;
    }

    /**
     * Web service operation
     *
     * @return String message
     */
    @WebMethod(operationName = "GetAllEventReservation")
    public String GetAllEventReservation() {
        //TODO write your implementation code here:
        StringJoiner message = new StringJoiner(";");
        EventReservation objEventReservation = new EventReservation();
        ArrayList<EventReservation> eventReservationList = objEventReservation.SelectData();

        for (EventReservation eventReservation : eventReservationList) {
            message.add(eventReservation.toString());
        }
        return null;
    }

}
