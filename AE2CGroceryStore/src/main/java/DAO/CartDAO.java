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
     * Get all the category from the database.
     *
     * @return list of all category.
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
}
