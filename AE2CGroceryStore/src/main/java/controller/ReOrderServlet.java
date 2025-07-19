/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import DAO.CartDAO;
import DAO.OrderDAO;
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
import java.util.LinkedList;
import java.util.List;
import model.Cart;
import model.Order;
import model.OrderItem;
import model.Product;
import model.User;

/**
 *
 * @author Nguyen Ho Phuoc An - CE190747
 */
@WebServlet(name = "ReOrderServlet", urlPatterns = {"/re-order"})
public class ReOrderServlet extends HttpServlet {

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
            out.println("<title>Servlet ReOrderServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ReOrderServlet at " + request.getContextPath() + "</h1>");
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
        HttpSession session = request.getSession();
        OrderDAO orderDAO = new OrderDAO();
        CartDAO cartDAO = new CartDAO();
        ProductDAO productDAO = new ProductDAO();
        
        User loggedUser = (User) session.getAttribute("loggedUser");
        
        String strOrderId = request.getParameter("orderId");
        
        try {
            int orderId = Integer.parseInt(strOrderId);
            Order order = orderDAO.getOrderById(orderId);
            
            List<Cart> wantedCartList = new LinkedList<>();
            
            for (OrderItem item : order.getOrderItems()) {
                Product product = productDAO.getById(item.getProduct().getProductID());
                if (!product.isIsHidden() && product.getQuantity() > 0) {
                    cartDAO.addNewProductToCart(loggedUser.getId(), product.getProductID(), Math.min(product.getQuantity(), item.getQuantity()));
                    wantedCartList.add(cartDAO.getLastedCartByUserID(loggedUser.getId()));
                }
            }
            
            session.removeAttribute("wantedCartList");
            if (!wantedCartList.isEmpty()) {
                session.setAttribute("wantedCartList", wantedCartList);
            }
        } catch (NumberFormatException nfe) {
            response.sendRedirect(request.getContextPath() + "/error-page");
        }        
        response.sendRedirect(request.getContextPath() + "/new-order");
        
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
        processRequest(request, response);
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
