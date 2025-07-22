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
import model.Order;
import model.OrderItem;
import model.Product;
import utils.PaginationUtil;

/**
 *
 * @author Phan Duc Tho - CE191246
 */
public class ProductDAO extends dbconnect.DBContext {

    /**
     * Lấy toàn bộ danh sách sản phẩm.
     *
     * @return danh sách tất cả sản phẩm.
     */
    public List<Product> getAll() {
        List<Product> list = new ArrayList<>();
        try {
            String query = "select ProductID, ProductCode, ProductName, Quantity, Price, c.CategoryID, c.CategoryName, p.IsHidden, p.ImagePath, c.ImagePath\n"
                    + "from Products p\n"
                    + "join  Categories c on p.CategoryID = c.CategoryID\n";

            PreparedStatement ps = this.getConnection().prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

//                Category Category = new Category(rs.getInt(6), rs.getString(7), rs.getBoolean(8));
                Category cat = new Category(rs.getInt(6), rs.getString(7), rs.getString(10), rs.getBoolean(8));
                Product pro = new Product(rs.getInt("ProductID"), rs.getString(2), rs.getString(3), rs.getInt("Quantity"), rs.getInt("Price"), cat);
                pro.setIsHidden(rs.getBoolean(8));
                pro.setCoverImg(rs.getString(9));
                list.add(pro);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);

        }
        return list;
    }

    /**
     * Lấy tất cả id sản phẩm trong cơ sở dữ liệu.
     *
     * @return danh sách các id sản phẩm trong cơ sở dữ liệu.
     */
    public List<Product> getAllProductID() {

        List<Product> list = new ArrayList<>();

        try {
            String query = "SELECT p.ProductID\n"
                    + "FROM [dbo].[Products] p;";

            ResultSet rs = execSelectQuery(query);

            while (rs.next()) {
                list.add(new Product(rs.getInt(1)));
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
            String query = "select ProductID, ProductCode, ProductName, Quantity, Price, c.CategoryID, c.CategoryName, c.IsHidden, p.ImagePath\n"
                    + "from Products p \n"
                    + "join  Categories c on p.CategoryID = c.CategoryID\n"
                    + "where p.IsHidden = 0\n"
                    + "order by ProductID asc\n"
                    + "offset ? rows fetch next ? rows only";
            Object[] params = {(page - 1) * PaginationUtil.NUMBER_OF_ITEMS_PER_PAGE, PaginationUtil.NUMBER_OF_ITEMS_PER_PAGE};
            ResultSet rs = execSelectQuery(query, params);
            while (rs.next()) {

                Category Category = new Category(rs.getInt(6), rs.getString(7), rs.getBoolean(8));
                Product pro = new Product(rs.getInt("ProductID"), rs.getString(2), rs.getString(3), rs.getInt("Quantity"), rs.getInt("Price"), Category, rs.getString(9), true);
                list.add(pro);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);

        }
        return list;
    }

    public List<Product> carouselProduct() {
        List<Product> list = new ArrayList<>();
        try {
            String query = "select top 5 p.ProductID, p.ProductName,p.ImagePath, SUM(od.Quantity) sumQuantity\n"
                    + "from Products p\n"
                    + "join OrderItems od\n"
                    + "on p.ProductID = od.ProductID\n"
                    + "where p.Quantity > 0 and isHidden = 0\n"
                    + "group by p.ProductID, p.ProductName, p.ImagePath, p.Quantity\n"
                    + "order by sumQuantity desc, p.Quantity desc";
            ResultSet rs = execSelectQuery(query, null);
            while (rs.next()) {
                list.add(new Product(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4)));
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

            String query = "SELECT ProductID, ProductCode, ProductName, Quantity, Price, c.CategoryID, c.CategoryName, c.IsHidden, p.ImagePath\n"
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
                Product pro = new Product(rs.getInt("ProductID"), rs.getString(2), rs.getString(3), rs.getInt("Quantity"), rs.getInt("Price"), Category, rs.getString(9), true);
                list.add(pro);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return list;
    }

    // Nhung gi se duoc hien thi trong phan trang
    public List<Product> getAvailableProductsByCategoryPage(int categoryID, int page) {
        List<Product> list = new ArrayList<>();
        String query = "SELECT ProductID, ProductCode, ProductName, Quantity, Price, c.CategoryID, c.CategoryName, c.IsHidden, p.ImagePath\n"
                + "FROM Products p\n"
                + "JOIN Categories c ON p.CategoryID = c.CategoryID\n"
                + "WHERE c.CategoryID = ? AND c.IsHidden = 0\n"
                + "ORDER BY ProductID ASC\n"
                + "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        Object[] params = {
            categoryID,
            (page - 1) * PaginationUtil.NUMBER_OF_ITEMS_PER_PAGE, PaginationUtil.NUMBER_OF_ITEMS_PER_PAGE};

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
                        ),
                        rs.getString(9),
                        true
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
    public int create(String productCore, String productName, int quantity, double price, int categoryId, String coverImg) {
        int check = 0;
        try {
            String sql = "insert into Products (ProductCode,ProductName,Quantity,Price,CategoryID, ImagePath)\n"
                    + "values(?,?,?,?,?,?)";
            PreparedStatement ps = this.getConnection().prepareStatement(sql);
            ps.setObject(1, productCore);
            ps.setObject(2, productName);
            ps.setObject(3, quantity);
            ps.setObject(4, price);
            ps.setObject(5, categoryId);
            ps.setObject(6, coverImg);
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
    public int edit(int prouductId, String productCode, String productName, int quantity, double price, int cateID, String coverImg) {
        try {
            String query = "update  Products\n"
                    + "set ProductCode = ?, ProductName = ?,Quantity = ?,Price= ?, CategoryID = ?,ImagePath = ?\n"
                    + "where ProductID = ?";

            PreparedStatement ps = this.getConnection().prepareStatement(query);
            ps.setObject(1, productCode);
            ps.setObject(2, productName);
            ps.setObject(3, quantity);
            ps.setObject(4, price);
            ps.setObject(5, cateID);
            ps.setObject(6, coverImg);
            ps.setObject(7, prouductId);

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
            String query = "SELECT ProductID, ProductCode, ProductName, Quantity, Price, c.CategoryID, c.CategoryName, c.IsHidden, p.ImagePath, p.isHidden "
                    + "FROM Products p "
                    + "JOIN Categories c ON p.CategoryID = c.CategoryID "
                    + "WHERE ProductID = ?";

            Object[] params = {ProductID};
            ResultSet rs = execSelectQuery(query, params);

            if (rs.next()) {
                Category cat = new Category(rs.getInt(6), rs.getString(7), rs.getBoolean(8));
                return new Product(rs.getInt(1), rs.getString(2), rs.getString(3),
                        rs.getInt(4), rs.getInt(5), cat, rs.getString(9), rs.getBoolean(10));
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

            String query = "SELECT p.ProductID, p.ProductCode, p.ProductName, p.Quantity, p.Price, p.IsHidden, c.CategoryID, c.CategoryName, c.IsHidden, p.ImagePath\n"
                    + "FROM [dbo].[Products] p\n"
                    + "JOIN [dbo].[Categories] c \n"
                    + "ON p.CategoryID = c.CategoryID\n"
                    + "WHERE p.ProductID = ?\n"
                    + "AND p.IsHidden = 0;";
            Object[] params = {productID};

            ResultSet rs = execSelectQuery(query, params);

            if (rs.next()) {
                return new Product(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5), new Category(rs.getInt(7), rs.getString(8), rs.getBoolean(9)), rs.getString(10), true);
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
    public int hide(int productId) {
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

    public int unhide(int productId) {
        String query = "update Products\n"
                + "set IsHidden = 0\n"
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

    /**
     * Đặt đường dẫn của ảnh sản phẩm trong cơ sở dữ liệu.
     *
     * @param productID là id của sản phẩm.
     * @param url là đường dẫn của ảnh.
     *
     * @return 0 nếu đặt đường dẫn thất bại. Khác 0 nếu thành công.
     */
    public int setImgUrl(int productID, String url) {

        try {

            String query = "UPDATE [dbo].[Products]\n"
                    + "SET ImagePath = ?\n"
                    + "WHERE ProductID = ?;";
            Object[] params = {url, productID};

            return execQuery(query, params);

        } catch (SQLException ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return 0;
    }

    public int reduceQuantity(int productId, int reducedNum) {
        String query = "update Products\n"
                + "set Quantity = Quantity - ?\n"
                + "where ProductID = ?";

        Object[] params = {reducedNum, productId};

        try {
            return execQuery(query, params);
        } catch (SQLException ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return 0;
    }

    public int increaseQuantity(int productId, int increasedNum) {
        String query = "update Products\n"
                + "set Quantity = Quantity + ?\n"
                + "where ProductID = ?";

        Object[] params = {increasedNum, productId};

        try {
            return execQuery(query, params);
        } catch (SQLException ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return 0;
    }

    public int increaseQuantity(Order order) {
        int numOfUpdatedRows = 0;
        for (OrderItem item : order.getOrderItems()) {
            numOfUpdatedRows += ProductDAO.this.increaseQuantity(item.getProduct().getProductID(), item.getQuantity());
        }

        return numOfUpdatedRows;
    }

    public int getMaxId() {
        try {
            String getMaxIdQuery = "SELECT MAX(ProductID) FROM Products";
            PreparedStatement maxIdPs = this.getConnection().prepareStatement(getMaxIdQuery);
            ResultSet maxIdRs = maxIdPs.executeQuery();
            if (maxIdRs.next()) {
                return maxIdRs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    public String getImagePathByProductId(int productId) {
        String imagePath = "";
        try {
            String query = "SELECT ImagePath FROM Products WHERE ProductID = ?";
            PreparedStatement ps = this.getConnection().prepareStatement(query);
            ps.setInt(1, productId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                imagePath = rs.getString("ImagePath");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imagePath;
    }
}
