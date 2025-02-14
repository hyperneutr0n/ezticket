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
        StringJoiner message = new StringJoiner("/");
        EventReservation objEventReservation = new EventReservation();
        ArrayList<EventReservation> eventReservationList = objEventReservation.SelectData();

        if (eventReservationList.isEmpty()) {
            message.add("FAILED");
        } else {
            message.add("SUCCESS");
            for (EventReservation eventReservation : eventReservationList) {
                message.add(eventReservation.toString());
            }
        }
        return message.toString();
    }

    /**
     * Web service operation
     *
     * @param userID the value of userID
     * @return String message
     */
    @WebMethod(operationName = "GetAllUserReservation")
    public String GetAllUserReservation(@WebParam(name = "userID") int userID) {
        //TODO write your implementation code here:
        StringJoiner message = new StringJoiner("/");
        EventReservation objEventReservation = new EventReservation();
        ArrayList<EventReservation> eventReservationList = objEventReservation.SelectUserReservation(userID);

        if (eventReservationList.isEmpty()) {
            message.add("FAILED");
        } else {
            message.add("SUCCESS");
            for (EventReservation eventReservation : eventReservationList) {
                message.add(eventReservation.toString());
            }
        }
        return message.toString();
    }

    /**
     * Web service operation
     *
     * @param userID the value of userID
     * @param eventID the value of eventID
     * @param claimDate the value of claimDate
     * @return String message
     */
    @WebMethod(operationName = "ClaimReservation")
    public String ClaimReservation(
            @WebParam(name = "userID") int userID,
            @WebParam(name = "eventID") int eventID,
            @WebParam(name = "claimDate") String claimDate
    ) {
        //TODO write your implementation code here:
        String message;
        User user = new User(userID);
        Event event = new Event(eventID);
        Timestamp claim_date = Timestamp.valueOf(claimDate);
        EventReservation eventReservation = new EventReservation(user, event, null, claim_date);

        int rowEffected = eventReservation.UpdateData();
        if (rowEffected == 0 || rowEffected > 1) {
            message = "Failed to claim event reservation.";
        } else {
            message = "Event reservation claimed successfully.";
        }
        return message;
    }
}
