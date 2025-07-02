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
import model.Date;
import model.Product;
import model.Review;
import model.User;

/**
 *
 * @author Vu Minh Khang - CE191371
 */
public class ReviewDAO extends dbconnect.DBContext {

    /**
     * Lấy tất cả review trong database.
     *
     * @return danh sách tất cả review tồn tại.
     */
    public List<Review> getAll() {
        List<Review> list = new ArrayList<>();
        String query = "SELECT rv.ReviewID, us.UserID, us.UserName, prod.ProductID, rv.Rating , rv.Comment, YEAR(rv.ReviewDate) AS YearComment, MONTH(rv.ReviewDate) AS MonthComment, DAY(rv.ReviewDate) AS DayComment\n"
                + "FROM [dbo].[Reviews] rv\n"
                + "JOIN [dbo].[Products] prod\n"
                + "ON prod.ProductID = rv.ProductID\n"
                + "JOIN [dbo].[Users] us\n"
                + "ON us.UserID = rv.UserID\n";

        try {
            ResultSet rs = execSelectQuery(query);

            while (rs.next()) {
                list.add(new Review(rs.getInt(1), new User(rs.getInt(2), rs.getString(3)), new Product(rs.getInt(4)), rs.getInt(5), rs.getString(6), new Date(rs.getInt(7), rs.getInt(8), rs.getInt(9))));
            }

        } catch (SQLException ex) {
            Logger.getLogger(ReviewDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return list;
    }

    /**
     * Phương thức lấy tất cả review về 1 loại sản phẩm nào đó.
     *
     * @param productID là id của sản phẩm muốn lấy review.
     *
     * @return danh sách các review về sản phẩm đó.
     */
    public List<Review> getByProductID(int productID) {
        List<Review> list = new ArrayList<>();
        String query = "SELECT rv.ReviewID, us.UserID, us.UserName, prod.ProductID, rv.Rating , rv.Comment, YEAR(rv.ReviewDate) AS YearComment, MONTH(rv.ReviewDate) AS MonthComment, DAY(rv.ReviewDate) AS DayComment\n"
                + "FROM [dbo].[Reviews] rv\n"
                + "JOIN [dbo].[Products] prod\n"
                + "ON prod.ProductID = rv.ProductID\n"
                + "JOIN [dbo].[Users] us\n"
                + "ON us.UserID = rv.UserID\n"
                + "WHERE prod.ProductID = ?\n"
                + "AND prod.IsHidden = 0;";
        Object[] params = {productID};

        try {
            ResultSet rs = execSelectQuery(query, params);

            while (rs.next()) {
                list.add(new Review(rs.getInt(1), new User(rs.getInt(2), rs.getString(3)), new Product(rs.getInt(4)), rs.getInt(5), rs.getString(6), new Date(rs.getInt(7), rs.getInt(8), rs.getInt(9))));
            }

        } catch (SQLException ex) {
            Logger.getLogger(ReviewDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return list;
    }
}
