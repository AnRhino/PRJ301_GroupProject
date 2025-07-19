/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import DAO.CategoryDAO;
import DAO.ProductDAO;
import adminController.CategoriesCreateServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.File;
import model.Category;
import model.Product;
import validate.ProductValidation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.ErrorMessage;
import utils.FileIOUtil;
import utils.MessageConstants;

/**
 * Servlet Controller xử lý các hành động liên quan đến sản phẩm.
 *
 * @author Phan Duc Tho
 */
@WebServlet(name = "ProductServlet", urlPatterns = {"/admin/product"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50 // 50MB
)
public class ProductServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ProductValidation validation = new ProductValidation();
        String view = request.getParameter("view");
        ProductDAO productDAO = new ProductDAO();

        if (view == null || view.isBlank() || view.equals("list")) {
            // Hiển thị danh sách sản phẩm
            request.setAttribute("list", productDAO.getAll());
            request.getRequestDispatcher("/WEB-INF/adminFeatures/product/list.jsp").forward(request, response);

        } else if (view.equals("create")) {
            // Hiển thị form tạo sản phẩm
            CategoryDAO cateDAO = new CategoryDAO();
            List<Category> categories = cateDAO.getAll();
            request.setAttribute("cate", categories);
            request.getRequestDispatcher("/WEB-INF/adminFeatures/product/create.jsp").forward(request, response);

        } else if (view.equals("delete")) {
            // Hiển thị xác nhận xoá sản phẩm

            boolean checkId = validation.checkProductId(request.getParameter("id"), productDAO.getAll());
            if (!checkId) {
                int id = Integer.parseInt(request.getParameter("id"));
                request.setAttribute("pro", productDAO.getById(id));
                request.getRequestDispatcher("/WEB-INF/adminFeatures/product/delete.jsp").forward(request, response);
            } else {
                request.getRequestDispatcher("/WEB-INF/errorPage/errorPage.jsp").forward(request, response);
            }
        } else if (view.equals("edit")) {
            // Hiển thị form chỉnh sửa sản phẩm

            boolean checkId = validation.checkProductId(request.getParameter("id"), productDAO.getAll());
            if (!checkId) {
                int id = Integer.parseInt(request.getParameter("id"));
                request.setAttribute("pro", productDAO.getById(id));
                CategoryDAO cateDAO = new CategoryDAO();
                List<Category> categories = cateDAO.getAll();
                request.setAttribute("cate", categories);
                request.getRequestDispatcher("/WEB-INF/adminFeatures/product/edit.jsp").forward(request, response);
            } else {
                request.getRequestDispatcher("/WEB-INF/errorPage/errorPage.jsp").forward(request, response);
            }
        } else if (view.equals("hidden")) {
            // Hiển thị xác nhận xoá sản phẩm

            boolean checkId = validation.checkProductId(request.getParameter("id"), productDAO.getAll());
            if (!checkId) {
                int id = Integer.parseInt(request.getParameter("id"));
                request.setAttribute("pro", productDAO.getById(id));
                request.getRequestDispatcher("/WEB-INF/adminFeatures/product/hidden.jsp").forward(request, response);
            } else {
                request.getRequestDispatcher("/WEB-INF/errorPage/errorPage.jsp").forward(request, response);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        ProductDAO productDAO = new ProductDAO();
        ProductValidation productValidation = new ProductValidation();
        List<Product> listProduct = productDAO.getAll();

        // Lấy dữ liệu từ form
        String productCode = request.getParameter("productCode");
        String productName = request.getParameter("productName");
        String quantityStr = request.getParameter("quantity");
        String priceStr = request.getParameter("price");
        String categoryStr = request.getParameter("category");

        // Xử lý tạo mới sản phẩm
        if ("create".equals(action)) {
            boolean hasErrors = false;
            String errorMessage = null;
            String imagePath = "";
            List<String> errors = new ArrayList<>();

            // Validate form input
            errors.addAll(productValidation.checkProductCode(productCode, listProduct));
            errors.addAll(productValidation.checkProductName(productName));
            errors.addAll(productValidation.checkQuantity(quantityStr));
            errors.addAll(productValidation.checkPrice(priceStr));
            errors.addAll(productValidation.checkCategoryID(categoryStr));

            if (!errors.isEmpty()) {
                // Đổ dữ liệu lại khi có lỗi
                setFormAttributes(request, productCode, productName, quantityStr, priceStr, categoryStr, errors);
                request.getRequestDispatcher("/WEB-INF/adminFeatures/product/create.jsp").forward(request, response);
                return;
            }

            // Tiếp tục xử lý nếu không có lỗi validate form
            try {
                Part coverImgPart = request.getPart("coverImg");

                if (coverImgPart == null || coverImgPart.getSize() == 0) {
                    hasErrors = true;
                    errorMessage = "Please choose Image for upload";
                } else {
                    int nextProductID = productDAO.getMaxId() + 1;

                    String result = processImageUpload(coverImgPart, nextProductID);
                    if (result != null) {
                        hasErrors = true;
                        errorMessage = result;
                    } else {
                        imagePath = "products/" + nextProductID
                                + coverImgPart.getSubmittedFileName()
                                        .substring(coverImgPart.getSubmittedFileName().lastIndexOf("."))
                                        .toLowerCase();
                    }
                }

            } catch (Exception e) {
                hasErrors = true;
                errorMessage = MessageConstants.ERROR_IMAGE_UPLOAD_MESSAGE + e.getMessage();
                Logger.getLogger(CategoriesCreateServlet.class.getName())
                        .log(Level.SEVERE, "Error in image upload", e);
            }

            // Nếu có lỗi ảnh
            if (hasErrors) {
                if (errorMessage != null) {
                    request.setAttribute("imageError", new ErrorMessage(errorMessage));
                }
                setFormAttributes(request, productCode, productName, quantityStr, priceStr, categoryStr, errors);
                request.getRequestDispatcher("/WEB-INF/adminFeatures/product/create.jsp").forward(request, response);
                return;
            }

            // Nếu không có lỗi thi tạo sản phẩm
            productDAO.create(productCode, productName, Integer.parseInt(quantityStr),
                    Double.parseDouble(priceStr), Integer.parseInt(categoryStr), imagePath);

            response.sendRedirect(request.getContextPath() + "/admin/product?view=list");
        } else if (action.equals("delete")) {

            int id = Integer.parseInt(request.getParameter("ProductID"));
            productDAO.delete(id);
            response.sendRedirect(request.getContextPath() + "/admin/product?view=list");
            // Xử lý chỉnh sửa sản phẩm
        } else if (action.equals("edit")) {
            String idStr = request.getParameter("ProductID");

            if (idStr == null || idStr.isEmpty()) {
                request.getRequestDispatcher("/WEB-INF/errorPage/errorPage.jsp").forward(request, response);
            }
            if (productValidation.checkProductID(idStr, listProduct)) {
                request.getRequestDispatcher("/WEB-INF/errorPage/errorPage.jsp").forward(request, response);
            }

            int id = Integer.parseInt(idStr);

            List<String> errors = new ArrayList<>();
            errors.addAll(productValidation.checkProductCodeEdit(productCode, id, listProduct));
            errors.addAll(productValidation.checkProductName(productName));
            errors.addAll(productValidation.checkQuantity(quantityStr));
            errors.addAll(productValidation.checkPrice(priceStr));
            errors.addAll(productValidation.checkCategoryID(categoryStr));

            if (!errors.isEmpty()) {
                // Đổ dữ liệu lại khi có lỗi
                request.setAttribute("oldCode", productCode);
                request.setAttribute("oldName", productName);
                request.setAttribute("oldQuantity", quantityStr);
                request.setAttribute("oldPrice", priceStr);
                request.setAttribute("oldCate", categoryStr);
                request.setAttribute("errorMessage", errors);

                // Phải gán lại đối tượng Product cũ để JSP hiển thị dữ liệu fallback
                request.setAttribute("pro", productDAO.getById(id));

                // Gán lại danh sách category
                CategoryDAO cateDAO = new CategoryDAO();
                request.setAttribute("cate", cateDAO.getAll());

                // Quay lại trang edit
                request.getRequestDispatcher("/WEB-INF/adminFeatures/product/edit.jsp").forward(request, response);
                return;
            }

            // Nếu hợp lệ thì cập nhật database
            productDAO.edit(id, productCode, productName, Integer.parseInt(quantityStr), Double.parseDouble(priceStr), Integer.parseInt(categoryStr));
            response.sendRedirect(request.getContextPath() + "/admin/product?view=list");
        } else if (action.equals("hidden")) {

            int id = Integer.parseInt(request.getParameter("id"));
            productDAO.hidden(id);
            response.sendRedirect(request.getContextPath() + "/admin/product?view=list");
            // Xử lý chỉnh sửa sản phẩm
        }
    }

    private void setFormAttributes(HttpServletRequest request, String code, String name,
            String quantity, String price, String cate, List<String> errors) {
        request.setAttribute("PCode", code);
        request.setAttribute("PName", name);
        request.setAttribute("PQuantity", quantity);
        request.setAttribute("PPrice", price);
        request.setAttribute("PCate", cate);
        request.setAttribute("errorMessages", errors);
        CategoryDAO cateDAO = new CategoryDAO();
        request.setAttribute("cate", cateDAO.getAll());
    }

    private String processImageUpload(Part coverImgPart, int categoryID) {
        try {
            String[] allowedExtensions = {".jpg", ".png", ".jpeg"};
            File folder = new File(FileIOUtil.getRootPath() + "images/products");

            // Create directory if it doesn't exist
            if (!folder.exists() && !folder.mkdirs()) {
                return MessageConstants.UNEXIST_DIRECTORY_MESSAGE;
            }

            // Validate file name
            String submittedFileName = coverImgPart.getSubmittedFileName();
            if (submittedFileName == null || submittedFileName.isBlank()) {
                return MessageConstants.NO_NAME_FILE_MESSAGE;
            }

            // Validate file extension
            int dotIndex = submittedFileName.lastIndexOf(".");
            if (dotIndex <= 0) {
                return MessageConstants.INVALID_FILE_NAME_MESSAGE + submittedFileName;
            }

            String fileExtension = submittedFileName.substring(dotIndex).toLowerCase();
            if (!Arrays.asList(allowedExtensions).contains(fileExtension)) {
                return MessageConstants.PREFIX_UNALLOWED_EXTENSION_MESSAGE + Arrays.toString(allowedExtensions) + MessageConstants.POSTFIX_UNALLOWED_EXTENSION_MESSAGE;
            }

            // Process file upload
            String newFilename = categoryID + fileExtension;
            File newFile = new File(folder, newFilename);

            // Delete existing file if it exists
            if (newFile.exists() && !newFile.delete()) {
                return MessageConstants.FAILED_DELETE_FILE_MESSAGE;
            }

            // Save new file
            FileIOUtil.fileUploader(coverImgPart, newFile);

            return null; // No error

        } catch (Exception e) {
            Logger.getLogger(CategoriesCreateServlet.class.getName())
                    .log(Level.SEVERE, "Error processing image upload", e);
            return MessageConstants.UNKNOWN_FILE_ERROR_MESSAGE + e.getMessage();
        }
    }

    @Override
    public String getServletInfo() {
        return "ProductServlet handles product-related actions including create, edit, delete, and display.";
    }
}
