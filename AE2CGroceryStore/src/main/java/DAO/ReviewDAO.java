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
import model.Review;

/**
 *
 * @author Vu Minh Khang - CE191371
 */
public class ReviewDAO extends dbconnect.DBContext {

    public List<Review> getAll() {
        List<Review> list = new ArrayList<>();
        String query = "SELECT rv.ReviewID, us.UserID, prod.ProductID, rv.Rating , rv.Comment, YEAR(rv.ReviewDate) AS YearComment, MONTH(rv.ReviewDate) AS MonthComment, DAY(rv.ReviewDate) AS DayComment\n"
                + "FROM [dbo].[Reviews] rv\n"
                + "JOIN [dbo].[Products] prod\n"
                + "ON prod.ProductID = rv.ProductID\n"
                + "JOIN [dbo].[Users] us\n"
                + "ON us.UserID = rv.UserID";

        try {
            ResultSet rs = execSelectQuery(query);

            while (rs.next()) {
                list.add(new Review(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getString(5), new Date(rs.getInt(6), rs.getInt(7), rs.getInt(8))));
            }

        } catch (SQLException ex) {
            Logger.getLogger(ReviewDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return list;
    }
}
