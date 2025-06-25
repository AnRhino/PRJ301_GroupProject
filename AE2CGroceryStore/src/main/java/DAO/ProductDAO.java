/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

                Category Category = new Category(rs.getInt(6), rs.getString(7));
                Product pro = new Product(rs.getInt("ProductID"), rs.getString(2), rs.getString(3), rs.getInt("Quantity"), rs.getInt("Price"), Category);
                list.add(pro);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);

        }
        return list;
    }

    public int create(String productCore, String productName, int quantity, double price, int categoryId) {
        int check = 0;
        try {
            String sql = "insert into Products (ProductCode,ProductName,Quantity,Price,CategoryID)\n"
                    + "values(?,?,?,?,?)";
            PreparedStatement ps = this.getConnection().prepareStatement(sql);
            ps.setObject(1, productCore);
            ps.setObject(2, productName);
            ps.setObject(3, quantity);
            ps.setObject(4, price);
            ps.setObject(5, categoryId);
            ps.executeQuery();

        } catch (SQLException ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return 0;
    }

    public int edit(int prouductId, String productCode, String productName, int quantity, double price, int cateID) {
        try {
            String query = "update  Products\n"
                    + "set ProductCode = ?, ProductName = ?,Quantity = ?,Price= ?, CategoryID = ?\n"
                    + "where ProductID = ?";

            PreparedStatement ps = this.getConnection().prepareStatement(query);
            ps.setObject(1, productCode);
            ps.setObject(2, productName);
            ps.setObject(3, quantity);
            ps.setObject(4, price);
            ps.setObject(5, cateID);
            ps.setObject(6, prouductId);
            ps.executeUpdate();

            return 0;
        } catch (SQLException ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public Product getById(int ProductID) {
        try {
            String query = "SELECT ProductID, ProductCode, ProductName, Quantity, Price, c.CategoryID, c.CategoryName "
                    + "FROM Products p "
                    + "JOIN Categories c ON p.CategoryID = c.CategoryID "
                    + "WHERE ProductID = ?";

            PreparedStatement ps = this.getConnection().prepareStatement(query);
            ps.setInt(1, ProductID);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Category cat = new Category(rs.getInt(6), rs.getString(7));
                return new Product(rs.getInt(1), rs.getString(2), rs.getString(3),
                        rs.getInt(4), rs.getInt(5), cat);
            } else {
                return null;
            }

        } catch (SQLException ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public int delete(int productId) {
        String query = "delete from Products where ProductID = ? ";
        try {
            PreparedStatement ps = this.getConnection().prepareStatement(query);
            ps.setObject(1, productId);
            return ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;

    }

    /**
     * Lấy sản phẩm theo loại doanh mục người dùng chọn.
     *
     * @param categoryID là loại danh mục người dùng muốn hiện sản phẩm ra.
     *
     * @return danh sách sản phẩm theo loại doanh mục người dùng chọn.
     */
    public List<Product> getProductsByCategory(int categoryID) {
        List<Product> list = new ArrayList<>();
        String query = "SELECT ProductID, ProductCode, ProductName, Quantity, Price, c.CategoryID, c.CategoryName\n"
                + "FROM Products p\n"
                + "JOIN  Categories c on p.CategoryID = c.CategoryID\n"
                + "WHERE c.CategoryID = ?";
        Object[] params = {categoryID};

        try {
            ResultSet rs = execSelectQuery(query, params);

            while (rs.next()) {
                Product pro = new Product(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt("Quantity"), rs.getInt("Price"), new Category(rs.getInt(6), rs.getString(7)));
                list.add(pro);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);

        }

        return list;
    }

    /**
     * Phương thức lấy giá trị trung bình đánh giá sản phẩm của khác hàng.
     *
     * @param productID là sản phẩm muốn lấy đánh giá.
     *
     * @return giá trị trung bình đánh giá của sản phẩm.
     */
    public Double getRateScore(int productID) {
        Double rateScore = null;
        String query = "SELECT\n"
                + "\n"
                + "(SELECT SUM(rv.Rating)\n"
                + "FROM [dbo].[Reviews] rv\n"
                + "JOIN [dbo].[Products] prod\n"
                + "ON prod.ProductID = rv.ProductID\n"
                + "WHERE prod.ProductID = ?)\n"
                + "/\n"
                + "(SELECT COUNT(ReviewID)\n"
                + "FROM [dbo].[Reviews] rv\n"
                + "JOIN [dbo].[Products] prod\n"
                + "ON prod.ProductID = rv.ProductID\n"
                + "WHERE prod.ProductID = ?)\n"
                + "\n"
                + "AS RESULT";
        Object[] params = {productID, productID};

        try {
            ResultSet rs = execSelectQuery(query, params);

            while (rs.next()) {
                rateScore = rs.getDouble(1);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return rateScore;
    }

    /**
     * Lấy số lượng trong kho của sản phẩm.
     *
     * @param productID là id của sản phẩm.
     *
     * @return số lượng của sản phẩm đó.
     */
    public int getMaxQuantity(int productID) {

        String query = "SELECT p.Quantity\n"
                + "FROM [dbo].[Products] p \n"
                + "WHERE p.ProductID = ?";
        Object[] params = {productID};

        try {
            ResultSet rs = execSelectQuery(query, params);

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return 0;
    }

    public String checkCharacter(String input) {
        String check = "Invalid";
        if (input.isEmpty()) {
            input = check;
        }

        for (int i = 0; i < input.length(); i++) {
            if (!((input.charAt(i) >= 'a' && input.charAt(i) <= 'z')
                    || (input.charAt(i) >= 'A' && input.charAt(i) <= 'Z') || (input.charAt(i) >= '0' && input.charAt(i) <= '9'))) {
                input = check;
                break;

            }
        }

        return input;
    }

    public String CheckNumber(String input) {

        String check = "Invalid";
        if (input.isEmpty()) {
            input = check;
        }

        for (int i = 0; i < input.length() - 1; i++) {
            if (input.charAt(i) < '0' || input.charAt(i) > '9') {
                input = check;
            }
        }

        return input;

    }

    public String ProCode(String input) {

        List<Product> list = new ArrayList<>();
        String check = "Invalid";
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
            if (input.isEmpty()) {
                 input = check;
            }

            for (int i = 0; i < input.length(); i++) {
                if (!((input.charAt(i) >= 'a' && input.charAt(i) <= 'z')
                        || (input.charAt(i) >= 'A' && input.charAt(i) <= 'Z') || (input.charAt(i) >= '0' && input.charAt(i) <= '9'))) {
                    input = check;
                    break;

                }
            }
            for (Product pr : list) {

                if (input.equalsIgnoreCase(pr.getProductCode())) {

                    input = check;

                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);

        }

        return input;
    }

}
