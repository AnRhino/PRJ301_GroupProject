/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import DAO.CategoryDAO;
import DAO.ProductDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.PaginationUtil;
import validate.InputValidate;

/**
 *
 * @author Vu Minh Khang - CE191371
 */
@WebServlet(name = "UserCategoryServlet", urlPatterns = {"/user-category"})
public class UserCategoryServlet extends HttpServlet {

    private ProductDAO productDao;
    private CategoryDAO categoryDao;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet UserCategoryServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UserCategoryServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//        processRequest(request, response);

        String view = request.getParameter("view");
        String categoryIDParam = request.getParameter("categoryID");
        String productIDParam = request.getParameter("productID");

        // Nếu view null hoặc rỗng.
        if (view == null || view.isBlank()) {
            handleErrorWhenExcute(request, response);

            // Nếu view có gì đó.
        } else {
            
            // Nếu id của sản phẩm và id của danh mục đều không hợp lệ.
            if (checkEmptyID(categoryIDParam, productIDParam)) {
                handleErrorWhenExcute(request, response);
                return;
            }
            
            // Tạo DAO.
            productDao = new ProductDAO();
            categoryDao = new CategoryDAO();

            // Xử lí theo yêu cầu người dùng.
            switch (view) {

                case "category": // Nếu người dùng chọn 1 danh mục.
                    // Check id danh mục có hợp lệ hay không.
                    if (!checkValidCategoryID(categoryIDParam)) {
                        handleErrorWhenExcute(request, response);
                        return;
                    }
                    // Hiện ra danh mục nếu hợp lệ.
                    showCategory(request, response, Integer.parseInt(categoryIDParam));
                    break;

                case "product": // Nếu người dùng chọn 1 sản phẩm.
                    response.sendRedirect(request.getContextPath() + "/user-product?productID=" + productIDParam);
                    break;

                default: // Nếu view rỗng.
                    handleErrorWhenExcute(request, response);
                    break;
            }
        }
    }

    /**
     * Kiểm tra id của product và category có null không.
     *
     * @param productIDParam là id của sản phẩm.
     * @param categoryIDParam là id của danh mục.
     *
     * @return True nếu id cả 2 null. False nếu không.
     */
    private boolean checkEmptyID(String productIDParam, String categoryIDParam) {
        return (productIDParam == null && categoryIDParam == null);
    }

    /**
     * Kiểm tra id của category.
     *
     * @param categoryIDParam là id của category.
     *
     * @return True nếu id hợp lệ. False nếu id không hợp lệ.
     */
    private boolean checkValidCategoryID(String categoryIDParam) {

        // Kiểm tra id của category có null hoặc rỗng không.
        if (InputValidate.checkEmptyInput(categoryIDParam)) {
            return false;

            // Kiểm tra id của category có phải là số không.
        } else if (InputValidate.checkValidIntegerNumber(categoryIDParam)) {
            return false;

            // Kiểm tra id của category có trong phạm vi hợp lệ không.
        } else if (InputValidate.checkIntegerNumberInRange(Integer.parseInt(categoryIDParam), InputValidate.ZERO_VALUE, categoryDao.getMaxId())) {
            return false;

            // Nếu tất cả hợp lệ.
        } else {
            return true;
        }
    }

    /**
     * Xem danh mục mà người dùng mong muốn.
     *
     * @param request là yêu cầu người dùng.
     * @param response là phản hồi người dùng.
     *
     * @throws ServletException
     * @throws IOException
     */
    private void showCategory(HttpServletRequest request, HttpServletResponse response, int categoryID) throws ServletException, IOException {
        getCategoryInfo(request, categoryID);
        request.getRequestDispatcher("/WEB-INF/products/category.jsp").forward(request, response);
    }

    /**
     * Xử lí người dùng truy cập vào category mà id không hợp lệ.
     *
     * @param response là phản hồi của người dùng.
     *
     * @throws IOException
     * @throws ServletException
     */
    private void handleErrorWhenExcute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        // Chuyển hướng người dùng đến nơi muốn hiện lỗi.
        response.sendRedirect(request.getContextPath() + "/error-page");
    }

    /**
     * Lấy thông tin của category và trả về cho người dùng.
     *
     * @param request là yêu cầu người dùng.
     * @param categoryID là id của danh mục.
     */
    private void getCategoryInfo(HttpServletRequest request, int categoryID) {
        int countItem = productDao.countItemByCategory(categoryID);
        int totalPages = PaginationUtil.getTotalPages(countItem);
        request.setAttribute("totalPages", totalPages); // Set tổng số page

        int page = 1; // Trang mặc định = 1.
        String pageParam = request.getParameter("page");

        if (pageParam != null && !pageParam.isBlank()) { // check nếu không null thì xử lý logic ở dưới
            try {
                page = Integer.parseInt(pageParam);

                if (page < 1) { // check xem nếu page nhỏ hơn min thì page = 1
                    page = 1;
                } else if (page > totalPages) { // check nếu page lớn hơn max thì page = max
                    page = totalPages;
                }

            } catch (NumberFormatException ex) { // Nếu khác số thì vào đây
                page = 1;
            }
        }

        request.setAttribute("currentPage", page);
        request.setAttribute("categoryList", categoryDao.getAllCategoryAvailable());
        request.setAttribute("productList", productDao.getAvailableProductsByCategoryPage(categoryID, page));
        request.setAttribute("categoryType", categoryDao.getAvailableOneByID(categoryID));
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//        processRequest(request, response);

        String categoryIDParam = request.getParameter("categoryID");

        // Xử lí nếu id danh mục không hợp lệ.
        if (!checkValidCategoryID(categoryIDParam)) {
            handleErrorWhenExcute(request, response);
            return;
        }

        response.sendRedirect(request.getContextPath() + "/user-category?categoryID=" + request.getParameter("categoryID"));
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
