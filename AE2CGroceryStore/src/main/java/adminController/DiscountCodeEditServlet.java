/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package adminController;

import DAO.DiscountCodeDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.DiscountCode;
import model.DiscountCodeType;

/**
 *
 * @author Dinh Cong Phuc - CE190770
 */
@WebServlet(name = "DiscountCodeEditServlet", urlPatterns = {"/admin/discount-code/edit"})
public class DiscountCodeEditServlet extends HttpServlet {

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
            out.println("<title>Servlet DiscountCodeEditServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet DiscountCodeEditServlet at " + request.getContextPath() + "</h1>");
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
    int discountCodeId = -1;
    DiscountCode discountCode = null;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//        processRequest(request, response);
        DiscountCodeDAO dao = new DiscountCodeDAO();
        List<DiscountCodeType> discountType = dao.getAllDiscountType();
        discountCodeId = Integer.parseInt(request.getParameter("id"));
        discountCode = dao.getDiscountCodeById(discountCodeId);
        request.setAttribute("discountCode", discountCode);
        request.setAttribute("discountType", discountType);
        request.getRequestDispatcher("/WEB-INF/adminFeatures/discountCode/discountCodeEdit.jsp").forward(request, response);
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
// id, String code, int value, int type, int quantity, LocalDate expiryDate, int minOrderValue, int isHidden
        //1. check null input
        //2. check valid input

        // Get parameters
        DiscountCodeDAO dao = new DiscountCodeDAO();
        String newCode = request.getParameter("code");
        String newValueStr = request.getParameter("value");
        String newTypeStr = request.getParameter("type");
        String newQuantityStr = request.getParameter("quantity");
        String newExpiryDateStr = request.getParameter("expiryDate");
        String newMinOrderValueStr = request.getParameter("minOrderValue");
        boolean newIsHiddenBool = Boolean.parseBoolean(request.getParameter("isHidden"));

        // Initialize default values
        int type = -1;
        String code = "";
        int value = 0;
        int quantity = 0;
        LocalDate expiryDate = null;
        int minOrderValue = 0;
        int isHidden = 0;

        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate currentDate = LocalDate.now();

        boolean hasError = false;
        String errorMsg = null;

        // Check for empty fields
        if (newTypeStr == null || newTypeStr.trim().isBlank()
                || newValueStr == null || newValueStr.trim().isBlank()
                || newCode == null || newCode.trim().isBlank()
                || newQuantityStr == null || newQuantityStr.trim().isBlank()
                || newExpiryDateStr == null || newExpiryDateStr.trim().isBlank()
                || newMinOrderValueStr == null || newMinOrderValueStr.trim().isBlank()
                || newIsHiddenBool != false && newIsHiddenBool != true) {

            request.setAttribute("errorMsg", "All fields must be filled");
            request.getRequestDispatcher("/WEB-INF/adminFeatures/discountCode/discountCodeEdit.jsp").forward(request, response);
            return;
        }

        // Start validation
        try {
            // Validate type
            type = Integer.parseInt(newTypeStr);
            if (type < 0 || type > 2) {
                hasError = true;
                request.setAttribute("typeError", "Invalid Discount Type");
            }

            // Validate code
            code = newCode.trim();
            if (!code.equals(discountCode.getCode()) && !code.matches("^[A-Z0-9]{5,15}$")) {
                hasError = true;
                request.setAttribute("codeError", "Discount Code must contain only Latin characters and numbers, and have length between 5 and 15");
            } else if (!code.equals(discountCode.getCode()) && dao.exists(code)) {
                hasError = true;
                request.setAttribute("codeError", "Inputted Code already exists");
            }

            // Validate value based on type
            value = Integer.parseInt(newValueStr);
            switch (type) {
                case 0: // Percentage
                    if (value < 0 || value > 100) {
                        hasError = true;
                        request.setAttribute("valueError", "Discount Value for Percentage Discount must be between 0-100");
                    }
                    break;
                case 1: // Fixed amount
                    if (value <= 0) {
                        hasError = true;
                        request.setAttribute("valueError", "Discount Value for Fixed Amount Discount must be greater than 0 (VND)");
                    }
                    break;
                case 2: // Free shipping
                    value = 0;
                    break;
                default:
                    hasError = true;
                    request.setAttribute("valueError", "Value invalidated because of Discount Type error");
            }

            // Validate quantity
            quantity = Integer.parseInt(newQuantityStr);
            if (quantity < 0) {
                hasError = true;
                request.setAttribute("quantityError", "Quantity must be at least 0");
            }

            // Validate expiry date
            try {
                expiryDate = LocalDate.parse(newExpiryDateStr, dateFormat);
                if (expiryDate.isBefore(currentDate)) {
                    hasError = true;
                    request.setAttribute("expiryDateError", "The expiry date must not be before today");
                }
            } catch (Exception e) {
                hasError = true;
                request.setAttribute("expiryDateError", "Invalid date format. Please use yyyy/MM/dd");
            }

            // Validate minimum order value
            minOrderValue = Integer.parseInt(newMinOrderValueStr);
            if (minOrderValue < 0 || minOrderValue > 50000000) {
                hasError = true;
                request.setAttribute("minOrderValueError", "Minimum Order Value must be between 0 and 50,000,000 VND");
            }

            // Validate isHidden
            isHidden = newIsHiddenBool ? 1 : 0;
            if (isHidden < 0 || isHidden > 1) {
                hasError = true;
                request.setAttribute("isHiddenError", "Invalid visibility setting");
            }

        } catch (NumberFormatException e) {
            hasError = true;
            errorMsg = "Invalid number format in one of the fields";
            System.out.println("NumberFormatException: " + e.getMessage());
        } catch (Exception e) {
            hasError = true;
            errorMsg = "An error occurred while processing your request";
            System.out.println("Exception: " + e.getMessage());
        }

        // If there are validation errors, return to the form
        if (hasError) {
            // Keep the form data for better UX
            request.setAttribute("code", newCode);
            request.setAttribute("value", newValueStr);
            request.setAttribute("type", newTypeStr);
            request.setAttribute("quantity", newQuantityStr);
            request.setAttribute("expiryDate", newExpiryDateStr);
            request.setAttribute("minOrderValue", newMinOrderValueStr);
            request.setAttribute("isHidden", newIsHiddenBool);

            request.setAttribute("errorMsg", errorMsg);

            // Get discount types for the dropdown
            List<DiscountCodeType> discountType = dao.getAllDiscountType();
            request.setAttribute("discountType", discountType);

            request.getRequestDispatcher("/WEB-INF/adminFeatures/discountCode/discountCodeEdit.jsp").forward(request, response);
            return;
        }

        // If all validations pass, proceed with creation
        try {
            dao.discountCodeEdit(discountCodeId, code, value, type, quantity, expiryDate, minOrderValue, isHidden);
            response.sendRedirect(request.getContextPath() + "/admin/discount-code");
        } catch (Exception e) {
            // Log the error and show user-friendly message
            request.setAttribute("errorMsg", "Failed to update discount code. Please try again.");

            // Get discount types for the dropdown
            List<DiscountCodeType> discountType = dao.getAllDiscountType();
            request.setAttribute("discountType", discountType);

            // request.getRequestDispatcher("/WEB-INF/adminFeatures/discountCode/discountCodeEdit.jsp").forward(request, response);
            this.doGet(request, response);
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
