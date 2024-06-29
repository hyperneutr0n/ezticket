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
public class ParkingLot extends Model {
    private int id;
    private String name;
    private Location location;
    private int capacity;
    private double price;

    public ParkingLot() {}

    public ParkingLot(String name, Location location, int capacity, double price) {
        this.name = name;
        this.location = location;
        this.capacity = capacity;
        this.price = price;
    }
    
    public ParkingLot(int id, String name, Location location, int capacity, double price) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.capacity = capacity;
        this.price = price;
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

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    
    public static ArrayList<ParkingLot> SelectData() {
        ArrayList<ParkingLot> listParkingLots = new ArrayList<>();
        String sql = "SELECT * FROM parking_lots";
        try {
            Statement stmt = Model.conn.createStatement();
            ResultSet result = stmt.executeQuery(sql);
            
            while(result.next()) {
                Location location = Location.FindLocation(result.getInt("locations_id"));
                
                ParkingLot parkingLot = new ParkingLot(
                        result.getInt("id"),
                        result.getString("name"),
                        location,
                        result.getInt("capacity"),
                        result.getDouble("price")
                );
                
                listParkingLots.add(parkingLot);
            }
        } catch (SQLException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listParkingLots;
    }
    
    @Override
    public void InsertData() {
        String sql = "INSERT INTO parking_lots (name, locations_id, capacity, price) VALUES (?, ?, ?, ?)";
        try {
            if (!Model.conn.isClosed()) {
                PreparedStatement pstmt = Model.conn.prepareStatement(sql);
                pstmt.setString(1, this.getName());
                pstmt.setInt(2, this.getLocation().getId());
                pstmt.setInt(3, this.getCapacity());
                pstmt.setDouble(4, this.getPrice());
                pstmt.executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void UpdateData() {
        String sql = "UPDATE parking_lots SET name=?, location_id=?, capacity=?, price=? WHERE id=?";
        try {
            if (!Model.conn.isClosed()) {
                PreparedStatement pstmt = Model.conn.prepareStatement(sql);
                pstmt.setInt(5, this.getId());
                pstmt.setString(1, this.getName());
                pstmt.setInt(2, this.getLocation().getId());
                pstmt.setInt(3, this.getCapacity());
                pstmt.setDouble(4, this.getPrice());
                pstmt.executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void DeleteData() {
        String sql = "DELETE FROM parking_lots WHERE id=?";
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
    
    public static ParkingLot FindParkingLot(int id) {
        String sql = "SELECT * FROM parking_lots WHERE id=?";
        ParkingLot searchResult = null;
        try {
            if (!Model.conn.isClosed()) {
                PreparedStatement pstmt = Model.conn.prepareStatement(sql);
                pstmt.setInt(2, id);
                ResultSet result = pstmt.executeQuery();
                if (result.next()) {
                    Location location = Location.FindLocation(result.getInt("locations_id"));
                
                    searchResult = new ParkingLot(
                        result.getInt("id"),
                        result.getString("name"),
                        location,
                        result.getInt("capacity"),
                        result.getDouble("price")
                    );
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
        return searchResult;
    }
}
