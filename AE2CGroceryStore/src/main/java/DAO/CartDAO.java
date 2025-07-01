/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Cart;
import model.Category;
import model.Product;
import model.User;

/**
 *
 * @author Vu Minh Khang - CE191371
 */
public class CartDAO extends dbconnect.DBContext {

    /**
     * Lấy toàn bộ cart của người dùng trong cơ sở dữ liệu.
     *
     * @return danh sách toàn bộ cart hiện tại.
     */
    public List<Cart> getAll() {

        List<Cart> list = new ArrayList<>();
        String query = "SELECT c.CartItemID, u.UserID, u.Username, prod.ProductID, c.Quantity\n"
                + "FROM [dbo].[Carts] c\n"
                + "JOIN [dbo].[Users] u\n"
                + "ON u.UserID = c.UserID\n"
                + "JOIN [dbo].[Products] prod\n"
                + "ON prod.ProductID = c.ProductID";

        try {

            ResultSet rs = execSelectQuery(query, null);

            while (rs.next()) {
                list.add(new Cart(rs.getInt(1), new User(rs.getInt(2), rs.getString(3)), new Product(rs.getInt(4)), rs.getInt(5)));
            }

        } catch (SQLException ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return list;
    }

    /**
     * Lấy toàn bộ cart của người dùng trong cơ sở dữ liệu theo id của người
     * dùng.
     *
     * @param userID là id cùa người dùng.
     * 
     * @return danh sách toàn bộ cart hiện tại của người dùng.
     */
    public List<Cart> getByUserID(int userID) {

        List<Cart> list = new ArrayList<>();
        String query = "SELECT c.CartItemID, u.UserID, u.Username, p.ProductID, c.Quantity\n"
                + "FROM [dbo].[Carts] c\n"
                + "JOIN [dbo].[Users] u\n"
                + "ON u.UserID = c.UserID\n"
                + "JOIN [dbo].[Products] p\n"
                + "ON p.ProductID = c.ProductID\n"
                + "WHERE p.IsHidden = 1\n"
                + "AND u.UserID = ?";
        Object[] params = {userID};

        try {

            ResultSet rs = execSelectQuery(query, params);

            while (rs.next()) {
                list.add(new Cart(rs.getInt(1), new User(rs.getInt(2), rs.getString(3)), new Product(rs.getInt(4)), rs.getInt(5)));
            }

        } catch (SQLException ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return list;
    }
}
