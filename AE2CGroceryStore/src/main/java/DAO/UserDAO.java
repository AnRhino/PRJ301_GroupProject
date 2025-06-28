/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.User;

/**
 *
 * @author Dinh Cong Phuc - CE190770
 */
public class UserDAO extends dbconnect.DBContext {
    
    public boolean authenticate(String username, String password) {
        try {
            String hashPwd = hashMd5(password);
            
            String query = "select Username, Password, RoleID\n"
                    + "from users\n"
                    + "where Username = ?\n"
                    + "and password = ?";
            Object[] params = {username, hashPwd};
            ResultSet rs = execSelectQuery(query, params);
            return rs.next();
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public User getUserByUsername(String username) {
        try {
            String query = "select Username, FullName, Email\n"
                    + "from users\n"
                    + "where Username = ?";
            Object[] params = {username};
            ResultSet rs = execSelectQuery(query, params);
            if (rs.next()) {
                return new User(rs.getString("Username"), rs.getString("FullName"), rs.getString("Email"));
            } else {
                return null;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public User register(String username, String password, String fullName, String email) {
        try {
            String hashPwd = hashMd5(password);
            
            String query = "select UserID, Username, Password, RoleID, Email\n"
                    + "from Users\n"
                    + "where Username = ?\n"
                    + "or Email = ?";
            Object[] params = {username, email};
            ResultSet rs = execSelectQuery(query, params);
            if (rs.next()) {
                // Username or Email already exists
                return null;
            } else {
                // create
                // SQL add new user with ID, username, pwd, email, fullname, RoleID
                String createQuery = "insert into Users (Username, Password, FullName, Email, RoleID) \n"
                        + "values (?, ?, ?, ?, ?)";
                Object[] createParams = {username, hashPwd, fullName, email, 0};
                execQuery(createQuery, createParams);
                // return new user
                return new User(username, null, 0);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    // method ma hoa pwd -> md5
    private String hashMd5(String raw) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] mess = md.digest(raw.getBytes());
            
            StringBuilder sb = new StringBuilder();
            for (byte b : mess) {
                sb.append(String.format("%02x", b));
            }
            
            return sb.toString();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        }
    }
}
