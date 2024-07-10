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

    // <editor-fold defaultstate="collapsed" desc="Data Members">
    private int id;
    private String name;
    private Location location;
    private int capacity;
    private double price;
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Constructors">
    public ParkingLot() {
    }

    public ParkingLot(int id) {
        this.id = id;
    }

    public ParkingLot(String name) {
        this.name = name;
    }

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
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Properties">
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
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Methods">
    public ArrayList<ParkingLot> SelectData() {
        ArrayList<ParkingLot> listParkingLots = new ArrayList<>();
        String sql = "SELECT * FROM parking_lots";
        try {
            stmt = Model.conn.createStatement();
            result = stmt.executeQuery(sql);

            while (result.next()) {
                Location rowLocation = new Location(result.getInt("locations_id"));

                ParkingLot parkingLot = new ParkingLot(
                        result.getInt("id"),
                        result.getString("name"),
                        rowLocation,
                        result.getInt("capacity"),
                        result.getDouble("price")
                );

                listParkingLots.add(parkingLot);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ParkingLot.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listParkingLots;
    }

    @Override
    public int InsertData() {
        String sql = "INSERT INTO parking_lots (name, locations_id, capacity, price) VALUES (?, ?, ?, ?)";
        int rowEffected = 0;
        try {
            if (!Model.conn.isClosed()) {
                PreparedStatement pstmt = Model.conn.prepareStatement(sql);
                pstmt.setString(1, this.getName());
                pstmt.setInt(2, this.getLocation().getId());
                pstmt.setInt(3, this.getCapacity());
                pstmt.setDouble(4, this.getPrice());
                rowEffected = pstmt.executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ParkingLot.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rowEffected;
    }

    @Override
    public int UpdateData() {
        String sql = "UPDATE parking_lots SET name=?, location_id=?, capacity=?, price=? WHERE id=?";
        int rowEffected = 0;
        try {
            if (!Model.conn.isClosed()) {
                PreparedStatement pstmt = Model.conn.prepareStatement(sql);
                pstmt.setInt(5, this.getId());
                pstmt.setString(1, this.getName());
                pstmt.setInt(2, this.getLocation().getId());
                pstmt.setInt(3, this.getCapacity());
                pstmt.setDouble(4, this.getPrice());
                rowEffected = pstmt.executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ParkingLot.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rowEffected;
    }

    @Override
    public int DeleteData() {
        String sql = "DELETE FROM parking_lots WHERE id=?";
        int rowEffected = 0;
        try {
            if (!Model.conn.isClosed()) {
                PreparedStatement pstmt = Model.conn.prepareStatement(sql);
                pstmt.setInt(1, this.getId());
                rowEffected = pstmt.executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ParkingLot.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rowEffected;
    }

    public ParkingLot FindParkingLot(int id) {
        String sql = "SELECT * FROM parking_lots WHERE id=?";
        ParkingLot searchResult = null;
        try {
            if (!Model.conn.isClosed()) {
                PreparedStatement pstmt = Model.conn.prepareStatement(sql);
                pstmt.setInt(1, id);
                result = pstmt.executeQuery();
                if (result.next()) {
                    Location searchedLocation = new Location(result.getInt("locations_id"));

                    searchResult = new ParkingLot(
                            result.getInt("id"),
                            result.getString("name"),
                            searchedLocation,
                            result.getInt("capacity"),
                            result.getDouble("price")
                    );
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ParkingLot.class.getName()).log(Level.SEVERE, null, ex);
        }
        return searchResult;
    }

    public ArrayList<ParkingLot> SelectDataByLocation(int locationID) {
        ArrayList<ParkingLot> listParkingLots = new ArrayList<>();
        String sql = "SELECT * FROM parking_lots WHERE locations_id=?";
        try {
            PreparedStatement pstmt = Model.conn.prepareStatement(sql);
            pstmt.setInt(1, locationID);
            result = pstmt.executeQuery();

            while (result.next()) {
                Location rowLocation = new Location(locationID);

                ParkingLot parkingLot = new ParkingLot(
                        result.getInt("id"),
                        result.getString("name"),
                        rowLocation,
                        result.getInt("capacity"),
                        result.getDouble("price")
                );

                listParkingLots.add(parkingLot);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ParkingLot.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listParkingLots;
    }

    @Override
    public String toString() {
        return this.getId() + ","
                + this.getName() + ","
                + this.getLocation().getId() + ","
                + this.getCapacity() + ","
                + this.getPrice();
    }
    // </editor-fold>
}
