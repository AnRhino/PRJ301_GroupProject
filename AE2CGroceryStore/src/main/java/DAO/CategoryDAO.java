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
        String query = "SELECT CategoryID, CategoryName\n"
                + "FROM Categories";

        try {
            PreparedStatement ps = this.getConnection().prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Category(rs.getInt(1), rs.getString(2)));
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

        Category cate = null;
        String query = "SELECT c.CategoryID, c.CategoryName\n"
                + "FROM [dbo].[Categories] c\n"
                + "WHERE c.CategoryID = ?";
        Object[] params = {categoryID};
        
        try {
            ResultSet rs = execSelectQuery(query, params);
            
            while (rs.next()) {
                cate = new Category(rs.getInt(1), rs.getString(2));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);

        }

        return cate;
    }
}
