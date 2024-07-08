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
public class User extends Model {

    // <editor-fold defaultstate="collapsed" desc="Data Members">
    private int id;
    private String name;
    private String email;
    private String username;
    private String password;
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Constructors">
    public User() {
    }

    public User(int id) {
        this.id = id;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(String name, String email, String username, String password) {
        this.name = name;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public User(int id, String name, String email, String username, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.username = username;
        this.password = password;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Methods">
    public ArrayList<User> SelectData() {
        ArrayList<User> listUsers = new ArrayList<>();
        String sql = "SELECT * FROM users";
        try {
            stmt = Model.conn.createStatement();
            result = stmt.executeQuery(sql);

            while (result.next()) {
                User user = new User(
                        result.getInt("id"),
                        result.getString("name"),
                        result.getString("email"),
                        result.getString("username"),
                        result.getString("password")
                );
                listUsers.add(user);
            }
        } catch (SQLException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listUsers;
    }

    @Override
    public int InsertData() {
        String sql = "INSERT INTO users (name, email, username, password) VALUES (?, ?, ?, ?)";
        int rowEffected = 0;
        try {
            if (!Model.conn.isClosed()) {
                PreparedStatement pstmt = Model.conn.prepareStatement(sql);
                pstmt.setString(1, this.getName());
                pstmt.setString(2, this.getEmail());
                pstmt.setString(3, this.getUsername());
                pstmt.setString(4, this.getPassword());
                rowEffected = pstmt.executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rowEffected;
    }

    @Override
    public int UpdateData() {
        String sql = "UPDATE users SET name=?, email=?, username=? WHERE id=?";
        int rowEffected = 0;
        try {
            if (!Model.conn.isClosed()) {
                PreparedStatement pstmt = Model.conn.prepareStatement(sql);
                pstmt.setInt(4, this.getId());
                pstmt.setString(1, this.getName());
                pstmt.setString(2, this.getEmail());
                pstmt.setString(3, this.getUsername());
                rowEffected = pstmt.executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rowEffected;
    }

    public User FindUser(String username) {
        String sql = "SELECT * FROM users WHERE username=?";
        User searchResult = null;
        try {
            if (!Model.conn.isClosed()) {
                PreparedStatement pstmt = Model.conn.prepareStatement(sql);
                pstmt.setString(1, username);
                result = pstmt.executeQuery();
                if (result.next()) {
                    searchResult = new User(
                            result.getInt("id"),
                            result.getString("name"),
                            result.getString("email"),
                            result.getString("username"),
                            ""
                    );
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
        return searchResult;
    }

    public User CheckLogin(String username, String password) {
        String sql = "SELECT * FROM users WHERE username=?";
        User user = null;
        try {
            if (!Model.conn.isClosed()) {
                PreparedStatement pstmt = Model.conn.prepareStatement(sql);
                pstmt.setString(1, username);
                result = pstmt.executeQuery();
                result.next();
                if (password.equals(result.getString("password"))) {
                    user = new User(
                            result.getInt("id"),
                            result.getString("name"),
                            result.getString("email"),
                            result.getString("username"),
                            ""
                    );
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
        return user;
    }
    // </editor-fold>
}
