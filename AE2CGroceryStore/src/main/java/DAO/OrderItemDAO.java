/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import dbconnect.DBContext;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.OrderItem;
import model.Product;

/**
 *
 * @author Nguyen Ho Phuoc An - CE190747
 */
public class OrderItemDAO extends DBContext {

    /**
     * Add a new row into table OrderItems of database
     *
     * @param orderId
     * @param productId
     * @param quantity
     * @param unitPrice
     * @return the number of new rows are added
     */
    public int createOrderItem(int orderId, int productId, int quantity, int unitPrice) {
        String query = "insert into OrderItems (OrderID, ProductID, Quantity, UnitPrice)\n"
                + "values (?, ?, ?, ?)";
        Object[] params = {
            orderId,
            productId,
            quantity,
            unitPrice
        };

        try {
            return execQuery(query, params);
        } catch (SQLException ex) {
            Logger.getLogger(OrderItemDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    /**
     * Add a new row into table OrderItems of database
     *
     * @param orderItem
     * @return the number of new rows are added
     */
    public int createOrderItem(OrderItem orderItem) {
        return createOrderItem(
                orderItem.getOrder().getId(),
                orderItem.getProduct().getProductID(),
                orderItem.getQuantity(),
                orderItem.getUnitPrice()
        );
    }

    /**
     * Add new rows into table OrderItems of database
     *
     * @param orderItems
     * @return the number of new rows are added
     */
    public int createOrderItems(List<OrderItem> orderItems) {
        int numberOfNewRow = 0;
        for (OrderItem orderItem : orderItems) {
            numberOfNewRow += createOrderItem(orderItem);
        }
        return numberOfNewRow;
    }

    public boolean hasBought(int userId, int productId) {
        String query = "select count(oi.OrderItemID)\n"
                + "from Orders o\n"
                + "join OrderItems oi\n"
                + "on o.OrderID = oi.OrderID\n"
                + "where o.UserID = 1\n"
                + "and oi.ProductID = 1";
        Object[] params = {userId, productId};

        try ( ResultSet rs = execSelectQuery(query, params)) {
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ReviewDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    public List<OrderItem> getAllByOrderId(int orderId) {
        String query = "select p.ProductID, p.ProductCode, p.ProductName, p.ImagePath, oi.Quantity, UnitPrice \n"
                + "from OrderItems oi\n"
                + "join Products p\n"
                + "on oi.ProductID = p.ProductID\n"
                + "where OrderID = ?";
        Object[] params = {orderId};

        try ( ResultSet rs = execSelectQuery(query, params)) {
            List<OrderItem> orderItems = new LinkedList<>();
            while (rs.next()) {
                orderItems.add(new OrderItem(
                        new Product(rs.getInt(1), rs.getString(2), rs.getString(3), 0, 0, null, rs.getString(4), false),
                        rs.getInt(5),
                        rs.getInt(6)
                ));
            }
            return orderItems;
        } catch (SQLException ex) {
            Logger.getLogger(OrderItemDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Delete a row of table OrderItems in database by OrderID
     *
     * @param orderId
     * @return the number of rows are deleted
     */
    public int deleteOrderItemsByOrderId(int orderId) {
        String query = "delete from OrderItems\n"
                + "where OrderID = ?";
        Object[] params = {orderId};

        try {
            return execQuery(query, params);
        } catch (SQLException ex) {
            Logger.getLogger(OrderItemDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
}
