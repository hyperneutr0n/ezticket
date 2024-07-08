/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/WebServices/WebService.java to edit this template
 */
package ezeventparking.services;

import ezeventparking.model.*;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author user
 */
@WebService(serviceName = "UserWS")
public class UserWS {

    /**
     * @param name the value of name
     * @param email the value of email
     * @param username the value of username
     * @param password the value of password
     * @return String message
     */
    @WebMethod(operationName = "Register")
    public String Register(
            @WebParam(name = "name") String name,
            @WebParam(name = "email") String email,
            @WebParam(name = "username") String username,
            @WebParam(name = "password") String password
    ) {
        //TODO write your implementation code here:
        User user = new User(name, email, username, password);
        int rowEffected = user.InsertData();

        String message;
        if (rowEffected == 0 || rowEffected > 1) {
            message = "Registration failed.";
        } else {
            message = "You have been registered successfully.";
        }
        return message;
    }

    /**
     * @param username the value of username
     * @param password the value of password
     * @return int logged in user id
     */
    @WebMethod(operationName = "CheckLogin")
    public int CheckLogin(
            @WebParam(name = "username") String username,
            @WebParam(name = "password") String password
    ) {
        //TODO write your implementation code here:
        User user = new User();
        User userLogged = user.CheckLogin(username, password);
        return userLogged.getId();
    }
}
