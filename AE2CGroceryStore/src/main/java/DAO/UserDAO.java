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
                    + "and Password = ?";
            Object[] params = {username, hashPwd};
            ResultSet rs = execSelectQuery(query, params);
            return rs.next();
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    /**
     * Nhận vào Username để lấy ra thông tin người dùng
     *
     * @param username
     * @return
     */
    public User getUserByUsername(String username) {
        try {
            String query = "select UserID, Username, FullName, Email, RoleID, Password, ImagePath\n"
                    + "from users\n"
                    + "where Username = ?";
            Object[] params = {username};
            ResultSet rs = execSelectQuery(query, params);
            if (rs.next()) {
                return new User(rs.getInt("UserID"), rs.getString("Username"), rs.getString("FullName"),
                        rs.getString("Email"), rs.getInt("RoleID"), rs.getString("Password"), rs.getString("ImagePath"));
            } else {
                return null;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Nhận vào fullname, email, username để cập nhật email hoặc fullname
     *
     * @param fullName
     * @param email
     * @param username
     * @return
     */
    public int updateProfile(String fullName, String email, String username) {
        try {
            String query = "update Users set FullName = ?, email = ? where Username = ?";
            Object[] params = {fullName, email, username};
            return execQuery(query, params);
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    /**
     * Cập nhật password
     *
     * @param username
     * @param password
     * @return
     */
    public int updatePassword(String username, String password) {
        try {
            String hashPwd = hashMd5(password);

            String query = "update Users set Password = ? where Username = ?";
            Object[] params = {hashPwd, username};
            return execQuery(query, params);
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
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
                String createQuery = "insert into Users (Username, Password, FullName, Email, RoleID, ImagePath) \n"
                        + "values (?, ?, ?, ?, ?, 'users/0.png')";
                Object[] createParams = {username, hashPwd, fullName, email, 0};
                execQuery(createQuery, createParams);
                // return new user
                return new User(username, password, 0, fullName, email);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    // method ma hoa pwd -> md5
    public String hashMd5(String raw) {
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

    /**
     * Lấy tất cả id của user từ cơ sở dữ liệu.
     *
     * @return danh sách chứa tất cả id của người dùng trong cơ sở dữ liệu.
     */
    public List<User> getAllUserID() {

        List<User> list = new ArrayList<>();

        try {
            String query = "SELECT u.UserID\n"
                    + "FROM [dbo].[Users] u;";

            ResultSet rs = execSelectQuery(query);

            while (rs.next()) {
                list.add(new User(rs.getInt(1)));
            }

        } catch (SQLException ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return list;
    }

    /**
     * Đặt đường dẫn của ảnh người dùng trong cơ sở dữ liệu.
     *
     * @param userID là id của người dùng.
     * @param url là đường dẫn của ảnh.
     *
     * @return 0 nếu đặt đường dẫn thất bại. Khác 0 nếu thành công.
     */
    public int setImgUrl(int userID, String url) {

        try {
            String query = "UPDATE [dbo].[Users]\n"
                    + "SET ImagePath = ?\n"
                    + "WHERE UserID= ?";
            Object[] params = {url, userID};

            return execQuery(query, params);

        } catch (SQLException ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return 0;
    }
}
