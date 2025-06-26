/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import DAO.ProductDAO;
import DAO.CategoryDAO;
import DAO.ReviewDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Category;
import model.ErrorMessage;
import model.User;
import validate.UserInputValidate;

/**
 *
 * @author Vu Minh Khang - CE191371
 */
@WebServlet(name = "UserProductServlet", urlPatterns = {"/user-product"})
public class UserProductServlet extends HttpServlet {

    private final ProductDAO productDao = new ProductDAO();
    private final CategoryDAO categoryDao = new CategoryDAO();
    private final ReviewDAO reviewDao = new ReviewDAO();

    ;

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
            out.println("<title>Servlet UserProductServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UserProductServlet at " + request.getContextPath() + "</h1>");
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

        if (view == null || view.isBlank()) {
            request.setAttribute("categoryList", categoryDao.getAll());
            request.setAttribute("productList", productDao.getAll());
            request.getRequestDispatcher("/WEB-INF/products/show.jsp").forward(request, response);

        } else {

            switch (view) {

                case "category":
                    request.setAttribute("categoryList", categoryDao.getAll());
                    request.setAttribute("productList", productDao.getProductsByCategory(Integer.parseInt(request.getParameter("id"))));
                    request.setAttribute("categoryType", categoryDao.getOneByID(Integer.parseInt(request.getParameter("id"))));
                    request.getRequestDispatcher("/WEB-INF/products/category.jsp").forward(request, response);
                    break;

                case "product":
                    request.setAttribute("product", productDao.getById(Integer.parseInt(request.getParameter("id"))));
                    request.setAttribute("productList", productDao.getProductsByCategory(categoryDao.getCategoryByProductID(Integer.parseInt(request.getParameter("id"))).getCategoryID()));
                    request.setAttribute("rateScore", productDao.getRateScore((Integer.parseInt(request.getParameter("id")))));
                    request.setAttribute("reviewList", reviewDao.getByProductID(Integer.parseInt(request.getParameter("id"))));
                    request.getRequestDispatcher("/WEB-INF/products/product.jsp").forward(request, response);
                    break;

                default:
                    request.setAttribute("categoryList", categoryDao.getAll());
                    request.setAttribute("productList", productDao.getAll());
                    request.getRequestDispatcher("/WEB-INF/products/show.jsp").forward(request, response);
                    break;
            }
        }
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

        HttpSession session = request.getSession();

        if (((User) session.getAttribute("loggedUser")) == null) {
            response.sendRedirect(request.getContextPath() + "/login");
        } else {

            String view = request.getParameter("view");
            String quantity = request.getParameter("quantity");

            request.setAttribute("product", productDao.getById(Integer.parseInt(request.getParameter("id"))));
            request.setAttribute("productList", productDao.getProductsByCategory(categoryDao.getCategoryByProductID(Integer.parseInt(request.getParameter("id"))).getCategoryID()));
            request.setAttribute("rateScore", productDao.getRateScore((Integer.parseInt(request.getParameter("id")))));
            request.setAttribute("reviewList", reviewDao.getByProductID(Integer.parseInt(request.getParameter("id"))));

            if (view == null || view.isBlank()) {
                response.sendRedirect(request.getContextPath() + "/user-product?view=product");
            } else {

                switch (view) {

                    case "cart":

                        if (UserInputValidate.checkEmptyInput(quantity) != null) {
                            request.setAttribute("Error", new ErrorMessage("Please enter something."));
                            request.getRequestDispatcher("/WEB-INF/products/product.jsp").forward(request, response);

                        } else if (UserInputValidate.checkValidIntegerNumber(quantity) != null) {
                            request.setAttribute("Error", new ErrorMessage("Please enter a valid number."));
                            request.getRequestDispatcher("/WEB-INF/products/product.jsp").forward(request, response);

                        } else if (UserInputValidate.checkIntegerNumberInRange(Integer.parseInt(quantity),
                                UserInputValidate.ZERO_VALUE,
                                productDao.getMaxQuantity(Integer.parseInt(request.getParameter("id"))))
                                != null) {
                            request.setAttribute("Error", new ErrorMessage("The cart value too large."));
                            request.getRequestDispatcher("/WEB-INF/products/product.jsp").forward(request, response);

                        } else {
                            String successMsg = "Add to your cart successfully.";
                            request.setAttribute("Success", successMsg);
                            request.getRequestDispatcher("/WEB-INF/products/product.jsp").forward(request, response);
                        }
                        break;

                    default:
                        response.sendRedirect(request.getContextPath() + "/user-product?view=product");
                        break;
                }
            }
        }
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
