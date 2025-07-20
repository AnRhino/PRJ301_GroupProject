/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Product;
import model.Review;
import model.User;
import utils.PaginationUtil;

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
        String query = "SELECT rv.ReviewID, us.UserID, us.UserName, prod.ProductID, rv.Rating , rv.Comment, rv.ReviewTime\n"
                + "FROM [dbo].[Reviews] rv\n"
                + "JOIN [dbo].[Products] prod\n"
                + "ON prod.ProductID = rv.ProductID\n"
                + "JOIN [dbo].[Users] us\n"
                + "ON us.UserID = rv.UserID\n";

        try {
            ResultSet rs = execSelectQuery(query);

            while (rs.next()) {
                list.add(new Review(rs.getInt(1), new User(rs.getInt(2), rs.getString(3)), new Product(rs.getInt(4)), rs.getInt(5), rs.getString(6), rs.getObject(7, LocalDateTime.class)));
            }

        } catch (SQLException ex) {
            Logger.getLogger(ReviewDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return list;
    }

    public int countReviewByProductId(int productID) {
        try {
            String query = "SELECT COUNT(ReviewID) \n"
                    + "FROM [dbo].[Reviews] rv\n"
                    + "JOIN [dbo].[Products] prod\n"
                    + "ON prod.ProductID = rv.ProductID\n"
                    + "JOIN [dbo].[Users] us\n"
                    + "ON us.UserID = rv.UserID\n"
                    + "WHERE isHidden = 0 AND prod.ProductID = ?";
            Object[] params = {productID};
            ResultSet rs = execSelectQuery(query, params);
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public List<Review> getByProductIDForPagination(int productID, int page) {
        List<Review> list = new ArrayList<>();
        String query = "SELECT rv.ReviewID, us.UserID, us.UserName, prod.ProductID, rv.Rating , rv.Comment, rv.ReviewTime\n"
                + "FROM [dbo].[Reviews] rv\n"
                + "JOIN [dbo].[Products] prod\n"
                + "ON prod.ProductID = rv.ProductID\n"
                + "JOIN [dbo].[Users] us\n"
                + "ON us.UserID = rv.UserID\n"
                + "WHERE prod.ProductID = ?\n"
                + "AND prod.IsHidden = 0\n"
                + "ORDER BY ReviewID ASC\n"
                + "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        Object[] params = {productID, (page - 1) * PaginationUtil.NUMBER_OF_REVIEWS_PER_PAGE,
            PaginationUtil.NUMBER_OF_REVIEWS_PER_PAGE};

        try {
            ResultSet rs = execSelectQuery(query, params);

            while (rs.next()) {
                list.add(new Review(rs.getInt(1), new User(rs.getInt(2), rs.getString(3)), new Product(rs.getInt(4)), rs.getInt(5), rs.getString(6), rs.getObject(7, LocalDateTime.class)));
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
        String query = "SELECT rv.ReviewID, us.UserID, us.UserName, prod.ProductID, rv.Rating , rv.Comment, rv.ReviewTime\n"
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
                list.add(new Review(rs.getInt(1), new User(rs.getInt(2), rs.getString(3)), new Product(rs.getInt(4)), rs.getInt(5), rs.getString(6), rs.getObject(7, LocalDateTime.class)));
            }

        } catch (SQLException ex) {
            Logger.getLogger(ReviewDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return list;
    }

    /**
     * Tạo comment mới.
     *
     * @param userID là id của người dùng.
     * @param productID là id của sản phẩm.
     * @param rating là đánh giá sản phẩm (1-5).
     * @param comment là comment của người dùng về sản phẩm.
     * @param reviewDate là ngảy comment.
     *
     * @return 0 nếu thêm comment không thành công. Khác 0 nếu thành công.
     */
    public int add(int userID, int productID, int rating, String comment, LocalDateTime reviewDate) {

        try {

            String query = "INSERT INTO [dbo].[Reviews] (UserID, ProductID, Rating, Comment, ReviewTime)\n"
                    + "VALUES (?, ?, ?, ?, ?)";
            Object[] params = {userID, productID, rating, comment, reviewDate};

            return execQuery(query, params);

        } catch (SQLException ex) {
            Logger.getLogger(ReviewDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return 0;
    }

    /**
     * Delete 1 review nào đó.
     *
     * @param reviewID là id của review cần xóa.
     *
     * @return 0 nếu delete thất bại. Khác 0 nếu delete thành công.
     */
    public int delete(int reviewID) {

        try {
            String query = "DELETE FROM [dbo].[Reviews] \n"
                    + "WHERE ReviewID = ?";
            Object[] params = {reviewID};

            return execQuery(query, params);

        } catch (SQLException ex) {
            Logger.getLogger(ReviewDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return 0;
    }

    /**
     * Update comment cũ thành 1 comment trong 1 review.
     *
     * @param reviewID là id của review.
     * @param comment là comment mới.
     *
     * @return 0 nếu update thất bại. Khác 0 nếu thành công.
     */
    public int edit(int reviewID, String comment) {

        try {
            String query = "UPDATE [dbo].[Reviews]\n"
                    + "SET Comment = ?\n"
                    + "WHERE ReviewID = ?";
            Object[] params = {comment, reviewID};

            return execQuery(query, params);

        } catch (SQLException ex) {
            Logger.getLogger(ReviewDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return 0;
    }
}
