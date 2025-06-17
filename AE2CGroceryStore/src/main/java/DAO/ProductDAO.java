/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Category;
import model.Product;

/**
 *
 * @author PC
 */
public class ProductDAO extends dbconnect.DBContext {

    public List<Product> getAll() {
        List<Product> list = new ArrayList<>();
        try {
            String query = "select ProductID, ProductCode, ProductName, Quantity, Price, c.CategoryID, c.CategoryName\n"
                    + "from Products p \n"
                    + "join  Categories c on p.CategoryID = c.CategoryID";
            PreparedStatement ps = this.getConnection().prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
               
                Category Category = new Category(rs.getInt(6),rs.getString(7));
                Product pro = new Product(rs.getInt("ProductID"),rs.getString(2), rs.getString(3), rs.getInt("Quantity"), rs.getInt("Price"), Category);
                list.add(pro);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);

        }
        return list;
    }

}
