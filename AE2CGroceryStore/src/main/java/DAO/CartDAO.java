/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Cart;
import model.Category;
import model.Product;
import model.User;

/**
 *
 * @author Vu Minh Khang - CE191371
 */
public class CartDAO extends dbconnect.DBContext {

    /**
     * Lấy các sản phẩm có thể mua trong giỏ hàng của người dùng.
     *
     * @param userId ID người dùng
     * @return Danh sách sản phẩm có thể mua
     */
    public List<Cart> getCanBuy(int userId) {
        List<Cart> list = new ArrayList<>();
        String query = "SELECT c.CartItemID, u.UserID, u.Username, prod.ProductID, c.Quantity, prod.ProductName, prod.Price "
                + "FROM Carts c "
                + "JOIN Users u ON u.UserID = c.UserID "
                + "JOIN Products prod ON prod.ProductID = c.ProductID "
                + "WHERE u.UserID = ? AND prod.Quantity > 0 AND prod.IsHidden = 0";
        try ( PreparedStatement ps = this.getConnection().prepareStatement(query)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Product product = new Product(rs.getInt(4));
                product.setProductName(rs.getString(6));
                product.setPrice(rs.getInt(7));
                User user = new User(rs.getInt(2), rs.getString(3));
                Cart cart = new Cart(rs.getInt(1), user, product, rs.getInt(5));
                list.add(cart);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CartDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    /**
     * Lấy danh sách sản phẩm hết hàng trong giỏ hàng.
     *
     * @param userId ID người dùng
     * @return Danh sách sản phẩm hết hàng
     */
    public List<Cart> getOutOfStock(int userId) {
        List<Cart> list = new ArrayList<>();
        String query = "SELECT c.CartItemID, u.UserID, u.Username, prod.ProductID, c.Quantity, prod.ProductName, prod.Price "
                + "FROM Carts c "
                + "JOIN Users u ON u.UserID = c.UserID "
                + "JOIN Products prod ON prod.ProductID = c.ProductID "
                + "WHERE u.UserID = ? AND prod.Quantity = 0 AND prod.IsHidden = 0";
        try ( PreparedStatement ps = this.getConnection().prepareStatement(query)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Product product = new Product(rs.getInt(4));
                product.setProductName(rs.getString(6));
                product.setPrice(rs.getInt(7));
                User user = new User(rs.getInt(2), rs.getString(3));
                Cart cart = new Cart(rs.getInt(1), user, product, rs.getInt(5));
                list.add(cart);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CartDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    /**
     * Lấy danh sách sản phẩm đã ẩn nhưng vẫn còn trong giỏ hàng.
     *
     * @param userId ID người dùng
     * @return Danh sách sản phẩm đã bị ẩn
     */
    public List<Cart> getProductIsHidden(int userId) {
        List<Cart> list = new ArrayList<>();
        String query = "SELECT c.CartItemID, u.UserID, u.Username, prod.ProductID, c.Quantity, prod.ProductName, prod.Price "
                + "FROM Carts c "
                + "JOIN Users u ON u.UserID = c.UserID "
                + "JOIN Products prod ON prod.ProductID = c.ProductID "
                + "WHERE u.UserID = ? AND prod.IsHidden = 1";
        try ( PreparedStatement ps = this.getConnection().prepareStatement(query)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Product product = new Product(rs.getInt(4));
                product.setProductName(rs.getString(6));
                product.setPrice(rs.getInt(7));
                User user = new User(rs.getInt(2), rs.getString(3));
                Cart cart = new Cart(rs.getInt(1), user, product, rs.getInt(5));
                list.add(cart);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CartDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    /**
     * Tạo 1 sản phấm trong cart của người dùng.
     *
     * @param userID là id của người dùng.
     * @param productID là id của sản phẩm muốn thêm vào cart.
     * @param quantity là số lượng thêm vào.
     *
     * @return 0 nếu thêm không thành công. Khác 0 nếu thành công.
     */
    public int addNewProductToCart(int userID, int productID, int quantity) {

        try {
            String query = "INSERT INTO [dbo].[Carts] (UserID, ProductID, Quantity)\n"
                    + "VALUES (?, ?, ?);";
            Object[] params = {userID, productID, quantity};

            return execQuery(query, params);

        } catch (SQLException ex) {
            Logger.getLogger(CartDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return 0;
    }

    public Cart getCartByID(int cartItemID) {
        String query = "SELECT c.CartItemID, u.UserID, u.Username, p.ProductID, p.ProductName, p.Price, c.Quantity "
                + "FROM Carts c "
                + "JOIN Users u ON u.UserID = c.UserID "
                + "JOIN Products p ON p.ProductID = c.ProductID "
                + "WHERE c.CartItemID = ?";
        try {
            PreparedStatement ps = this.getConnection().prepareStatement(query);
            ps.setInt(1, cartItemID);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                User user = new User(rs.getInt("UserID"), rs.getString("Username"));
                Product product = new Product(rs.getInt("ProductID"));
                product.setProductName(rs.getString("ProductName"));
                product.setPrice(rs.getInt("Price"));
                product.setQuantity(rs.getInt("Quantity"));
                return new Cart(rs.getInt("CartItemID"), user, product, rs.getInt("Quantity"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Cập nhật số lượng sản phẩm trong cart.
     *
     * @param cartId ID mục cart
     * @param quantity Số lượng mới
     */
    public void edit(int cartId, int quantity) {
        String query = "UPDATE Carts SET Quantity = ? WHERE CartItemID = ?";
        try ( PreparedStatement ps = this.getConnection().prepareStatement(query)) {
            ps.setInt(1, quantity);
            ps.setInt(2, cartId);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CartDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Xoá mục cart theo ID.
     *
     * @param cartID ID mục cart cần xoá
     * @return Số dòng bị ảnh hưởng
     */
    public int delete(int cartID) {
        String query = "DELETE FROM Carts WHERE CartItemID = ?";
        try ( PreparedStatement ps = this.getConnection().prepareStatement(query)) {
            ps.setInt(1, cartID);
            return ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CartDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public int deleteByUserIdAndProductId(int userId, int productId) {
        String query = "delete from Carts\n"
                + "where UserID = ? AND ProductID = ?";
        Object[] params = {userId, productId};
        
        try {
            return execQuery(query, params);
        } catch (SQLException ex) {
            Logger.getLogger(CartDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

}
