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
import utils.ImagePathInitializer;
import utils.PaginationUtil;

/**
 *
 * @author Vu Minh Khang - CE191371
 */
@WebServlet(name = "HomeServlet", urlPatterns = {"/home"})
public class HomeServlet extends HttpServlet {

    private final CategoryDAO categoryDao = new CategoryDAO();
    private final ProductDAO productDao = new ProductDAO();

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
            out.println("<title>Servlet HomeServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet HomeServlet at " + request.getContextPath() + "</h1>");
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

        // Đặt đường dẫn cho từng ảnh trong database.
        ImagePathInitializer.initialize();

        if (view == null || view.isBlank()) {
            getDataIndex(request);
            request.getRequestDispatcher("index.jsp").forward(request, response);

        } else {

            switch (view) {

                case "category":
                    response.sendRedirect(request.getContextPath() + "/user-category?categoryID=" + request.getParameter("categoryID"));
                    break;

                case "product":
                    response.sendRedirect(request.getContextPath() + "/user-product?productID=" + request.getParameter("productID"));
                    break;

                default:
                    getDataIndex(request);
                    request.getRequestDispatcher("index.jsp").forward(request, response);
                    break;
            }

        }
    }

    /**
     * Lấy danh sách danh mục và danh sách sản phẩm.
     *
     * @param request là yêu cầu người dùng.
     */
    private void getDataIndex(HttpServletRequest request) {
        int countItem = productDao.countItem();
        int totalPages = PaginationUtil.getTotalPages(countItem);
        request.setAttribute("totalPages", totalPages); // Set tổng số page

        int page = 1; // Trang mặc định = 1.
        String pageParam = request.getParameter("page");

        if (pageParam != null && !pageParam.isBlank()) { // check nếu không null thì xử lý logic ở dưới
            try {
                page = Integer.parseInt(pageParam);

                if (page < PaginationUtil.MIN_NUMBER_PAGE) { // check xem nếu page nhỏ hơn min thì page = 1
                    page = PaginationUtil.MIN_NUMBER_PAGE;
                } else if (page > totalPages) { // check nếu page lớn hơn max thì page = max
                    page = totalPages;
                }

            } catch (NumberFormatException ex) { // Nếu khác số thì vào đây
                page = PaginationUtil.MIN_NUMBER_PAGE;
            }
        }

        request.setAttribute("carouselList", productDao.carouselProduct());
        request.setAttribute("categoryList", categoryDao.getAllCategoryAvailable());
        request.setAttribute("productList", productDao.getProductForEachPage(page));
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

        response.sendRedirect(request.getContextPath() + "/home");
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
