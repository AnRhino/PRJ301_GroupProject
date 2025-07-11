/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import dbconnect.DBContext;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.DiscountCode;

/**
 *
 * @author Nguyen Ho Phuoc An - CE190747
 */
public class DiscountCodeDAO extends DBContext {
    //Create

    //Read
    public List<DiscountCode> getAll() {
        List<DiscountCode> list = new ArrayList<>();
        String query = "select DiscountCodeID, Code, DiscountValue, DiscountTypeID, QuantityAvailable, ExpiryDate, MinOrderValue\n"
                + "from DiscountCodes";
        try ( ResultSet rs = execSelectQuery(query)) {
            while (rs.next()) {
                list.add(new DiscountCode(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getInt(3),
                        rs.getInt(4),
                        rs.getInt(5),
                        rs.getDate(6),
                        rs.getInt(7)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DiscountCodeDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    //Update

    //Delete
}
