/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import DAO.CategoryDAO;
import DAO.ProductDAO;
import DAO.UserDAO;
import java.io.File;
import java.util.List;
import model.Category;
import model.Product;
import model.User;

/**
 *
 * @author Vu Minh Khang - CE191371
 */
public class ImagePathInitializer {

    public final static String IMAGE_STORAGE_FOLDER = "images";
    public final static String CATEGORY_IMAGE_FOLDER = "category";
    public final static String PRODUCT_IMAGE_FOLDER = "products";
    public final static String USER_IMAGE_FOLDER = "users";
    public final static String PNG_IMAGE_FILE_FORMAT = ".png";
    public final static String JPG_IMAGE_FILE_FORMAT = ".jpg";
    public final static String JPEG_IMAGE_FILE_FORMAT = ".jpeg";
    public final static String JFIF_IMAGE_FILE_FORMAT = ".jfif";
    public final static String SVG_IMAGE_FILE_FORMAT = ".svg";
    public final static String GIF_IMAGE_FILE_FORMAT = ".gif";
    public static boolean status = false;

    /**
     * Set url của ảnh trong database.
     */
    public static void initialize() {
        
        // Nếu đã chạy rồi thì không chạy nữa
        if (status) {
            return;
        }
        
        // Check thử có tồn tại thư mục dự án ko
        File projectFolder = new File(FileIOUtil.getRootPath());
        if (projectFolder.exists() && projectFolder.isDirectory()) {

            // Xử lí ảnh của dự án.
            createImgFolderIfNotExist(projectFolder);

            // Set trạng thái đặt url thành true.
            status = true;
        }
    }

    /**
     * Tạo folder chứa toàn bộ ảnh của dự án nếu không tồn tại.
     *
     * @param projectFolder
     */
    private static void createImgFolderIfNotExist(File projectFolder) {

        // Check thử có tồn tại thư mục chứa ảnh ko
        File imgFolder = new File(projectFolder, IMAGE_STORAGE_FOLDER);

        // Nếu có thư mục thì load ảnh
        if (imgFolder.exists() && imgFolder.isDirectory()) {
            createProductImgFolderIfNotExist(imgFolder);
            createCategoryImgFolderIfNotExist(imgFolder);
            createUserImgFolderIfNotExist(imgFolder);

            // Nếu không có thư mục chứa ảnh thì tạo mới.
        } else {
            imgFolder.mkdir();
        }
    }

    private static void createUserImgFolderIfNotExist(File imgFolder) {

        // Check thử có tồn tại thư mục chứa ảnh ko
        File userImgFolder = new File(imgFolder, USER_IMAGE_FOLDER);

        // Nếu có thư mục thì load ảnh.
        if (userImgFolder.exists() && userImgFolder.isDirectory()) {
            setUserImg(userImgFolder);

            // Nếu không có thư mục chứa ảnh thì tạo mới.
        } else {
            userImgFolder.mkdir();
        }
    }

    /**
     * Tạo thư mục chứa toàn bộ ảnh danh mục nếu không tồn tại.
     *
     * @param imgFolder là thư mục chứa ảnh của dự án.
     */
    private static void createCategoryImgFolderIfNotExist(File imgFolder) {

        // Check thử có tồn tại thư mục chứa ảnh ko
        File categoryImgFolder = new File(imgFolder, CATEGORY_IMAGE_FOLDER);

        // Nếu có thư mục thì load ảnh.
        if (categoryImgFolder.exists() && categoryImgFolder.isDirectory()) {
            setCategoryImg(categoryImgFolder);

            // Nếu không có thư mục chứa ảnh thì tạo mới.
        } else {
            categoryImgFolder.mkdir();
        }
    }

    /**
     * Tạo thư mục chứa toàn bộ ảnh sản phẩm nếu không tồn tại.
     *
     * @param imgFolder là thư mục chứa ảnh của dự án.
     */
    private static void createProductImgFolderIfNotExist(File imgFolder) {

        // Check thử có tồn tại thư mục chứa ảnh ko
        File productImgFolder = new File(imgFolder, PRODUCT_IMAGE_FOLDER);

        // Nếu có thư mục thì load ảnh.
        if (productImgFolder.exists() && productImgFolder.isDirectory()) {
            setProductImg(productImgFolder);

            // Nếu không có thư mục chứa ảnh thì tạo mới.
        } else {
            productImgFolder.mkdir();
        }
    }

