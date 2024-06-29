/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ezeventparking.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author user
 */
public class Event {
    private int id;
    private String name;
    private String description;
    private Location location;
    private double price;
    private Timestamp date;

    public Event() {}

    public Event(String name, String description, Location location, double price, Timestamp date) {
        this.name = name;
        this.description = description;
        this.location = location;
        this.price = price;
        this.date = date;
    }

    public Event(int id, String name, String description, Location location, double price, Timestamp date) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.location = location;
        this.price = price;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }
    
    public static ArrayList<Event> SelectData() {
        ArrayList<Event> listEvents = new ArrayList<>();
        String sql = "SELECT * FROM events";
        try {
            Statement stmt = Model.conn.createStatement();
            ResultSet result = stmt.executeQuery(sql);
            
            while(result.next()) {
                Location location = Location.FindLocation(result.getInt("locations_id"));
                
                Event event = new Event(
                        result.getInt("id"),
                        result.getString("name"),
                        result.getString("description"),
                        location,
                        result.getDouble("price"),
                        result.getTimestamp("date")
                );
                
                listEvents.add(event);
            }
        } catch (SQLException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listEvents;
    }
    
    public void InsertData() {
        String sql = "INSERT INTO events (name, description, locations_id, price, date) VALUES (?,?,?,?,?)";
        try {
            if (!Model.conn.isClosed()) {
                PreparedStatement pstmt = Model.conn.prepareStatement(sql);
                pstmt.setString(1, this.getName());
                pstmt.setString(2, this.getDescription());
                pstmt.setInt(3, this.getLocation().getId());
                pstmt.setDouble(4, this.getPrice());
                pstmt.setDate(5, new Date(this.getDate().getTime()));
                pstmt.executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void UpdateData() {
        String sql = "UPDATE events SET name=?, description=?, locations_id=?, price=?, date=? WHERE id=?";
        try {
            if (!Model.conn.isClosed()) {
                PreparedStatement pstmt = Model.conn.prepareStatement(sql);
                pstmt.setInt(6, this.getId());
                pstmt.setString(1, this.getName());
                pstmt.setString(2, this.getDescription());
                pstmt.setInt(3, this.getLocation().getId());
                pstmt.setDouble(4, this.getPrice());
                pstmt.setDate(5, new Date(this.getDate().getTime()));
                pstmt.executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void DeleteData() {
        String sql = "DELETE FROM events WHERE id=?";
        try {
            if (!Model.conn.isClosed()) {
                PreparedStatement pstmt = Model.conn.prepareStatement(sql);
                pstmt.setInt(1, this.getId());
                pstmt.executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static Event FindEvent(int id) {
        String sql = "SELECT * FROM events WHERE id=?";
        Event searchResult = null;
        try {
            Statement stmt = Model.conn.createStatement();
            ResultSet result = stmt.executeQuery(sql);
            
            if(result.next()) {
                Location location = Location.FindLocation(result.getInt("locations_id"));
                
                searchResult = new Event(
                        result.getInt("id"),
                        result.getString("name"),
                        result.getString("description"),
                        location,
                        result.getDouble("price"),
                        result.getTimestamp("date")
                );
            }
        } catch (SQLException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
        return searchResult;
    }
}
