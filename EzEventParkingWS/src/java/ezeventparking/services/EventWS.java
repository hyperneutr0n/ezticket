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
@WebService(serviceName = "EventWS")
public class EventWS {

    /**
     * Web service operation
     *
     * @param name the value of name
     * @param description the value of description
     * @param locationID the value of locationID
     * @param price the value of price
     * @param date the value of date
     * @return String message
     */
    @WebMethod(operationName = "AddEvent")
    public String AddEvent(
            @WebParam(name = "name") String name,
            @WebParam(name = "description") String description,
            @WebParam(name = "locationID") int locationID,
            @WebParam(name = "price") double price,
            @WebParam(name = "date") String date
    ) {
        //TODO write your implementation code here:
        Location location = new Location(locationID);
        Event event = new Event(name, description, location, price, Timestamp.valueOf(date));
        int rowEffected = event.InsertData();

        String message;
        if (rowEffected == 0 || rowEffected > 1) {
            message = "Failed to add event.";
        } else {
            message = "Event added successfully.";
        }
        return message;
    }

    /**
     * Web service operation
     *
     * @return String message
     */
    @WebMethod(operationName = "GetAllEvent")
    public String GetAllEvent() {
        //TODO write your implementation code here:
        StringJoiner message = new StringJoiner("/");
        Event objEvent = new Event();
        ArrayList<Event> eventList = objEvent.SelectData();

        if (eventList.isEmpty()) {
            message.add("FAILED");
        } else {
            message.add("SUCCESS");
            for (Event event : eventList) {
                message.add(event.toString());
            }
        }
        return message.toString();
    }
}
