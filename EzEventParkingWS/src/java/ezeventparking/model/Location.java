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
public class Location extends Model {

    // <editor-fold defaultstate="collapsed" desc="Data Members">
    private int id;
    private String name;
    private String address;
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Constructors">
    public Location() {
    }

    public Location(int id) {
        this.id = id;
    }

    public Location(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Location(String name) {
        this.name = name;
    }

    public Location(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public Location(int id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Methods">
    public ArrayList<Location> SelectData() {
        ArrayList<Location> listLocations = new ArrayList<>();
        String sql = "SELECT * FROM locations";
        try {
            stmt = Model.conn.createStatement();
            result = stmt.executeQuery(sql);

            while (result.next()) {
                Location location = new Location(
                        result.getInt("id"),
                        result.getString("name"),
                        result.getString("address")
                );
                listLocations.add(location);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Location.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listLocations;
    }

    @Override
    public int InsertData() {
        String sql = "INSERT INTO locations (name, address) VALUES (?, ?)";
        int rowEffected = 0;
        try {
            if (!Model.conn.isClosed()) {
                PreparedStatement pstmt = Model.conn.prepareStatement(sql);
                pstmt.setString(1, this.getName());
                pstmt.setString(2, getAddress());
                rowEffected = pstmt.executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(Location.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rowEffected;
    }

    @Override
    public int UpdateData() {
        String sql = "UPDATE locations SET name=?, address=? WHERE id=?";
        int rowEffected = 0;
        try {
            if (!Model.conn.isClosed()) {
                PreparedStatement pstmt = Model.conn.prepareStatement(sql);
                pstmt.setInt(3, this.getId());
                pstmt.setString(1, this.getName());
                pstmt.setString(2, getAddress());
                rowEffected = pstmt.executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(Location.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rowEffected;
    }

    @Override
    public int DeleteData() {
        String sql = "DELETE FROM users WHERE id=?";
        int rowEffected = 0;
        try {
            if (!Model.conn.isClosed()) {
                PreparedStatement pstmt = Model.conn.prepareStatement(sql);
                pstmt.setInt(1, this.getId());
                rowEffected = pstmt.executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(Location.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rowEffected;
    }

    public Location FindLocation(int id) {
        String sql = "SELECT * FROM locations WHERE id=?";
        Location searchResult = null;
        try {
            if (!Model.conn.isClosed()) {
                PreparedStatement pstmt = Model.conn.prepareStatement(sql);
                pstmt.setInt(1, id);
                result = pstmt.executeQuery();
                if (result.next()) {
                    searchResult = new Location(
                            result.getInt("id"),
                            result.getString("name"),
                            result.getString("address")
                    );
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(Location.class.getName()).log(Level.SEVERE, null, ex);
        }
        return searchResult;
    }

    @Override
    public String toString() {
        return this.getId() + "," + this.getName() + "," + this.getAddress();
    }
    // </editor-fold>
}
