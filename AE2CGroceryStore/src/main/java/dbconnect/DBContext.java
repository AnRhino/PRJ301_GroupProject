/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dbconnect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nguyen Ho Phuoc An - CE190747
 */
public class DBContext {

    private Connection conn;
    private final String DB_URL = "jdbc:sqlserver://127.0.0.1:1433;databaseName=GroceryStore;encrypt=false";
    private final String DB_USER = "sa";
    private final String DB_PWD = "123456"; //fill the password

    public DBContext() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            this.conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PWD);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Connection getConnection() {
        return conn;
    }

    /**
     * Execute INSERT, UPDATE, DELETE query
     *
     * @param query
     * @param params
     * @return the row count for SQL Data Manipulation Language (DML)
     * @throws SQLException
     */
    protected int execQuery(String query, Object[] params) throws SQLException {
        PreparedStatement ps = conn.prepareStatement(query);

        if (params != null) {
            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }
        }
        
        return ps.executeUpdate();
    }

    /**
     * Execute SELECT query
     *
     * @param query
     * @param params
     * @return
     * @throws SQLException
     */
    protected ResultSet execSelectQuery(String query, Object[] params) throws SQLException {
        PreparedStatement ps = conn.prepareStatement(query);

        if (params != null) {
            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }
        }

        return ps.executeQuery();
    }

    /**
     * Execute SELECT query
     *
     * @param query
     * @return
     * @throws SQLException
     */
    protected ResultSet execSelectQuery(String query) throws SQLException {
        return execSelectQuery(query, null);
    }
}
