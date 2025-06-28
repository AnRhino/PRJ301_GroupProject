/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package validate;

import DAO.ProductDAO;
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
public class ProductValidation extends dbconnect.DBContext {

    public List<Product> getAll() {
        List<Product> list = new ArrayList<>();
        try {
            String query = "select ProductID, ProductCode, ProductName, Quantity, Price, c.CategoryID, c.CategoryName\n"
                    + "from Products p \n"
                    + "join  Categories c on p.CategoryID = c.CategoryID";
            PreparedStatement ps = this.getConnection().prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                Category Category = new Category(rs.getInt(6), rs.getString(7));
                Product pro = new Product(rs.getInt("ProductID"), rs.getString(2), rs.getString(3), rs.getInt("Quantity"), rs.getInt("Price"), Category);
                list.add(pro);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);

        }
        return list;
    }

    public List<String> checkProductCode(String input) {
        List<String> msg = new ArrayList<>();
        if (input == null || input.trim().isEmpty()) {
            msg.add("Product code not is blank");
            return msg;
        }

        for (int i = 0; i < input.length(); i++) {
            if (!((input.charAt(i) >= 'a' && input.charAt(i) <= 'z')
                    || (input.charAt(i) >= 'A' && input.charAt(i) <= 'Z') || (input.charAt(i) >= '0' && input.charAt(i) <= '9'))) {
                msg.add("Product code only character and number");
                return msg;
            }
        }

        for (Product p : getAll()) {
            if (input.equalsIgnoreCase(p.getProductCode())) {
                msg.add("Product code already exists");
                return msg;
            }
        }

        return msg;
    }

    public List<String> checkProductName(String input) {
        List<String> msg = new ArrayList<>();
        if (input == null || input.trim().isEmpty()) {
            msg.add("Product name not is blank");
            return msg;
        }

        for (int i = 0; i < input.length(); i++) {
            if (!((input.charAt(i) >= 'a' && input.charAt(i) <= 'z')
                    || (input.charAt(i) >= 'A' && input.charAt(i) <= 'Z'))) {
                msg.add("Product name only character and number");
                return msg;
            }
        }

        return msg;
    }

    public List<String> checkQuantity(String input) {
        List<String> msg = new ArrayList<>();
        if (input == null || input.trim().isEmpty()) {
            msg.add("Quantity not is blank");
            return msg;
        }
        for (int i = 0; i < input.length(); i++) {
            if (!((input.charAt(i) >= '0' && input.charAt(i) <= '9'))) {
                msg.add("Quantity only positive number");
                return msg;
            }
        }

        return msg;
    }

    public List<String> checkPrice(String input) {
        List<String> msg = new ArrayList<>();
        if (input == null || input.trim().isEmpty()) {
            msg.add("Price not is blank");
            return msg;
        }

        for (int i = 0; i < input.length(); i++) {
            if (!((input.charAt(i) >= '0' && input.charAt(i) <= '9'))) {
                msg.add("Price only positive number");
                return msg;
            }
        }

        return msg;
    }
}
