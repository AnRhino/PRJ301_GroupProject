/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import dbconnect.DBContext;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.DiscountCode;
import model.DiscountCodeType;

/**
 *
 * @author Nguyen Ho Phuoc An - CE190747
 */
public class DiscountCodeDAO extends DBContext {

    //Create
    public int create(String code, int value, int type, int quantity, LocalDate expiryDate, int minOrderValue, int isHidden) {
        try {
            String query = "INSERT INTO [dbo].[DiscountCodes]\n"
                    + "           ([Code]\n"
                    + "           ,[DiscountValue]\n"
                    + "           ,[DiscountTypeID]\n"
                    + "           ,[QuantityAvailable]\n"
                    + "           ,[ExpiryDate]\n"
                    + "           ,[MinOrderValue]\n"
                    + "           ,[IsHidden])\n"
                    + "     VALUES\n"
                    + "           (?\n"
                    + "           ,?\n"
                    + "           ,?\n"
                    + "           ,?\n"
                    + "           ,?\n"
                    + "           ,?\n"
                    + "           ,?)";
            Object[] params = {code, value, type, quantity, expiryDate, minOrderValue, isHidden};
            return execQuery(query, params);
        } catch (SQLException ex) {
            Logger.getLogger(DiscountCodeDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

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
                        rs.getDate(6).toLocalDate(),
                        rs.getInt(7)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DiscountCodeDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public List<DiscountCode> getAllShippingCode() {
        List<DiscountCode> listShippingCode = new ArrayList<>();
        String query = "SELECT DiscountCodeID, Code, DiscountValue, DiscountTypeID, QuantityAvailable, ExpiryDate, MinOrderValue\n"
                + "FROM DiscountCodes\n"
                + "WHERE  DiscountTypeID = 2";
        try ( ResultSet rs = execSelectQuery(query)) {
            while (rs.next()) {
                listShippingCode.add(new DiscountCode(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getInt(3),
                        rs.getInt(4),
                        rs.getInt(5),
                        rs.getDate(6).toLocalDate(),
                        rs.getInt(7)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DiscountCodeDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listShippingCode;
    }

    public List<DiscountCode> getAllPriceCode() {
        List<DiscountCode> listPriceCode = new ArrayList<>();
        String query = "SELECT DiscountCodeID, Code, DiscountValue, DiscountTypeID, QuantityAvailable, ExpiryDate, MinOrderValue\n"
                + "FROM DiscountCodes\n"
                + "WHERE NOT (DiscountTypeID = 2)";
        try ( ResultSet rs = execSelectQuery(query)) {
            while (rs.next()) {
                listPriceCode.add(new DiscountCode(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getInt(3),
                        rs.getInt(4),
                        rs.getInt(5),
                        rs.getDate(6).toLocalDate(),
                        rs.getInt(7)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DiscountCodeDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listPriceCode;
    }

    /**
     * This function check if code is already exists in database
     *
     * @param code to be checked
     * @return 1 for exists, 0 otherwise
     */
    public boolean exists(String code) {
        String query = "SELECT Code\n"
                + "FROM DiscountCodes\n"
                + "WHERE Code = ?";
        Object[] params = {code};
        try(ResultSet rs = execSelectQuery(query, params)){
            if (rs.next()){
               return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DiscountCodeDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    /**
     * This function query valid code for current user (not hidden, hasn't
     * expired, haven't used in past order, quantity > 0)
     *
     * @return
     */
    public List<DiscountCode> getAllUsableShippingCode(int UserID) {
        List<DiscountCode> listUsableShippingCode = new ArrayList<>();
        String query = "SELECT DiscountCodeID, Code, DiscountValue, DiscountTypeID, QuantityAvailable, ExpiryDate, MinOrderValue\n"
                + "FROM DiscountCodes\n"
                + "WHERE IsHidden = 0\n"
                + "AND ExpiryDate >= GETDATE()\n"
                + "AND  DiscountTypeID = 2\n"
                + "AND DiscountCodeID NOT IN (\n"
                + "SELECT DiscountCodeID\n"
                + "FROM Orders\n"
                + "WHERE UserID = ?\n"
                + "AND DiscountCodeID IS NOT NULL\n"
                + ")\n"
                + "AND QuantityAvailable > 1";
        Object[] params = {UserID};
        try ( ResultSet rs = execSelectQuery(query, params)) {
            while (rs.next()) {
                listUsableShippingCode.add(new DiscountCode(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getInt(3),
                        rs.getInt(4),
                        rs.getInt(5),
                        rs.getDate(6).toLocalDate(),
                        rs.getInt(7)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DiscountCodeDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listUsableShippingCode;
    }

    public List<DiscountCode> getAllUsablePriceCode(int UserID) {
        List<DiscountCode> listUsablePriceCode = new ArrayList<>();
        String query = "SELECT DiscountCodeID, Code, DiscountValue, DiscountTypeID, QuantityAvailable, ExpiryDate, MinOrderValue\n"
                + "FROM DiscountCodes\n"
                + "WHERE IsHidden = 0\n"
                + "AND ExpiryDate >= GETDATE()\n"
                + "AND  NOT (DiscountTypeID = 2)\n"
                + "AND DiscountCodeID NOT IN (\n"
                + "SELECT DiscountCodeID\n"
                + "FROM Orders\n"
                + "WHERE UserID = ?\n"
                + "AND DiscountCodeID IS NOT NULL\n"
                + ")\n"
                + "AND QuantityAvailable > 1";
        Object[] params = {UserID};
        try ( ResultSet rs = execSelectQuery(query, params)) {
            while (rs.next()) {
                listUsablePriceCode.add(new DiscountCode(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getInt(3),
                        rs.getInt(4),
                        rs.getInt(5),
                        rs.getDate(6).toLocalDate(),
                        rs.getInt(7)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DiscountCodeDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listUsablePriceCode;
    }

    public List<DiscountCodeType> getAllDiscountType() {
        List<DiscountCodeType> listDiscountType = new ArrayList<>();
        String query = "SELECT DiscountTypeID, TypeName FROM DiscountTypes";
        try ( ResultSet rs = execSelectQuery(query)) {
            while (rs.next()) {
                listDiscountType.add(new DiscountCodeType(rs.getInt(1), rs.getString(2)));
            }
            return listDiscountType;
        } catch (SQLException ex) {
            Logger.getLogger(DiscountCodeDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    //Update
    public int discountCodeEdit(int id, String code, int value, int type, int quantity, Date expiryDate, int minOrderValue, int isHidden) {
        try {
            String query = "UPDATE [dbo].[DiscountCodes]\n"
                    + "   SET [Code] = ?\n"
                    + "      ,[DiscountValue] = ?\n"
                    + "      ,[DiscountTypeID] = ?\n"
                    + "      ,[QuantityAvailable] = ?\n"
                    + "      ,[ExpiryDate] = ?\n"
                    + "      ,[MinOrderValue] = ?\n"
                    + "      ,[IsHidden] = ?\n"
                    + " WHERE DiscountCodeId = ?";
            Object[] params = {code, value, type, quantity, expiryDate, minOrderValue, isHidden, id};
            return execQuery(query, params);
        } catch (SQLException ex) {
            Logger.getLogger(DiscountCodeDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }
    //Delete
}
