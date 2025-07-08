/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import DAO.CartDAO;
import DAO.CategoryDAO;
import DAO.ProductDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import model.Cart;
import model.Product;
import model.User;
import validate.ProductValidation;

/**
 *
 * @author Vu Minh Khang - CE191371
 */
@WebServlet(name = "CartServlet", urlPatterns = {"/cart"})
public class CartServlet extends HttpServlet {

    private final CartDAO cartDao = new CartDAO();
    private final CategoryDAO categoryDao = new CategoryDAO();
    private final ProductDAO productDao = new ProductDAO();
    private String view;

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
            out.println("<title>Servlet CartServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CartServlet at " + request.getContextPath() + "</h1>");
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

        if (((User) request.getSession().getAttribute("loggedUser")) == null) {
            response.sendRedirect(request.getContextPath() + "/login");
        } else {
            view = request.getParameter("view");

            if (view == null || view.isBlank()) {
                User user = (User) request.getSession().getAttribute("loggedUser");
                List<Cart> list = cartDao.getAll(user.getId());
                request.setAttribute("cartList", list);
                request.getRequestDispatcher("/WEB-INF/users/cart.jsp").forward(request, response);
                return;
            } else if ("edit".equals(view)) {
                int cartId = Integer.parseInt(request.getParameter("id"));
                Cart cart = cartDao.getCartByID(cartId);
                request.setAttribute("cart", cart);
                request.getRequestDispatcher("/WEB-INF/users/edit.jsp").forward(request, response);

            } else if ("delete".equals(view)) {
                int cartId = Integer.parseInt(request.getParameter("id"));
                Cart cart = cartDao.getCartByID(cartId);
                request.setAttribute("cart", cart);
                request.getRequestDispatcher("/WEB-INF/users/delete.jsp").forward(request, response);

            } else if ("order".equals(view)) {
                HttpSession session = request.getSession();
                if (session.getAttribute("cartListToOrder") == null) {
                    session.setAttribute("cartListToOrder", new ArrayList<Cart>());

                }

                request.getRequestDispatcher("/WEB-INF/users/order.jsp").forward(request, response);
                return;
            }

            request.getRequestDispatcher("/WEB-INF/users/cart.jsp").forward(request, response);
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

        if (((User) request.getSession().getAttribute("loggedUser")) == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String action = request.getParameter("action");

        if ("edit".equals(action)) {
            String cartid = request.getParameter("cartId");
            String quantity = request.getParameter("quantity");
            ProductValidation pr = new ProductValidation();
            List<String> checkQuantity = pr.checkQuantity(quantity);
            if (!checkQuantity.isEmpty()) {
                Cart cart = cartDao.getCartByID(Integer.parseInt(cartid));
                request.setAttribute("cart", cart);
                request.setAttribute("checkQuantity", checkQuantity);
                request.getRequestDispatcher("/WEB-INF/users/edit.jsp").forward(request, response);
                return;
            }

            cartDao.edit(Integer.parseInt(cartid), Integer.parseInt(quantity));
            response.sendRedirect(request.getContextPath() + "/cart");
            return;
        } else if ("delete".equals(action)) {
            String cartid = request.getParameter("cartId");
            cartDao.delete(Integer.parseInt(cartid));
            response.sendRedirect(request.getContextPath() + "/cart");
            return;

        } else if ("order".equals(action)) {
            User user = (User) request.getSession().getAttribute("loggedUser");
            List<Cart> cartList = cartDao.getAll(user.getId());
            List<Cart> wantedCartList = new ArrayList<>();
            for (Cart cart : cartList) {
                String autoCheckBox = "isBuy" + cart.getCartItemID();
                if (request.getParameter(autoCheckBox) != null) {
                    wantedCartList.add(cart);

                }
            }

            request.getSession().setAttribute("wantedCartList", wantedCartList);

            response.sendRedirect(request.getContextPath() + "/cart?view=order");
            return;
        }

        response.sendRedirect(request.getContextPath() + "/cart");
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
