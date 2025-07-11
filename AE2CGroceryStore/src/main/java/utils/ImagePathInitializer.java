/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import DAO.CategoryDAO;
import DAO.ProductDAO;
import java.io.File;
import java.util.List;
import model.Category;
import model.Product;

/**
 *
 * @author Vu Minh Khang - CE191371
 */
public class ImagePathInitializer {
    
    public final static String IMAGE_STORAGE_FOLDER = "images";
    public final static String CATEGORY_IMAGE_FOLDER = "category";
    public final static String PRODUCT_IMAGE_FOLDER = "products";
    public final static String PNG_IMAGE_FILE_FORMAT = ".png";
    public static boolean LOAD_IMG_STATUS = false;
    
    /**
     * Set url của ảnh trong database.
     */
    public static void initialize() {
        // Check thử có tồn tại thư mục dự án ko
        File projectFolder = new File(FileIOUtil.getRootPath());
        if (projectFolder.exists() && projectFolder.isDirectory()) {
            
            // Xử lí ảnh của dự án.
            createImgFolderIfNotExist(projectFolder);
            
            // Set trạng thái đặt url thành true.
            LOAD_IMG_STATUS = true;
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

            // Nếu không có thư mục chứa ảnh thì tạo mới.
        } else {
            imgFolder.mkdir();
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
     * Kiểm tra ảnh có trong thư mục ảnh của danh mục hay không.
     *
     * @param categoryImgFolder là thư mục chứa ảnh.
     * @param categoryID là id của danh mục.
     *
     * @return True nếu có chứa ảnh trong thư mục chứa ảnh. False nếu không có tồn
     * tại ảnh.
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
     * @return True nếu có chứa ảnh trong thư mục chứa ảnh. False nếu không có tồn
     * tại ảnh.
     */
    private static boolean checkExistProductImgPath(File productImgFolder, int productID) {
        return new File(productImgFolder, productID + PNG_IMAGE_FILE_FORMAT).exists();
    }
}
