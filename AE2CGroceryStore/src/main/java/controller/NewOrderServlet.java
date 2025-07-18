/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import DAO.CartDAO;
import DAO.DiscountCodeDAO;
import DAO.OrderDAO;
import DAO.OrderItemDAO;
import DAO.ProductDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.LinkedList;
import java.util.List;
import model.Cart;
import model.DiscountCode;
import model.ErrorMessage;
import model.Order;
import model.OrderItem;
import model.User;
import utils.MessageConstants;

/**
 *
 * @author Nguyen Ho Phuoc An - CE190747
 */
@WebServlet(name = "NewOrderServlet", urlPatterns = {"/new-order"})
public class NewOrderServlet extends HttpServlet {

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
            out.println("<title>Servlet OrderServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet OrderServlet at " + request.getContextPath() + "</h1>");
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
        DiscountCodeDAO dao = new DiscountCodeDAO();
        User loggedUser = (User) request.getSession().getAttribute("loggedUser");
        
        List<DiscountCode> discountCodes = dao.getAllUsableCode(loggedUser.getId());
        request.setAttribute("discountCodes", discountCodes);
        
        request.getRequestDispatcher("/WEB-INF/order/create.jsp").forward(request, response);
        
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
        OrderDAO orderDAO = new OrderDAO();
        CartDAO cartDAO = new CartDAO();
        OrderItemDAO orderItemDAO = new OrderItemDAO();
        ProductDAO productDAO = new ProductDAO();
        HttpSession session = request.getSession();
        ErrorMessage errorMessage = new ErrorMessage(MessageConstants.UNKNOWN_ERROR_MESSAGE);
        int numberOfNewRows = 0;

        // Get data from session
        User loggedUser = (User) session.getAttribute("loggedUser");
        List<Cart> cartItems = (List) session.getAttribute("wantedCartList");

        // Get data from the view's form
        String strDeliveryDate = request.getParameter("delivery-date");
        String strDiscountCodeId = request.getParameter("discount-code-id");
        String strPhoneNumber = request.getParameter("phone-number");
        String strAddress = request.getParameter("address");
        
        try {
            int userId = loggedUser.getId();
            // Parse to right datatype
            // Format: yyyy-mm-dd
            LocalDateTime deliveryDate = LocalDate.parse(strDeliveryDate,
                    DateTimeFormatter.ISO_LOCAL_DATE).atStartOfDay();
            // If Delivery Date before today, auto set it is today
            if (deliveryDate.isBefore(LocalDate.now().atStartOfDay())) {
                deliveryDate = LocalDate.now().atStartOfDay();
            }
            
            // If cannot parse the discount code id, set it null
            Integer discountCodeId;
            try {
                discountCodeId = Integer.valueOf(strDiscountCodeId);
            } catch (NumberFormatException nfe) {
                discountCodeId = null;
            }

            // Create new order, save it into database
            numberOfNewRows = 0;
            numberOfNewRows = orderDAO.createOrder(userId, deliveryDate,
                    discountCodeId, strPhoneNumber, strAddress);
            if (numberOfNewRows == 0) {
                throw new Exception(MessageConstants.ERROR_CREATE_NEW_ORDER);
            }

            // Get the lastest order
            Order lastestOrder = orderDAO.getLastestOrderByUser(loggedUser);

            // List of item, prepare to save into database
            List<OrderItem> orderItems = new LinkedList<>();
            for (Cart cart : cartItems) {
                orderItems.add(new OrderItem(lastestOrder, cart.getProduct(), cart.getQuantity()));
            }

            // Save order items into database
            numberOfNewRows = 0;
            numberOfNewRows = orderItemDAO.createOrderItems(orderItems);
            if (numberOfNewRows != cartItems.size()) {
                // Delete data of error order 
                orderItemDAO.deleteOrderItemsByOrderId(lastestOrder.getId());
                orderDAO.deleteOrderById(lastestOrder.getId());
                throw new Exception(MessageConstants.ERROR_ADD_ITEM_INTO_ORDER);
            }

            // Delete items in cart
            for (Cart cart : cartItems) {
                cartDAO.delete(cart.getCartItemID());
            }
            
            // Reduce quantity in stock
            productDAO.reduceQuantity(lastestOrder);

            // Clear list items in session
            session.removeAttribute("wantedCartList");

            // Redirect to list order
            response.sendRedirect(request.getContextPath() + "/order");
            
        } catch (DateTimeParseException dtpe) {
            errorMessage.setMessage(MessageConstants.INVALID_DATE);
        } catch (Exception e) {
            errorMessage.setMessage(e.getMessage());
        } finally {
            request.setAttribute("errorMessage", errorMessage);
            request.getRequestDispatcher("/WEB-INF/order/create.jsp").
                    forward(request, response);
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
