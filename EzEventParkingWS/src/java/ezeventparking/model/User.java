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
    private int id;
    private String name;
    private String email;
    private String username;
    private String password;

    public User() {}
    
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
    
    public static ArrayList<User> SelectData() {
        ArrayList<User> listUsers = new ArrayList<>();
        String sql = "SELECT * FROM users";
        try {
            Statement stmt = Model.conn.createStatement();
            ResultSet result = stmt.executeQuery(sql);
            
            while(result.next()) {
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
    public void InsertData() {
        String sql = "INSERT INTO users (name, email, username, password) VALUES (?, ?, ?, ?)";
        try {
            if (!Model.conn.isClosed()) {
                PreparedStatement pstmt = Model.conn.prepareStatement(sql);
                pstmt.setString(1, this.getName());
                pstmt.setString(2, this.getEmail());
                pstmt.setString(3, this.getUsername());
                pstmt.setString(4, this.getPassword());
                pstmt.executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void UpdateData() {
        String sql = "UPDATE users SET name=?, email=?, username=? WHERE id=?";
        try {
            if (!Model.conn.isClosed()) {
                PreparedStatement pstmt = Model.conn.prepareStatement(sql);
                pstmt.setInt(4, this.getId());
                pstmt.setString(1, this.getName());
                pstmt.setString(2, this.getEmail());
                pstmt.setString(3, this.getUsername());
                pstmt.executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void DeleteData() {
        String sql = "DELETE FROM users WHERE id=?";
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
    
    public static User FindUser(String username) {
        String sql = "SELECT * FROM users WHERE username=?";
        User searchResult = null;
        try {
            if (!Model.conn.isClosed()) {
                PreparedStatement pstmt = Model.conn.prepareStatement(sql);
                pstmt.setString(1, username);
                ResultSet result = pstmt.executeQuery();
                if(result.next()) {
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
    
    public static boolean CheckLogin(String username, String password) {
        String sql = "SELECT password FROM users WHERE username=?";
        boolean loginSuccess = false;
        try {
            if (!Model.conn.isClosed()) {
                PreparedStatement pstmt = Model.conn.prepareStatement(sql);
                pstmt.setString(1, username);
                ResultSet result = pstmt.executeQuery();
                result.next();
                if (password.equals(result.getString("password"))) {
                    loginSuccess = true;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
        return loginSuccess;
    }
}
