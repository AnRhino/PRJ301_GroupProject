/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.User;

/**
 *
 * @author Dinh Cong Phuc - CE190770
 */
public class UserDAO extends dbconnect.DBContext {

    public User login(String username, String password) {
        try {
            String hashPwd = hashMd5(password);

            String query = "select UserID, Username, Password\n"
                    + "from users\n"
                    + "where Username = ?\n"
                    + "and password = ?";
            Object[] params = {username, hashPwd};
            ResultSet rs = execSelectQuery(query, params);
            if (rs.next()) {
                return new User(rs.getInt(1), rs.getString(2), null);
            } else {
                return null;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public User register(String username, String password, String fullName, String email, String roleId) { // Not Done
        try {
            String hashPwd = hashMd5(password);

            String query = "select Username, Email,\n"
                    + "from users\n"
                    + "where Username = ?\n"
                    + "and password = ?";
            Object[] params = {username, hashPwd};
            ResultSet rs = execSelectQuery(query, params);
            if (rs.next()) {
                // check if username and email already exist
            } else {
                // create
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
