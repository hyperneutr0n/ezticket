/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ezeventparking.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author user
 */
public abstract class Model {

    protected static Connection conn;
    protected Statement stmt;
    protected ResultSet result;

    public Model() {
        this.conn = this._getConnection();
        this.stmt = null;
        this.result = null;
    }

    public Connection _getConnection() {
        if (Model.conn == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                return DriverManager.getConnection("jdbc:mysql://localhost/ezticket", "root", "");
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
        return Model.conn;
    }

    public abstract int InsertData();

    public abstract int UpdateData();

    public abstract int DeleteData();
}
