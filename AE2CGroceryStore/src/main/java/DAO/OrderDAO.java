/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import dbconnect.DBContext;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Category;
import model.DiscountCode;
import model.Order;
import model.OrderItem;
import model.OrderStatus;
import model.Product;
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

    public List<Order> getAllOrdersByUser(User user) {
        String query = "select OrderID, OrderDate, DeliveryDate, \n"
                + "st.StatusID, st.StatusDescription, \n"
                + "dc.DiscountCodeID, dc.DiscountValue, dc.DiscountTypeID,\n"
                + "PhoneNumber, [Address]\n"
                + "from Orders o\n"
                + "join DiscountCodes dc\n"
                + "on o.DiscountCodeID = dc.DiscountCodeID\n"
                + "join StatusType st\n"
                + "on o.StatusID = st.StatusID\n"
                + "where UserID = ?\n"
                + "order by OrderDate desc";
        Object[] params = {user.getId()};
        try ( ResultSet rs = execSelectQuery(query, params)) {
            List<Order> orders = new LinkedList<>();
            while (rs.next()) {
                orders.add(new Order(
                        rs.getInt(1),
                        user,
                        rs.getDate(2).toLocalDate().atStartOfDay(),
                        rs.getDate(3).toLocalDate().atStartOfDay(),
                        new OrderStatus(rs.getInt(4), rs.getString(5)),
                        new DiscountCode(rs.getInt(6), rs.getInt(7), rs.getInt(8)),
                        rs.getString(9),
                        rs.getString(10),
                        null
                ));
                return orders;
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

    /**
     * Lấy tất cả từng order với 1 order gắn liền với orderitem.
     *
     * @return danh sách order trong db.
     */
    public List<Order> getAllOrderData() {

        List<Order> list = new ArrayList<>();

        try {

            String query = "SELECT o.OrderID, o.OrderDate, o.DeliveryDate, p.ProductID, p.ProductCode, p.ProductName, s.StatusID, s.StatusDescription, ot.Quantity, ot.UnitPrice, c.CategoryID, c.CategoryName\n"
                    + "FROM [dbo].[Orders] o\n"
                    + "JOIN [dbo].[OrderItems] ot\n"
                    + "ON ot.OrderID = o.OrderID\n"
                    + "JOIN [dbo].[Products] p\n"
                    + "ON p.ProductID = ot.ProductID\n"
                    + "JOIN [dbo].[StatusType] s\n"
                    + "ON s.StatusID = o.StatusID\n"
                    + "JOIN [dbo].[Categories] c\n"
                    + "ON c.CategoryID = p.CategoryID;";

            ResultSet rs = execSelectQuery(query);

            while (rs.next()) {

                List<OrderItem> listOrderItem = new ArrayList();
                listOrderItem.add(new OrderItem(
                        null,
                        new Product(rs.getInt(4),
                                rs.getString(5),
                                rs.getString(6),
                                new Category(rs.getInt(11),
                                        rs.getString(12))),
                        rs.getInt(9),
                        rs.getInt(10)));

                list.add(
                        new Order(
                                rs.getInt(1),
                                null,
                                rs.getObject(2, LocalDateTime.class),
                                rs.getObject(3, LocalDateTime.class),
                                new OrderStatus(rs.getInt(7),
                                        rs.getString(8)),
                                null,
                                null,
                                null,
                                listOrderItem
                        ));
            }

        } catch (SQLException ex) {
            Logger.getLogger(OrderDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return list;
    }
}
