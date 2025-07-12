/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package validate;

import DAO.CategoryDAO;
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
 * @author Phan Duc Tho - CE191246
 */

public class ProductValidation {

    public List<String> checkProductCode(String input, List<Product> products) {
        List<String> msg = new ArrayList<>();
        if (input.trim().isEmpty()) {
            msg.add("Product input must not be blank");
            return msg;
        }

        for (int i = 0; i < input.length(); i++) {
            if (!((input.charAt(i) >= 'a' && input.charAt(i) <= 'z')
                    || (input.charAt(i) >= 'A' && input.charAt(i) <= 'Z')
                    || (input.charAt(i) >= '0' && input.charAt(i) <= '9'))) {
                msg.add("Product input must contain only letters and digits");
                return msg;
            }
        }

        for (Product p : products) {
            if (input.equalsIgnoreCase(p.getProductCode())) {
                msg.add("Product input already exists");
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
        int inputNumBer = Integer.parseInt(input);
        if (inputNumBer <= 0) {
            msg.add("Price only positive number");
            return msg;
        }

        return msg;
    }

    public List<String> checkCategoryID(String cateID) {
        List<String> msg = new ArrayList<>();

        if (cateID.trim().isEmpty()) {
            msg.add("Category must not be blank");
            return msg;
        }

        int cateId;
        try {
            cateId = Integer.parseInt(cateID);
        } catch (NumberFormatException e) {
            msg.add("Category must be a valid number");
            return msg;
        }

        CategoryDAO cateDAO = new CategoryDAO();
        List<Category> allCate = cateDAO.getAll();
        boolean found = false;
        for (Category c : allCate) {
            if (c.getCategoryID() == cateId) {
                found = true;
                break;
            }
        }

        if (!found) {
            msg.add("Selected category does not exist");
        }

        return msg;
    }

    public List<String> checkProductCodeEdit(String input, int productId, List<Product> productList) {
        List<String> msg = new ArrayList<>();

        if (input.trim().isEmpty()) {
            msg.add("Product input must not be blank");
            return msg;
        }

        for (int i = 0; i < input.length(); i++) {
            if (!((input.charAt(i) >= 'a' && input.charAt(i) <= 'z')
                    || (input.charAt(i) >= 'A' && input.charAt(i) <= 'Z')
                    || (input.charAt(i) >= '0' && input.charAt(i) <= '9'))) {
                msg.add("Product input must contain only letters and digits");
                return msg;
            }
        }

        for (Product p : productList) {

            if (p.getProductCode().equalsIgnoreCase(input) && p.getProductID() != productId) {
                msg.add("Product input already exists");
                break;
            }
        }

        return msg;
    }

}
