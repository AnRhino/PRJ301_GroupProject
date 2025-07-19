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
import model.OrderStatus;

/**
 *
 * @author Nguyen Ho Phuoc An - CE190747
 */
public class OrderStatusDAO extends DBContext {

    public List<OrderStatus> getAll() {
        String query = "select StatusID, StatusDescription "
                + "from StatusType "
                + "where StatusID > 0";
        try ( ResultSet rs = execSelectQuery(query)) {
            List<OrderStatus> statuses = new LinkedList<>();
            while (rs.next()) {
                statuses.add(new OrderStatus(rs.getInt(1), rs.getString(2)));
            }
            return statuses;
        } catch (SQLException ex) {
            Logger.getLogger(OrderStatusDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
