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
public class ParkingTicket extends Model {

    // <editor-fold defaultstate="collapsed" desc="Data Members">
    private int id;
    private User user;
    private ParkingLot parkingLot;
    private String slotNumber;
    private double ticketPrice;
    private Timestamp reservationDate;
    private Timestamp entryDate;
    private Timestamp exitDate;
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Constructors">
    public ParkingTicket() {
    }

    public ParkingTicket(int id) {
        this.id = id;
    }

    public ParkingTicket(User user, ParkingLot parkingLot, String slotNumber, double ticketPrice, Timestamp reservationDate) {
        this.user = user;
        this.parkingLot = parkingLot;
        this.slotNumber = slotNumber;
        this.ticketPrice = ticketPrice;
        this.reservationDate = reservationDate;
    }

    public ParkingTicket(int id, User user, ParkingLot parkingLot, String slotNumber, double ticketPrice, Timestamp reservationDate) {
        this.id = id;
        this.user = user;
        this.parkingLot = parkingLot;
        this.slotNumber = slotNumber;
        this.ticketPrice = ticketPrice;
        this.reservationDate = reservationDate;
    }

    public ParkingTicket(int id, Timestamp entryDate, Timestamp exitDate) {
        this.id = id;
        this.entryDate = entryDate;
        this.exitDate = exitDate;
    }

