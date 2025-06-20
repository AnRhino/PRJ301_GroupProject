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

    public int create(String productCore, String productName, int quantity, int price, String categoryName) {
    int nextCategoryId = -1;

    try {
        // Bước 1: Insert vào Categories và lấy CategoryID tự động
        String insertCate = "INSERT INTO Categories (CategoryName) VALUES (?)";
        PreparedStatement ps1 = this.getConnection().prepareStatement(insertCate, Statement.RETURN_GENERATED_KEYS);
        ps1.setString(1, categoryName);
        ps1.executeUpdate();

        ResultSet rs = ps1.getGeneratedKeys();
        if (rs.next()) {
            nextCategoryId = rs.getInt(1);
        }

        // Kiểm tra xem có lấy được CategoryID không
        if (nextCategoryId == -1) {
            throw new SQLException("Không lấy được CategoryID.");
        }

        // Bước 2: Insert vào Products với CategoryID vừa tạo
        String insertPro = "INSERT INTO Products (ProductCode, ProductName, Quantity, Price, CategoryID) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement ps2 = this.getConnection().prepareStatement(insertPro);
        ps2.setString(1, productCore);
        ps2.setString(2, productName);
        ps2.setInt(3, quantity);
        ps2.setInt(4, price);
        ps2.setInt(5, nextCategoryId);

        return ps2.executeUpdate();

    } catch (SQLException ex) {
        Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
    }

    return 0;
}


    /**
     * Lấy sản phẩm theo loại doanh mục người dùng chọn.
     *
     * @param typeCategory là loại danh mục người dùng muốn hiện ra.
     *
     * @return danh sách sản phẩm theo loại doanh mục người dùng chọn.
     */
    public List<Product> getTypeCategory(int typeCategory) {
        List<Product> list = new ArrayList<>();
        String query = "SELECT ProductID, ProductCode, ProductName, Quantity, Price, c.CategoryID, c.CategoryName\n"
                + "FROM Products p\n"
                + "JOIN  Categories c on p.CategoryID = c.CategoryID\n"
                + "WHERE c.CategoryID = ?";
        Object[] params = {typeCategory};

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
}
