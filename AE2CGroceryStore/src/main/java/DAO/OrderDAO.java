/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import dbconnect.DBContext;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nguyen Ho Phuoc An - CE190747
 */
public class OrderDAO extends DBContext {

    public int createOrder(int userId, LocalDateTime deliveryDate,
            int discountCodeId, String phoneNumber, String address) {
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
    
    
}