    public ParkingTicket(int id, User user, ParkingLot parkingLot, String slotNumber, double ticketPrice, Timestamp reservationDate, Timestamp entryDate, Timestamp exitDate) {
        this.id = id;
        this.user = user;
        this.parkingLot = parkingLot;
        this.slotNumber = slotNumber;
        this.ticketPrice = ticketPrice;
        this.reservationDate = reservationDate;
        this.entryDate = entryDate;
        this.exitDate = exitDate;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Properties">
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ParkingLot getParkingLot() {
        return parkingLot;
    }

    public void setParkingLot(ParkingLot parkingLot) {
        this.parkingLot = parkingLot;
    }

    public String getSlotNumber() {
        return slotNumber;
    }

    public void setSlotNumber(String slotNumber) {
        this.slotNumber = slotNumber;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public Timestamp getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(Timestamp reservationDate) {
        this.reservationDate = reservationDate;
    }

    public Timestamp getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Timestamp entryDate) {
        this.entryDate = entryDate;
    }

    public Timestamp getExitDate() {
        return exitDate;
    }

    public void setExitDate(Timestamp exitDate) {
        this.exitDate = exitDate;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Methods">
    public ArrayList<ParkingTicket> SelectData() {
        ArrayList<ParkingTicket> listParkingTickets = new ArrayList<>();
        String sql = "SELECT pt.*, pl.name AS parking_lot_name, l.name AS location_name "
                + "FROM parking_tickets pt "
                + "INNER JOIN parking_lots pl ON pt.parking_lots_id = pl.id "
                + "INNER JOIN locations l ON pl.locations_id = l.id";
        try {
            stmt = Model.conn.createStatement();
            result = stmt.executeQuery(sql);

            while (result.next()) {
                User rowUser = new User(result.getInt("users_id"));

                ParkingLot rowParkingLot = new ParkingLot(result.getString("parking_lot_name"));

                rowParkingLot.setLocation(new Location(result.getString("location_name")));

                ParkingTicket parkingTicket = new ParkingTicket(
                        result.getInt("id"),
                        rowUser,
                        rowParkingLot,
                        result.getString("slot_number"),
                        result.getDouble("ticket_price"),
                        result.getTimestamp("reservation_date"),
                        result.getTimestamp("entry_date"),
                        result.getTimestamp("exit_date")
                );

                listParkingTickets.add(parkingTicket);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ParkingTicket.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listParkingTickets;
    }

    @Override
    public int InsertData() {
        String sql = "INSERT INTO parking_tickets (users_id, parking_lots_id, slot_number, ticket_price, reservation_date) VALUES (?,?,?,?,?)";
        int rowEffected = 0;
        try {
            if (!Model.conn.isClosed()) {
                PreparedStatement pstmt = Model.conn.prepareStatement(sql);
                pstmt.setInt(1, this.getUser().getId());
                pstmt.setInt(2, this.getParkingLot().getId());
                pstmt.setString(3, this.getSlotNumber());
                pstmt.setDouble(4, this.getTicketPrice());
                pstmt.setTimestamp(5, this.getReservationDate());
                rowEffected = pstmt.executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ParkingTicket.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rowEffected;
    }

    @Override
    public int UpdateData() {
        String sql = "UPDATE parking_tickets SET entry_date=?, exit_date=? WHERE id=?";
        int rowEffected = 0;
        try {
            if (!Model.conn.isClosed()) {
                PreparedStatement pstmt = Model.conn.prepareStatement(sql);
                pstmt.setInt(3, this.getId());
                pstmt.setTimestamp(1, this.getEntryDate());
                pstmt.setTimestamp(2, this.getExitDate());
                rowEffected = pstmt.executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ParkingTicket.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rowEffected;
    }

    @Override
    public int DeleteData() {
        String sql = "DELETE FROM parking_tickets WHERE id=?";
        int rowEffected = 0;
        try {
            if (!Model.conn.isClosed()) {
                PreparedStatement pstmt = Model.conn.prepareStatement(sql);
                pstmt.setInt(1, this.getId());
                rowEffected = pstmt.executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ParkingTicket.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rowEffected;
    }

    public ParkingTicket FindParkingTicket(int id) {
        ParkingTicket searchedResult = null;
        String sql = "SELECT * FROM parking_tickets WHERE id=?";
        try {
            if (!Model.conn.isClosed()) {
                PreparedStatement pstmt = Model.conn.prepareStatement(sql);
                pstmt.setInt(1, id);
                result = pstmt.executeQuery();
                if (result.next()) {
                    User searchedUser = new User(result.getInt("users_id"));

                    ParkingLot searchedParkingLot = new ParkingLot(result.getInt("parking_lots_id"));

                    searchedResult = new ParkingTicket(
                            result.getInt("id"),
                            searchedUser,
                            searchedParkingLot,
                            result.getString("slot_number"),
                            result.getDouble("ticket_price"),
                            result.getTimestamp("reservation_date"),
                            result.getTimestamp("entry_date"),
                            result.getTimestamp("exit_date")
                    );
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ParkingTicket.class.getName()).log(Level.SEVERE, null, ex);
        }
        return searchedResult;
    }

    public ArrayList<Object> FindOccupiedSlot(Timestamp reservationDate, int parkingLotsID) {
        ArrayList<Object> output = new ArrayList<>();
        String sql = "SELECT (SELECT capacity FROM parking_lots WHERE id=?) as capacity, slot_number "
                + "FROM parking_tickets WHERE reservation_date=? AND parking_lots_id=?";
        try {
            if (!Model.conn.isClosed()) {
                PreparedStatement pstmt = Model.conn.prepareStatement(sql);
                pstmt.setInt(1, parkingLotsID);
                pstmt.setTimestamp(2, reservationDate);
                pstmt.setInt(3, parkingLotsID);
                result = pstmt.executeQuery();

                if (result.next()) {
                    output.add(result.getInt("capacity"));
                    output.add(result.getString("slot_number"));
                    while (result.next()) {
                        String occupiedSlot = result.getString("slot_number");
                        output.add(occupiedSlot);
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ParkingTicket.class.getName()).log(Level.SEVERE, null, ex);
        }
        return output;
    }

    public ArrayList<ParkingTicket> SelectUsersTicket(int userID) {
        ArrayList<ParkingTicket> listParkingTickets = new ArrayList<>();
        String sql = "SELECT pt.*, pl.name AS parking_lot_name, l.name AS location_name "
                + "FROM parking_tickets pt "
                + "INNER JOIN parking_lots pl ON pt.parking_lots_id = pl.id "
                + "INNER JOIN locations l ON pl.locations_id = l.id "
                + "WHERE pt.users_id = ?";
        try {
            PreparedStatement pstmt = Model.conn.prepareStatement(sql);
            pstmt.setInt(1, userID);
            result = pstmt.executeQuery();

            while (result.next()) {
                User rowUser = new User(result.getInt("users_id"));

                ParkingLot rowParkingLot = new ParkingLot(result.getString("parking_lot_name"));

                rowParkingLot.setLocation(new Location(result.getString("location_name")));

                ParkingTicket parkingTicket = new ParkingTicket(
                        result.getInt("id"),
                        rowUser,
                        rowParkingLot,
                        result.getString("slot_number"),
                        result.getDouble("ticket_price"),
                        result.getTimestamp("reservation_date"),
                        result.getTimestamp("entry_date"),
                        result.getTimestamp("exit_date")
                );

                listParkingTickets.add(parkingTicket);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ParkingTicket.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listParkingTickets;
    }

    @Override
    public String toString() {
        String entry_date;
        if (this.getEntryDate() != null) {
            entry_date = this.getEntryDate().toString();
        } else {
            entry_date = null;
        }

        String exit_date;
        if (this.getExitDate() != null) {
            exit_date = this.getExitDate().toString();
        } else {
            exit_date = null;
        }
        return this.getId() + ","
                + this.getUser().getId() + ","
                + this.getParkingLot().getName() + ","
                + this.getParkingLot().getLocation().getName() + ","
                + this.getSlotNumber() + ","
                + this.getReservationDate().toString() + ","
                + entry_date + ","
                + exit_date;
    }
    // </editor-fold>
}
