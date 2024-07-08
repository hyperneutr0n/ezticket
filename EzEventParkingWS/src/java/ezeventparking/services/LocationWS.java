/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/WebServices/WebService.java to edit this template
 */
package ezeventparking.services;

import ezeventparking.model.*;
import java.util.ArrayList;
import java.util.StringJoiner;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author user
 */
@WebService(serviceName = "LocationWS")
public class LocationWS {

    /**
     * @param name the value of name
     * @param address the value of address
     * @return String message
     */
    @WebMethod(operationName = "AddLocation")
    public String AddLocation(
            @WebParam(name = "name") String name,
            @WebParam(name = "address") String address
    ) {
        //TODO write your implementation code here:
        Location location = new Location(name, address);
        int rowEffected = location.InsertData();

        String message;
        if (rowEffected == 0 || rowEffected > 1) {
            message = "Failed to add location.";
        } else {
            message = "Location added successfully.";
        }
        return message;
    }

    /**
     * Web service operation
     *
     * @return String message
     */
    @WebMethod(operationName = "GetAllLocation")
    public String GetAllLocation() {
        //TODO write your implementation code here:
        StringJoiner message = new StringJoiner(";");
        Location objLocation = new Location();
        ArrayList<Location> locationList = objLocation.SelectData();

        for (Location location : locationList) {
            message.add(location.toString());
        }
        return message.toString();
    }
}
