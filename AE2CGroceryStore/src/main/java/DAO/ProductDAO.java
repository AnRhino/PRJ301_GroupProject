/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Category;
import model.Product;
import utils.PaginationUtil;

/**
 *
 * @author Phan Duc Tho - CE191246
 */
public class ProductDAO extends dbconnect.DBContext {

    /**
     * Lấy toàn bộ danh sách sản phẩm đang hoạt động (chưa bị ẩn).
     *
     * @return danh sách tất cả sản phẩm có IsHidden = 0.
     */
    public List<Product> getAll() {
        List<Product> list = new ArrayList<>();
        try {
            String query = "select ProductID, ProductCode, ProductName, Quantity, Price, c.CategoryID, c.CategoryName, c.IsHidden\n"
                    + "from Products p \n"
                    + "join  Categories c on p.CategoryID = c.CategoryID\n"
                    + "where p.IsHidden = 0";
            PreparedStatement ps = this.getConnection().prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                Category Category = new Category(rs.getInt(6), rs.getString(7), rs.getBoolean(8));
                Product pro = new Product(rs.getInt("ProductID"), rs.getString(2), rs.getString(3), rs.getInt("Quantity"), rs.getInt("Price"), Category);
                list.add(pro);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);

        }
        return list;
    }

    // Hàm này dùng để phân trang, show ra số lượng sản phẩm nhất định.
    public List<Product> getProductForEachPage(int page) {
        List<Product> list = new ArrayList<>();
        try {
            String query = "select ProductID, ProductCode, ProductName, Quantity, Price, c.CategoryID, c.CategoryName, c.IsHidden\n"
                    + "from Products p \n"
                    + "join  Categories c on p.CategoryID = c.CategoryID\n"
                    + "where p.IsHidden = 0\n"
                    + "order by ProductID asc\n"
                    + "offset ? rows fetch next ? rows only";
            Object[] params = {(page - 1) * PaginationUtil.NUMBER_OF_ITEMS_PER_PAGE, PaginationUtil.NUMBER_OF_ITEMS_PER_PAGE};
            ResultSet rs = execSelectQuery(query, params);
            while (rs.next()) {

                Category Category = new Category(rs.getInt(6), rs.getString(7), rs.getBoolean(8));
                Product pro = new Product(rs.getInt("ProductID"), rs.getString(2), rs.getString(3), rs.getInt("Quantity"), rs.getInt("Price"), Category);
                list.add(pro);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);

        }
        return list;
    }

    /**
     * Lấy từng trang sản phẩm mà người dùng tìm kiếm.
     *
     * @param searchInput là sản phẩm người dùng muốn tìm.
     * @param page là thứ tự trang.
     *
     * @return danh sách sản phẩm của 1 trang khớp với tìm kiếm của người dùng.
     */
    public List<Product> getSearchProductForEachPage(String searchInput, int page) {

        List<Product> list = new ArrayList<>();

        try {

            String query = "SELECT ProductID, ProductCode, ProductName, Quantity, Price, c.CategoryID, c.CategoryName, c.IsHidden\n"
                    + "FROM [dbo].[Products] p\n"
                    + "JOIN [dbo].[Categories] c ON p.CategoryID = c.CategoryID\n"
                    + "WHERE p.IsHidden = 0\n"
                    + "AND (p.ProductCode LIKE '%' + ? + '%' OR p.ProductName LIKE '%' + ? + '%')\n"
                    + "ORDER BY p.ProductID ASC\n"
                    + "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
            Object[] params = {searchInput, searchInput, (page - 1) * PaginationUtil.NUMBER_OF_ITEMS_PER_PAGE, PaginationUtil.NUMBER_OF_ITEMS_PER_PAGE};

            ResultSet rs = execSelectQuery(query, params);

            while (rs.next()) {
                Category Category = new Category(rs.getInt(6), rs.getString(7), rs.getBoolean(8));
                Product pro = new Product(rs.getInt("ProductID"), rs.getString(2), rs.getString(3), rs.getInt("Quantity"), rs.getInt("Price"), Category);
                list.add(pro);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return list;
    }

    // Dùng để phân trang cho categories.
    public List<Product> getAvailableProductsByCategoryPage(int categoryID, int page) {
        List<Product> list = new ArrayList<>();
        String query = "SELECT ProductID, ProductCode, ProductName, Quantity, Price, c.CategoryID, c.CategoryName, c.IsHidden\n"
                + "FROM Products p\n"
                + "JOIN Categories c ON p.CategoryID = c.CategoryID\n"
                + "WHERE c.CategoryID = ? AND c.IsHidden = 0\n"
                + "ORDER BY ProductID ASC\n"
                + "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        Object[] params = {
            categoryID,
            (page - 1) * PaginationUtil.NUMBER_OF_ITEMS_PER_PAGE,
            PaginationUtil.NUMBER_OF_ITEMS_PER_PAGE
        };

        try {
            ResultSet rs = execSelectQuery(query, params);

            while (rs.next()) {
                Product pro = new Product(
                        rs.getInt("ProductID"),
                        rs.getString("ProductCode"),
                        rs.getString("ProductName"),
                        rs.getInt("Quantity"),
                        rs.getInt("Price"),
                        new Category(
                                rs.getInt("CategoryID"),
                                rs.getString("CategoryName"),
                                rs.getBoolean("IsHidden")
                        )
                );
                list.add(pro);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return list;
    }

    // Đếm số lượng sản phẩm trong category.
    public int countItemByCategory(int categoryID) {
        try {
            String query = "SELECT COUNT(ProductID) FROM Products WHERE isHidden = 0 AND CategoryID = ?";
            Object[] params = {categoryID};
            ResultSet rs = execSelectQuery(query, params);
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    /**
     * Đếm số lượng sản phẩm khả thi với yêu cầu của người dùng.
     *
     * @param input là sản phầm người dùng tìm kiếm.
     *
     * @return số lượng sản phẩm khớp người dùng tìm kiếm.
     */
    public int countSearchItemMatchWithSearchInput(String input) {

        try {

            String query = "SELECT COUNT(p.ProductID)\n"
                    + "FROM [dbo].[Products] p\n"
                    + "WHERE p.IsHidden = 0\n"
                    + "AND (p.ProductCode LIKE '%' + ? + '%' OR p.ProductName LIKE '%' + ? + '%');";
            Object[] params = {input, input};

            ResultSet rs = execSelectQuery(query, params);

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return 0;
    }

    // Hàm này dùng để đếm số lượng sản phẩm còn hàng trong shop.
    public int countItem() {
        try {
            String query = "select count(ProductID) as numrow from Products where isHidden = 0";
            ResultSet rs = execSelectQuery(query, null);
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    /**
     * Thêm sản phẩm mới vào cơ sở dữ liệu.
     *
     * @return 0 nếu thêm thành công, ngược lại trả về lỗi.
     */
    public int create(String productCore, String productName, int quantity, double price, int categoryId) {
        int check = 0;
        try {
            String sql = "insert into Products (ProductCode,ProductName,Quantity,Price,CategoryID)\n"
                    + "values(?,?,?,?,?)";
            PreparedStatement ps = this.getConnection().prepareStatement(sql);
            ps.setObject(1, productCore);
            ps.setObject(2, productName);
            ps.setObject(3, quantity);
            ps.setObject(4, price);
            ps.setObject(5, categoryId);
            ps.executeQuery();

        } catch (SQLException ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return 0;
    }

    /**
     * Chỉnh sửa thông tin của sản phẩm dựa theo mã sản phẩm.
     *
     * @return 0 nếu sửa thành công, ngược lại trả về lỗi.
     */
    public int edit(int prouductId, String productCode, String productName, int quantity, double price, int cateID) {
        try {
            String query = "update  Products\n"
                    + "set ProductCode = ?, ProductName = ?,Quantity = ?,Price= ?, CategoryID = ?\n"
                    + "where ProductID = ?";

            PreparedStatement ps = this.getConnection().prepareStatement(query);
            ps.setObject(1, productCode);
            ps.setObject(2, productName);
            ps.setObject(3, quantity);
            ps.setObject(4, price);
            ps.setObject(5, cateID);
            ps.setObject(6, prouductId);
            ps.executeUpdate();

            return 0;
        } catch (SQLException ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
// dùng để lấy productId

    public Product getById(int ProductID) {
        try {
            String query = "SELECT ProductID, ProductCode, ProductName, Quantity, Price, c.CategoryID, c.CategoryName, c.IsHidden "
                    + "FROM Products p "
                    + "JOIN Categories c ON p.CategoryID = c.CategoryID "
                    + "WHERE ProductID = ?";

            PreparedStatement ps = this.getConnection().prepareStatement(query);
            ps.setInt(1, ProductID);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Category cat = new Category(rs.getInt(6), rs.getString(7), rs.getBoolean(8));
                return new Product(rs.getInt(1), rs.getString(2), rs.getString(3),
                        rs.getInt(4), rs.getInt(5), cat);
            } else {
                return null;
            }

        } catch (SQLException ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    /**
     * Lấy sản phẩm khả thi hiện tại từ cơ sở dữ liệu.
     *
     * @param productID là id sản phẩm.
     *
     * @return 1 sản phẩm từ cơ sở dữ liệu. Null nếu ko có sản phẩm nào khớp với
     * id tìm hoặc sản phẩm không khả thi (isHidden = 0).
     */
    public Product getAvailableProductById(int productID) {

        Product prod = null;

        try {

            String query = "SELECT p.ProductID, p.ProductCode, p.ProductName, p.Quantity, p.Price, p.IsHidden, c.CategoryID, c.CategoryName, c.IsHidden\n"
                    + "FROM [dbo].[Products] p\n"
                    + "JOIN [dbo].[Categories] c \n"
                    + "ON p.CategoryID = c.CategoryID\n"
                    + "WHERE p.ProductID = 1\n"
                    + "AND p.IsHidden = 0;";
            Object[] params = {productID};

            ResultSet rs = execSelectQuery(query, params);

            if (rs.next()) {
                return new Product(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5), new Category(rs.getInt(7), rs.getString(8), rs.getBoolean(9)));
            }

        } catch (SQLException ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

        return prod;
    }

    /**
     * Xóa mềm một sản phẩm (ẩn khỏi danh sách hiển thị).
     *
     * @param productId là mã sản phẩm cần xóa.
     *
     * @return số dòng bị ảnh hưởng (1 nếu thành công).
     */
    public int delete(int productId) {
        String query = "update Products\n"
                + "set IsHidden = 1\n"
                + "where ProductID = ?";
        try {
            PreparedStatement ps = this.getConnection().prepareStatement(query);
            ps.setObject(1, productId);
            return ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;

    }

    /**
     * Lấy sản phẩm theo loại doanh mục người dùng chọn.
     *
     * @param categoryID là loại danh mục người dùng muốn hiện sản phẩm ra.
     *
     * @return danh sách sản phẩm theo loại doanh mục người dùng chọn.
     */
    public List<Product> getProductsByCategory(int categoryID) {
        List<Product> list = new ArrayList<>();
        String query = "SELECT ProductID, ProductCode, ProductName, Quantity, Price, c.CategoryID, c.CategoryName, c.IsHidden\n"
                + "FROM Products p\n"
                + "JOIN  Categories c on p.CategoryID = c.CategoryID\n"
                + "WHERE c.CategoryID = ?;";
        Object[] params = {categoryID};

        try {
            ResultSet rs = execSelectQuery(query, params);

            while (rs.next()) {
                Product pro = new Product(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt("Quantity"), rs.getInt("Price"), new Category(rs.getInt(6), rs.getString(7), rs.getBoolean(8)));
                list.add(pro);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);

        }

        return list;
    }

    /**
     * Lấy sản phẩm khả dụng theo loại doanh mục người dùng chọn.
     *
     * @param categoryID là loại danh mục người dùng muốn hiện sản phẩm ra.
     *
     * @return danh sách sản phẩm theo loại doanh mục người dùng chọn.
     */
    public List<Product> getAvailableProductsByCategory(int categoryID) {
        List<Product> list = new ArrayList<>();
        String query = "SELECT ProductID, ProductCode, ProductName, Quantity, Price, c.CategoryID, c.CategoryName, c.IsHidden\n"
                + "FROM Products p\n"
                + "JOIN  Categories c on p.CategoryID = c.CategoryID\n"
                + "WHERE c.CategoryID = ?"
                + "AND c.IsHidden = 0;";
        Object[] params = {categoryID};

        try {
            ResultSet rs = execSelectQuery(query, params);

            while (rs.next()) {
                Product pro = new Product(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt("Quantity"), rs.getInt("Price"), new Category(rs.getInt(6), rs.getString(7), rs.getBoolean(8)));
                list.add(pro);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);

        }

        return list;
    }

    /**
     * Phương thức lấy giá trị trung bình đánh giá sản phẩm của khác hàng.
     *
     * @param productID là sản phẩm muốn lấy đánh giá.
     *
     * @return giá trị trung bình đánh giá của sản phẩm.
     */
    public Double getRateScore(int productID) {
        Double rateScore = null;
        String query = "SELECT\n"
                + "\n"
                + "(SELECT SUM(rv.Rating)\n"
                + "FROM [dbo].[Reviews] rv\n"
                + "JOIN [dbo].[Products] prod\n"
                + "ON prod.ProductID = rv.ProductID\n"
                + "WHERE prod.ProductID = ?)\n"
                + "/\n"
                + "(SELECT COUNT(ReviewID)\n"
                + "FROM [dbo].[Reviews] rv\n"
                + "JOIN [dbo].[Products] prod\n"
                + "ON prod.ProductID = rv.ProductID\n"
                + "WHERE prod.ProductID = ?)\n"
                + "\n"
                + "AS RESULT";
        Object[] params = {productID, productID};

        try {
            ResultSet rs = execSelectQuery(query, params);

            while (rs.next()) {
                rateScore = rs.getDouble(1);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return rateScore;
    }

    /**
     * Lấy số lượng trong kho của sản phẩm.
     *
     * @param productID là id của sản phẩm.
     *
     * @return số lượng của sản phẩm đó.
     */
    public int getMaxQuantity(int productID) {

        String query = "SELECT p.Quantity\n"
                + "FROM [dbo].[Products] p \n"
                + "WHERE p.ProductID = ?";
        Object[] params = {productID};

        try {
            ResultSet rs = execSelectQuery(query, params);

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return 0;
    }

    /**
     * Lấy id cao nhất trong database của product.
     *
     * @return id cao nhất của product.
     */
    public int getMaxID() {

        try {
            String query = "SELECT MAX(p.ProductID)\n"
                    + "FROM [dbo].[Products] p;";

            ResultSet rs = execSelectQuery(query);

            while (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return 0;
    }

}
