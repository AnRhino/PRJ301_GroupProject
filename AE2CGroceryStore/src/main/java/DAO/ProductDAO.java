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
                Product pro = new Product(rs.getInt("ProductID"), rs.getString(2), rs.getString(3), rs.getInt("Quantity"), rs.getDouble("Price"), Category);
                list.add(pro);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);

        }
        return list;
    }

    public int create(String productCore, String productName, int quantity, double price, String categoryName) {
        int nextCategoryId = 1;

        try {

            String insertCate = "INSERT INTO Categories (CategoryName) VALUES (?)";
            PreparedStatement ps1 = this.getConnection().prepareStatement(insertCate, Statement.RETURN_GENERATED_KEYS);
            ps1.setString(1, categoryName);
            ps1.executeUpdate();

            ResultSet rs = ps1.getGeneratedKeys();
            if (rs.next()) {
                nextCategoryId = rs.getInt(1);
            }

            String insertPro = "INSERT INTO Products (ProductCode, ProductName, Quantity, Price, CategoryID) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps2 = this.getConnection().prepareStatement(insertPro);
            ps2.setString(1, productCore);
            ps2.setString(2, productName);
            ps2.setInt(3, quantity);
            ps2.setDouble(4, price);
            ps2.setInt(5, nextCategoryId);

            return ps2.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return 0;
    }

    public int edit(String productCore, String productName, int quantity, double price, String categoryName) {
        String query = "update  Products\n"
                + "set ProductCode = ?, ProductName = ? ,Quantity=?,Price=?\n"
                + "from Products pr \n"
                + "where CategoryID = ?\n"
                + "\n"
                + "update Categories\n"
                + "set CategoryName = ?\n"
                + "where CategoryID = ?";

        return 0;
    }

    public Product getById(int productId) {

        try {
            String query = "select ProductID,ProductCode,ProductName,Quantity,Price,cat.CategoryID,cat.CategoryName\n"
                    + "from Products pr\n"
                    + "join Categories cat on pr.CategoryID = cat.CategoryID\n"
                    + "where ProductID = ?";
            PreparedStatement ps = this.getConnection().prepareStatement(query);
            ps.setInt(1, productId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Category cat = new Category(rs.getInt(6), rs.getString(7));
                return new Product(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getDouble(5), cat);
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
                Product pro = new Product(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt("Quantity"), rs.getDouble("Price"), new Category(rs.getInt(6), rs.getString(7)));
                list.add(pro);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);

        }

        return list;
    }

    public Double getRateSocre(int productID) {
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
}