    /**
     * Tạo đường dẫn cho ảnh người dùng trong database nếu có tồng tại. Tên ảnh
     * = số id danh mục + .png.
     *
     * @param userImgFolder là thư mục chứ ảnh của sản phẩm.
     */
    private static void setUserImg(File userImgFolder) {
        UserDAO userDao = new UserDAO();

        // Lấy danh sách người dùng.
        List<User> userList = userDao.getAllUserID();

        // Check từng người dùng trong database.
        for (User user : userList) {

            // Nếu có tồn tại ảnh thì set đường dẫn của nó trong database.
            if (checkExistUserImgPath(userImgFolder, user.getId())) {
                userDao.setImgUrl(user.getId(), USER_IMAGE_FOLDER + "/" + user.getId() + PNG_IMAGE_FILE_FORMAT);
            }
        }
    }

    /**
     * Tạo đường dẫn cho ảnh danh mục trong database nếu có tồng tại. Tên ảnh =
     * số id danh mục + .png.
     *
     * @param categoryImgFolder là thư mục chứ ảnh của sản phẩm.
     */
    private static void setCategoryImg(File categoryImgFolder) {
        CategoryDAO categoryDao = new CategoryDAO();

        // Lấy danh sách sản phẩm.
        List<Category> categoryList = categoryDao.getAllCategoryID();

        // Check từng sản phẩm trong database.
        for (Category category : categoryList) {

            // Nếu có tồn tại ảnh thì set đường dẫn của nó trong database.
            if (checkExistCategoryImgPath(categoryImgFolder, category.getCategoryID())) {
                categoryDao.setImgUrl(category.getCategoryID(), CATEGORY_IMAGE_FOLDER + "/" + category.getCategoryID() + PNG_IMAGE_FILE_FORMAT);
            }
        }
    }

    /**
     * Tạo đường dẫn cho ảnh sản phẩm trong database nếu có tồng tại. Tên ảnh =
     * số id sản phẩm + .png.
     *
     * @param productImgFolder là thư mục chứ ảnh của sản phẩm.
     */
    private static void setProductImg(File productImgFolder) {
        ProductDAO productDao = new ProductDAO();

        // Lấy danh sách sản phẩm.
        List<Product> productList = productDao.getAllProductID();

        // Check từng sản phẩm trong database.
        for (Product product : productList) {

            // Nếu có tồn tại ảnh thì set đường dẫn của nó trong database.
            if (checkExistProductImgPath(productImgFolder, product.getProductID())) {
                productDao.setImgUrl(product.getProductID(), PRODUCT_IMAGE_FOLDER + "/" + product.getProductID() + PNG_IMAGE_FILE_FORMAT);
            }
        }
    }
    
    /**
     * Kiểm tra ảnh có trong thư mục ảnh của người dùng hay không.
     *
     * @param userImgFolder là thư mục chứa ảnh.
     * @param userID là id của người dùng.
     *
     * @return True nếu có chứa ảnh trong thư mục chứa ảnh. False nếu không có
     * tồn tại ảnh.
     */
    private static boolean checkExistUserImgPath(File userImgFolder, int userID) {
        return new File(userImgFolder, userID + PNG_IMAGE_FILE_FORMAT).exists();
    }

    /**
     * Kiểm tra ảnh có trong thư mục ảnh của danh mục hay không.
     *
     * @param categoryImgFolder là thư mục chứa ảnh.
     * @param categoryID là id của danh mục.
     *
     * @return True nếu có chứa ảnh trong thư mục chứa ảnh. False nếu không có
     * tồn tại ảnh.
     */
    private static boolean checkExistCategoryImgPath(File categoryImgFolder, int categoryID) {
        return new File(categoryImgFolder, categoryID + PNG_IMAGE_FILE_FORMAT).exists();
    }

    /**
     * Kiểm tra ảnh có trong thư mục ảnh của sản phẩm hay không.
     *
     * @param productImgFolder là thư mục chứa ảnh.
     * @param productID là id của sản phẩm.
     *
     * @return True nếu có chứa ảnh trong thư mục chứa ảnh. False nếu không có
     * tồn tại ảnh.
     */
    private static boolean checkExistProductImgPath(File productImgFolder, int productID) {
        return new File(productImgFolder, productID + PNG_IMAGE_FILE_FORMAT).exists();
    }
}
