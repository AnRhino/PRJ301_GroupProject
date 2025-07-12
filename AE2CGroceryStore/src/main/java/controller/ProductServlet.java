/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import DAO.CategoryDAO;
import DAO.ProductDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Category;
import model.Product;
import validate.ProductValidation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Servlet Controller xử lý các hành động liên quan đến sản phẩm.
 *
 * @author Phan Duc Tho
 */
@WebServlet(name = "ProductServlet", urlPatterns = {"/product"})
public class ProductServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String view = request.getParameter("view");
        ProductDAO productDAO = new ProductDAO();

        if (view == null || view.isBlank() || view.equals("list")) {
            // Hiển thị danh sách sản phẩm
            request.setAttribute("list", productDAO.getAll());
            request.getRequestDispatcher("/WEB-INF/product/list.jsp").forward(request, response);

        } else if (view.equals("create")) {
            // Hiển thị form tạo sản phẩm
            CategoryDAO cateDAO = new CategoryDAO();
            List<Category> categories = cateDAO.getAll();
            request.setAttribute("cate", categories);
            request.getRequestDispatcher("/WEB-INF/product/create.jsp").forward(request, response);

        } else if (view.equals("delete")) {
            // Hiển thị xác nhận xoá sản phẩm
            int id = Integer.parseInt(request.getParameter("id"));
            request.setAttribute("pro", productDAO.getById(id));
            request.getRequestDispatcher("/WEB-INF/product/delete.jsp").forward(request, response);

        } else if (view.equals("edit")) {
            // Hiển thị form chỉnh sửa sản phẩm
            int id = Integer.parseInt(request.getParameter("id"));
            request.setAttribute("pro", productDAO.getById(id));
            CategoryDAO cateDAO = new CategoryDAO();
            List<Category> categories = cateDAO.getAll();
            request.setAttribute("cate", categories);
            request.getRequestDispatcher("/WEB-INF/product/edit.jsp").forward(request, response);
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
        String productCode = request.getParameter("productCore");
        String productName = request.getParameter("productName");
        String quantityStr = request.getParameter("quantity");
        String priceStr = request.getParameter("price");
        String categoryStr = request.getParameter("categogy");

        // Xử lý tạo mới sản phẩm
        if ("create".equals(action)) {
            List<String> errors = new ArrayList<>();
            errors.addAll(productValidation.checkProductCode(productCode, listProduct));
            errors.addAll(productValidation.checkProductName(productName));
            errors.addAll(productValidation.checkQuantity(quantityStr));
            errors.addAll(productValidation.checkPrice(priceStr));
            errors.addAll(productValidation.checkCategoryID(categoryStr));

            if (!errors.isEmpty()) {
                // Đổ dữ liệu lại khi có lỗi
                setFormAttributes(request, productCode, productName, quantityStr, priceStr, categoryStr, errors);
                request.getRequestDispatcher("/WEB-INF/product/create.jsp").forward(request, response);
                return;
            }

            // Thêm sản phẩm
            productDAO.create(productCode, productName, Integer.parseInt(quantityStr),
                    Double.parseDouble(priceStr), Integer.parseInt(categoryStr));
            response.sendRedirect(request.getContextPath() + "/product?view=list");
        } else if (action.equals("delete")) {

            int id = Integer.parseInt(request.getParameter("id"));
            productDAO.delete(id);
            response.sendRedirect(request.getContextPath() + "/product?view=list");
            // Xử lý chỉnh sửa sản phẩm
        } else if (action.equals("edit")) {

            int id = Integer.parseInt(request.getParameter("id"));
            List<String> errors = new ArrayList<>();
            errors.addAll(productValidation.checkProductCodeEdit(productCode, id, listProduct));
            errors.addAll(productValidation.checkProductName(productName));
            errors.addAll(productValidation.checkQuantity(quantityStr));
            errors.addAll(productValidation.checkPrice(priceStr));
            errors.addAll(productValidation.checkCategoryID(categoryStr));

            if (!errors.isEmpty()) {
                // Đổ dữ liệu lại khi có lỗi
                setFormAttributes(request, productCode, productName, quantityStr, priceStr, categoryStr, errors);
                request.getRequestDispatcher("/WEB-INF/product/edit.jsp").forward(request, response);
                return;
            }

            // Nếu hợp lệ thì cập nhật database
            productDAO.edit(id, productCode, productName, Integer.parseInt(quantityStr), Double.parseDouble(priceStr), Integer.parseInt(categoryStr));
            response.sendRedirect(request.getContextPath() + "/product?view=list");
        }
    }

    private void setFormAttributes(HttpServletRequest request, String code, String name,
            String quantity, String price, String cate, List<String> errors) {
        request.setAttribute("PCode", code);
        request.setAttribute("PName", name);
        request.setAttribute("PQuantity", quantity);
        request.setAttribute("PPrice", price);
        request.setAttribute("PCate", cate);
        request.setAttribute("errorMessage", errors);
        CategoryDAO cateDAO = new CategoryDAO();
        request.setAttribute("cate", cateDAO.getAll());
    }

    @Override
    public String getServletInfo() {
        return "ProductServlet handles product-related actions including create, edit, delete, and display.";
    }
}
