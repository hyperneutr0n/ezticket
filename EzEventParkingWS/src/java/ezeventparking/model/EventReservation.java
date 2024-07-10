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
public class EventReservation extends Model {

    // <editor-fold defaultstate="collapsed" desc="Data Members">
    private User user;
    private Event event;
    private Timestamp reservationDate;
    private Timestamp claimDate;
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Constructors">
    public EventReservation() {
    }

    public EventReservation(User user, Event event, Timestamp reservationDate) {
        this.user = user;
        this.event = event;
        this.reservationDate = reservationDate;
    }

    public EventReservation(User user, Event event, Timestamp reservationDate, Timestamp claimDate) {
        this.user = user;
        this.event = event;
        this.reservationDate = reservationDate;
        this.claimDate = claimDate;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Properties">
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Timestamp getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(Timestamp reservationDate) {
        this.reservationDate = reservationDate;
    }

    public Timestamp getClaimDate() {
        return claimDate;
    }

    public void setClaimDate(Timestamp claimDate) {
        this.claimDate = claimDate;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Methods">
    public ArrayList<EventReservation> SelectData() {
        ArrayList<EventReservation> listEventReservations = new ArrayList<>();
        String sql = "SELECT er.*, e.name as 'event_name' FROM event_reservations er INNER JOIN events e ON e.id = er.events_id";
        try {
            stmt = Model.conn.createStatement();
            result = stmt.executeQuery(sql);

            while (result.next()) {
                User rowUser = new User(result.getInt("users_id"));

                Event rowEvent = new Event(result.getInt("events_id"), result.getString("event_name"));

                EventReservation eventReservation = new EventReservation(
                        rowUser,
                        rowEvent,
                        result.getTimestamp("reservation_date"),
                        result.getTimestamp("claim_date")
                );

                listEventReservations.add(eventReservation);
            }
        } catch (SQLException ex) {
            Logger.getLogger(EventReservation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listEventReservations;
    }

    @Override
    public int InsertData() {
        String sql = "INSERT INTO event_reservations (users_id, events_id, reservation_date) VALUES (?,?,?)";
        int rowEffected = 0;
        try {
            if (!Model.conn.isClosed()) {
                PreparedStatement pstmt = Model.conn.prepareStatement(sql);
                pstmt.setInt(1, this.getUser().getId());
                pstmt.setInt(2, this.getEvent().getId());
                pstmt.setTimestamp(3, this.getReservationDate());
                rowEffected = pstmt.executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(EventReservation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rowEffected;
    }

    @Override
    public int UpdateData() {
        String sql = "UPDATE event_reservations SET claim_date=? WHERE users_id=? AND events_id=?";
        int rowEffected = 0;
        try {
            if (!Model.conn.isClosed()) {
                PreparedStatement pstmt = Model.conn.prepareStatement(sql);
                pstmt.setInt(2, this.getUser().getId());
                pstmt.setInt(3, this.getEvent().getId());
                pstmt.setTimestamp(1, this.getClaimDate());
                rowEffected = pstmt.executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(EventReservation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rowEffected;
    }

    @Override
    public int DeleteData() {
        String sql = "DELETE FROM event_reservations WHERE users_id=? AND events_id=?";
        int rowEffected = 0;
        try {
            if (!Model.conn.isClosed()) {
                PreparedStatement pstmt = Model.conn.prepareStatement(sql);
                pstmt.setInt(1, this.getUser().getId());
                pstmt.setInt(2, this.getEvent().getId());
                rowEffected = pstmt.executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(EventReservation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rowEffected;
    }

    public EventReservation FindEventReservation(int id) {
        String sql = "SELECT * FROM event_reservations WHERE id=?";
        EventReservation searchedResult = null;
        try {
            if (!Model.conn.isClosed()) {
                PreparedStatement pstmt = Model.conn.prepareStatement(sql);
                pstmt.setInt(1, this.getUser().getId());
                result = pstmt.executeQuery();
                if (result.next()) {
                    User searchedUser = new User(result.getInt("users_id"));

                    Event searchedEvent = new Event(result.getInt("events_id"));

                    searchedResult = new EventReservation(
                            searchedUser,
                            searchedEvent,
                            result.getTimestamp("reservation_date"),
                            result.getTimestamp("claim_date")
                    );
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(EventReservation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return searchedResult;
    }

    public ArrayList<EventReservation> SelectUserReservation(int userID) {
        ArrayList<EventReservation> listEventReservations = new ArrayList<>();
        String sql = "SELECT er.*, e.name as 'event_name' FROM event_reservations er INNER JOIN events e ON e.id = er.events_id  WHERE users_id=?";
        try {
            PreparedStatement pstmt = Model.conn.prepareStatement(sql);
            pstmt.setInt(1, userID);
            result = pstmt.executeQuery();

            while (result.next()) {
                User rowUser = new User(result.getInt("users_id"));

                Event rowEvent = new Event(result.getInt("events_id"), result.getString("event_name"));

                EventReservation eventReservation = new EventReservation(
                        rowUser,
                        rowEvent,
                        result.getTimestamp("reservation_date"),
                        result.getTimestamp("claim_date")
                );

                listEventReservations.add(eventReservation);
            }
        } catch (SQLException ex) {
            Logger.getLogger(EventReservation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listEventReservations;
    }

    @Override
    public String toString() {
        String claim_date;
        if (this.getClaimDate() != null) {
            claim_date = this.getClaimDate().toString();
        } else {
            claim_date = null;
        }
        return this.getUser().getId() + "," + this.getEvent().getId() + "," + this.getEvent().getName() + "," + this.getReservationDate().toString() + "," + claim_date;
    }
    // </editor-fold>
}
