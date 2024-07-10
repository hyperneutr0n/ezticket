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
public class Event extends Model {

    // <editor-fold defaultstate="collapsed" desc="Data Members">
    private int id;
    private String name;
    private String description;
    private Location location;
    private double price;
    private Timestamp date;
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Constructors">
    public Event() {
    }

    public Event(int id) {
        this.id = id;
    }

    public Event(int id, String name) {
        this.id = id;
        this.name = name;
    }

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
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Methods">
    public ArrayList<Event> SelectData() {
        ArrayList<Event> listEvents = new ArrayList<>();
        String sql = "SELECT e.*, l.name AS 'location_name' FROM events e INNER JOIN locations l ON l.id = e.locations_id";
        try {
            stmt = Model.conn.createStatement();
            result = stmt.executeQuery(sql);

            while (result.next()) {
                Location rowLocation = new Location(result.getString("locations_id"), result.getString("location_name"));

                Event event = new Event(
                        result.getInt("id"),
                        result.getString("name"),
                        result.getString("description"),
                        rowLocation,
                        result.getDouble("price"),
                        result.getTimestamp("date")
                );

                listEvents.add(event);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Event.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listEvents;
    }

    @Override
    public int InsertData() {
        String sql = "INSERT INTO events (name, description, locations_id, price, date) VALUES (?,?,?,?,?)";
        int rowEffected = 0;
        try {
            if (!Model.conn.isClosed()) {
                PreparedStatement pstmt = Model.conn.prepareStatement(sql);
                pstmt.setString(1, this.getName());
                pstmt.setString(2, this.getDescription());
                pstmt.setInt(3, this.getLocation().getId());
                pstmt.setDouble(4, this.getPrice());
                pstmt.setDate(5, new Date(this.getDate().getTime()));
                rowEffected = pstmt.executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(Event.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rowEffected;
    }

    @Override
    public int UpdateData() {
        String sql = "UPDATE events SET name=?, description=?, locations_id=?, price=?, date=? WHERE id=?";
        int rowEffected = 0;
        try {
            if (!Model.conn.isClosed()) {
                PreparedStatement pstmt = Model.conn.prepareStatement(sql);
                pstmt.setInt(6, this.getId());
                pstmt.setString(1, this.getName());
                pstmt.setString(2, this.getDescription());
                pstmt.setInt(3, this.getLocation().getId());
                pstmt.setDouble(4, this.getPrice());
                pstmt.setDate(5, new Date(this.getDate().getTime()));
                rowEffected = pstmt.executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(Event.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rowEffected;
    }

    @Override
    public int DeleteData() {
        String sql = "DELETE FROM events WHERE id=?";
        int rowEffected = 0;
        try {
            if (!Model.conn.isClosed()) {
                PreparedStatement pstmt = Model.conn.prepareStatement(sql);
                pstmt.setInt(1, this.getId());
                rowEffected = pstmt.executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(Event.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rowEffected;
    }

    public Event FindEvent(int id) {
        String sql = "SELECT * FROM events WHERE id=?";
        Event searchResult = null;
        try {
            if (!Model.conn.isClosed()) {
                PreparedStatement pstmt = Model.conn.prepareStatement(sql);
                pstmt.setInt(1, id);
                result = pstmt.executeQuery();
                if (result.next()) {
                    Location searchedLocation = new Location(result.getString("location_name"));

                    searchResult = new Event(
                            result.getInt("id"),
                            result.getString("name"),
                            result.getString("description"),
                            searchedLocation,
                            result.getDouble("price"),
                            result.getTimestamp("date")
                    );
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(Event.class.getName()).log(Level.SEVERE, null, ex);
        }
        return searchResult;
    }

    @Override
    public String toString() {
        return this.getId() + ";"
                + this.getName() + ";"
                + this.getDescription() + ";"
                + this.getLocation().getId() + ";"
                + this.getLocation().getName() + ";"
                + this.getPrice() + ";"
                + this.getDate().toString();
    }
    // </editor-fold>
}
