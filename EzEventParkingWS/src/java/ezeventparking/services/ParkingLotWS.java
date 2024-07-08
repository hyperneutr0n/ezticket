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
@WebService(serviceName = "ParkingLotWS")
public class ParkingLotWS {

    /**
     * Web service operation
     *
     * @param name the value of name
     * @param locationID the value of locationID
     * @param capacity the value of capacity
     * @param price the value of price
     * @return String message
     */
    @WebMethod(operationName = "AddParkingLot")
    public String AddParkingLot(
            @WebParam(name = "name") String name,
            @WebParam(name = "location_id") int locationID,
            @WebParam(name = "capacity") int capacity,
            @WebParam(name = "price") double price
    ) {
        Location location = new Location(locationID);
        ParkingLot parkingLot = new ParkingLot(name, location, capacity, price);
        int rowEffected = parkingLot.InsertData();

        String message;
        if (rowEffected == 0 || rowEffected > 1) {
            message = "Failed to add parking lot.";
        } else {
            message = "Parking lot added successfully.";
        }
        return message;
    }

    /**
     * Web service operation
     *
     * @return String message
     */
    @WebMethod(operationName = "GetAllParkingLot")
    public String GetAllParkingLot() {
        //TODO write your implementation code here:
        StringJoiner message = new StringJoiner(";");
        ParkingLot objParkingLot = new ParkingLot();
        ArrayList<ParkingLot> parkingLotList = objParkingLot.SelectData();

        for (ParkingLot parkingLot : parkingLotList) {
            message.add(parkingLot.toString());
        }
        return message.toString();
    }
}
