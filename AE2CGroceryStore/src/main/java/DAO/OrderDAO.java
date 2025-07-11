/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import dbconnect.DBContext;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.DiscountCode;
import model.Order;
import model.OrderStatus;
import model.User;

/**
 *
 * @author Nguyen Ho Phuoc An - CE190747
 */
public class OrderDAO extends DBContext {

    /**
     * Create a new row for table Orders in database
     *
     * @param userId
     * @param deliveryDate
     * @param discountCodeId
     * @param phoneNumber
     * @param address
     * @return the number of rows are added
     */
    public int createOrder(int userId, LocalDateTime deliveryDate,
            Integer discountCodeId, String phoneNumber, String address) {
        String query = "INSERT INTO Orders (UserID, DeliveryDate, DiscountCodeID, PhoneNumber, [Address])\n"
                + "VALUES (?, ?, ?, ?, ?)";
        Object[] params = {
            userId,
            java.sql.Date.valueOf(deliveryDate.toLocalDate()),
            discountCodeId,
            phoneNumber,
            address
        };

        try {
            return execQuery(query, params);
        } catch (SQLException ex) {
            Logger.getLogger(OrderDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return 0;
    }

    /**
     * Return the lastest order of this user in database
     *
     * @param user
     * @return an object Order
     */
    public Order getLastestOrderByUser(User user) {
        String query = "select top 1 OrderID, OrderDate, DeliveryDate, \n"
                + "StatusID, DiscountCodeID, PhoneNumber, [Address]\n"
                + "from Orders\n"
                + "where UserID = ?\n"
                + "order by OrderID desc";
        Object[] params = {user.getId()};
        try ( ResultSet rs = execSelectQuery(query, params)) {
            if (rs.next()) {
                return new Order(
                        rs.getInt(1),
                        user,
                        rs.getDate(2).toLocalDate().atStartOfDay(),
                        rs.getDate(3).toLocalDate().atStartOfDay(),
                        new OrderStatus(rs.getInt(4), null),
                        new DiscountCode(rs.getInt(5), null, 0, 0, 0, null, 0),
                        rs.getString(6),
                        rs.getString(7),
                        null
                );
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrderDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Delete a row of table Orders in database by OrderID
     *
     * @param orderId
     * @return the number of rows are deleted
     */
    public int deleteOrderById(int orderId) {
        String query = "delete from Orders\n"
                + "where OrderID = ?";
        Object[] params = {orderId};

        try {
            return execQuery(query, params);
        } catch (SQLException ex) {
            Logger.getLogger(OrderDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
}
