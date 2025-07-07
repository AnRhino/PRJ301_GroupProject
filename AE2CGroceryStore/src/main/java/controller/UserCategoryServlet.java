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

/**
 *
 * @author Vu Minh Khang - CE191371
 */
@WebServlet(name = "UserCategoryServlet", urlPatterns = {"/user-category"})
public class UserCategoryServlet extends HttpServlet {
    
    private final ProductDAO productDao = new ProductDAO();
    private final CategoryDAO categoryDao = new CategoryDAO();

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
        String idParam = request.getParameter("id");
        
        if (view == null || idParam == null || view.isBlank() || idParam.isBlank()) {
            response.sendRedirect("index.jsp");
            
        } else {
            
            try {
                
                int id = Integer.parseInt(idParam);

                switch (view) {

                    case "category":
                        response.sendRedirect(request.getContextPath() + "/user-category?id=" + id);
                        break;

                    case "product":
                        response.sendRedirect(request.getContextPath() + "/user-product?id=" + id);
                        break;

                    default:
                        response.sendRedirect("index.jsp");
                        break;
                }

            } catch (NumberFormatException ex) {
                response.sendRedirect("index.jsp");
            }
        }
    }

    /**
     * Lấy thông tin của category và trả về cho người dùng.
     *
     * @param request servlet request
     */
    private void getCategoryInfo(HttpServletRequest request) {
        request.setAttribute("categoryList", categoryDao.getAll());
        request.setAttribute("productList", productDao.getProductsByCategory(Integer.parseInt(request.getParameter("id"))));
        request.setAttribute("categoryType", categoryDao.getOneByID(Integer.parseInt(request.getParameter("id"))));
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
