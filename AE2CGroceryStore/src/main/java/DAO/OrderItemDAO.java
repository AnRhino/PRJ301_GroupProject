/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import dbconnect.DBContext;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.OrderItem;

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
}
