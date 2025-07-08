/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Category;
import model.Product;

/**
 *
 * @author Vu Minh Khang - CE191371
 */
public class CategoryDAO extends dbconnect.DBContext {

    /**
     * Get all the category from the database.
     *
     * @return list of all category.
     */
    public List<Category> getAll() {

        List<Category> list = new ArrayList<>();
        String query = "SELECT CategoryID, CategoryName, IsHidden, ImagePath\n"
                + "FROM Categories";

        try {
            ResultSet rs = execSelectQuery(query, null);

            while (rs.next()) {
                list.add(new Category(rs.getInt(1), rs.getString(2), rs.getString(4), rs.getBoolean(3)));
            }

        } catch (SQLException ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return list;
    }
    
    /**
     * Lấy tất cả danh mục khả thi trong cơ sở dữ liệu.
     *
     * @return danh sách các danh mục khả dụng.
     */
    public List<Category> getAllCategoryAvailable() {

        List<Category> list = new ArrayList<>();
        String query = "SELECT CategoryID, CategoryName, IsHidden, ImagePath\n"
                + "FROM Categories"
                + "WHERE IsHidden = 0;";

        try {
            ResultSet rs = execSelectQuery(query, null);

            while (rs.next()) {
                list.add(new Category(rs.getInt(1), rs.getString(2), rs.getString(4), rs.getBoolean(3)));
            }

        } catch (SQLException ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return list;
    }

    /**
     * Lấy danh mục dựa trên id được cho.
     *
     * @param categoryID là id được truyền vào.
     *
     * @return 1 object category với id khớp với id truyền vào.
     */
    public Category getOneByID(int categoryID) {

        Category category = null;
        String query = "SELECT c.CategoryID, c.CategoryName, c.IsHidden, c.ImagePath\n"
                + "FROM [dbo].[Categories] c\n"
                + "WHERE c.CategoryID = ?";
        Object[] params = {categoryID};

        try {

            ResultSet rs = execSelectQuery(query, params);

            while (rs.next()) {
                category = new Category(rs.getInt(1), rs.getString(2), rs.getString(4), rs.getBoolean(3));
            }

        } catch (SQLException ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return category;
    }

    /**
     * Lấy danh mục dựa trên id của sản phẩm.
     *
     * @param productID là id của sản phẩm muốn tìm danh mục.
     *
     * @return danh mục khớp với id truyền vào.
     */
    public Category getCategoryByProductID(int productID) {

        Category cate = null;
        String query = "SELECT c.CategoryID, c.CategoryName, c.IsHidden\n"
                + "FROM [dbo].[Categories] c\n"
                + "JOIN [dbo].[Products] p\n"
                + "ON p.CategoryID = c.CategoryID\n"
                + "WHERE p.ProductID = ?";
        Object[] params = {productID};

        try {
            ResultSet rs = execSelectQuery(query, params);

            while (rs.next()) {
                cate = new Category(rs.getInt(1), rs.getString(2), rs.getBoolean(3));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return cate;
    }

    public int getMaxId() {
        try {
            String getMaxIdQuery = "SELECT MAX(CategoryID) FROM Categories";
            PreparedStatement maxIdPs = this.getConnection().prepareStatement(getMaxIdQuery);
            ResultSet maxIdRs = maxIdPs.executeQuery();
            if (maxIdRs.next()) {
                return maxIdRs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    public int getProductCount(int categoryID) {
        try {
            String countQuery = "SELECT c.CategoryID, c.CategoryName, COUNT(p.ProductID) AS ProductCount\n"
                    + "FROM Categories c \n"
                    + "LEFT JOIN Products p ON c.CategoryID = p.CategoryID\n"
                    + "WHERE c.CategoryID = ? \n"
                    + "GROUP BY c.CategoryID, c.CategoryName;";
            PreparedStatement psCount = this.getConnection().prepareStatement(countQuery);
            psCount.setObject(1, categoryID);
            ResultSet countRs = psCount.executeQuery();
            if (countRs.next()) {
                return countRs.getInt(3);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    public int create(String categoryName, int isHidden, String coverImg) {
        try {

            String createQuery = "INSERT INTO Categories VALUES (?, ?, ?)";
            PreparedStatement createPs = this.getConnection().prepareStatement(createQuery);
            createPs.setObject(1, categoryName);
            createPs.setObject(2, isHidden);
            createPs.setObject(3, coverImg);

            return createPs.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    public int updateCategoryName(int categoryID, String categoryName) {
        try {
            String updateQuery = "UPDATE Categories\n"
                    + "SET CategoryName = ?\n"
                    + "WHERE CategoryID = ?;";
            PreparedStatement updatePs = this.getConnection().prepareStatement(updateQuery);
            updatePs.setObject(1, categoryName);
            updatePs.setObject(2, categoryID);
            return updatePs.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    public int updateHidden(int categoryID, int isHidden) {
        try {
            String updateQuery = "UPDATE Categories\n"
                    + "SET IsHidden = ?\n"
                    + "WHERE CategoryID = ?;";
            PreparedStatement updatePs = this.getConnection().prepareStatement(updateQuery);
            updatePs.setObject(1, isHidden);
            updatePs.setObject(2, categoryID);
            return updatePs.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    public int updateCoverImg(int categoryID, String coverImg) {
        try {
            String updateQuery = "UPDATE Categories\n"
                    + "SET ImagePath = ?\n"
                    + "WHERE CategoryID = ?;";
            PreparedStatement updatePs = this.getConnection().prepareStatement(updateQuery);
            updatePs.setObject(1, coverImg);
            updatePs.setObject(2, categoryID);
            return updatePs.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    public int deleteCategoryById(int categoryID) {
        try {
            String deleteQuery = "DELETE FROM Categories WHERE CategoryID = ?";
            PreparedStatement deletePs = this.getConnection().prepareStatement(deleteQuery);
            deletePs.setObject(1, categoryID);
            return deletePs.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

}
