/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import DAO.ProductDAO;
import DAO.ReviewDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import model.ErrorMessage;
import model.User;
import utils.MessageConstants;
import validate.InputValidate;

/**
 *
 * @author Vu Minh Khang - CE191371
 */
@WebServlet(name = "ReviewServlet", urlPatterns = {"/review"})
public class ReviewServlet extends HttpServlet {

    private final ReviewDAO reviewDao = new ReviewDAO();
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
            out.println("<title>Servlet ReviewServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ReviewServlet at " + request.getContextPath() + "</h1>");
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

        String productIDParam = request.getParameter("productID");

        // Nếu id sản phẩm không hợp lệ.
        if (!checkValidProductID(productIDParam)) {
            handleErrorWhenExcute(response);
            return;
        }
        
        redirectToProductPage(request, response);
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

        String view = request.getParameter("view");
        String productIDParam = request.getParameter("productID");

        // Nếu view null hoặc rỗng.
        if (view == null || view.isBlank()) {
            handleErrorWhenExcute(response);

            // Nếu view có gì đó.
        } else {

            // Nếu id sản phẩm không hợp lệ.
            if (!checkValidProductID(productIDParam)) {
                handleErrorWhenExcute(response);
                return;
            }

            // Xử lí yêu cầu của người dùng.
            switch (view) {

                case "comment": // Tạo comment mới.
                    handleCommentInput(request);
                    break;

                case "removeComment": // Xóa comment.
                    handleDeleteComment(request, request.getParameter("reviewID"));
                    break;

                case "editComment": // Chỉnh sửa 1 comment.
                    handleEditComment(request, request.getParameter("reviewID"));
                    break;

                default:
                    break;
            }

            redirectToProductPage(request, response);
        }
    }

    /**
     * Chuyển hướng người dùng đến trang lỗi.
     *
     * @param response servlet response
     *
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private void handleErrorWhenExcute(HttpServletResponse response) throws ServletException, IOException {

        // Chuyển hướng đến trang lỗi.
        response.sendRedirect("index.jsp");
    }

    /**
     * Kiểm tra id của product.
     *
     * @param categoryIDParam là id của product.
     *
     * @return True nếu id hợp lệ. False nếu id không hợp lệ.
     */
    private boolean checkValidProductID(String productID) {

        // Kiểm tra id của category có null hoặc rỗng không.
        if (InputValidate.checkEmptyInput(productID)) {
            return false;

            // Kiểm tra id của category có phải là số không.
        } else if (InputValidate.checkValidIntegerNumber(productID)) {
            return false;

            // Kiểm tra id của category có trong phạm vi hợp lệ không.
        } else if (InputValidate.checkIntegerNumberInRange(Integer.parseInt(productID), InputValidate.ZERO_VALUE, productDao.getMaxID())) {
            return false;

            // Nếu tất cả hợp lệ.
        } else {
            return true;
        }
    }

    /**
     * Chuyển hướng về chỉnh product hiện tại.
     *
     * @param request là yêu cầu của người dùng.
     * @param response là phản hồi của người dùng.
     *
     * @throws IOException
     */
    private void redirectToProductPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect(request.getContextPath() + "/user-product?view=product&id=" + request.getParameter("productID"));
    }

    /**
     * Xử lí tạo comment mới của người dùng.
     *
     * Nếu comment của người dùng lỗi thù sẽ thêm lỗi đó vào session của người
     * dùng.
     *
     * @param request là yêu cầu của người dùng.
     */
    private void handleCommentInput(HttpServletRequest request) {
        String comment = request.getParameter("comment");
        String rating = request.getParameter("rating");

        // Kiểm tra comment rỗng.
        if (InputValidate.checkEmptyInput(comment)) {
            request.getSession().setAttribute("errorComment", new ErrorMessage(MessageConstants.EMPTY_COMMENT_INPUT_MESSAGE));

            // Kiểm tra rating rỗng.
        } else if (InputValidate.checkEmptyInput(rating)) {
            request.getSession().setAttribute("errorComment", new ErrorMessage(MessageConstants.EMPTY_RATING_INPUT_MESSAGE));

            // Kiểm tra rating không phải số hợp lệ.
        } else if (InputValidate.checkValidIntegerNumber(rating)) {
            request.getSession().setAttribute("errorComment", new ErrorMessage(MessageConstants.INVALID_RATING_INPUT_MESSAGE));

            // Kiểm tra rating vượt ngoài phạm vi (1-5).
        } else if (InputValidate.checkIntegerNumberInRange(Integer.parseInt(rating), InputValidate.MIN_RATING_VALUE, InputValidate.MAX_RATING_VALUE)) {
            request.getSession().setAttribute("errorComment", new ErrorMessage(MessageConstants.OUT_OF_RANGE_RATING_INPUT_MESSAGE));

            // Nếu không có lỗi thì tạo comment mới.
        } else {
            reviewDao.add(((User) request.getSession().getAttribute("loggedUser")).getId(), Integer.parseInt(request.getParameter("productID")), Integer.parseInt(rating), comment, LocalDateTime.now());
        }
    }

    /**
     * Xử lí xóa comment của người dùng.
     *
     * Nếu comment của người dùng lỗi sẽ ném ra thông báo lỗi. Nếu không có lỗi
     * sẽ thông báo thành công.
     *
     * @param request là yêu cầu người dùng.
     * @param reviewID là id của review.
     */
    private void handleDeleteComment(HttpServletRequest request, String reviewID) {

        // Kiểm tra nếu id comment rỗng.
        if (InputValidate.checkEmptyInput(reviewID)) {
            request.getSession().setAttribute("errorDeleteComment", new ErrorMessage(MessageConstants.ERROR_DELETE_COMMENT_MESSAGE));

            // Kiểm tra nếu id comment không hợp lệ.
        } else if (InputValidate.checkValidIntegerNumber(reviewID)) {
            request.getSession().setAttribute("errorDeleteComment", new ErrorMessage(MessageConstants.UNKNOWN_COMMENT_MESSAGE));;

            // Kiểm tra nếu xóa comment thành công.
        } else {
            reviewDao.delete(Integer.parseInt(reviewID));
            request.getSession().setAttribute("successDeleteComment", MessageConstants.SUCCESS_DELETE_COMMENT_MESSAGE);
        }
    }

    /**
     * Xử lí chỉnh sửa comment của người dùng.
     *
     * Nếu comment của người dùng lỗi sẽ ném ra thông báo lỗi. Nếu không có lỗi
     * sẽ thông báo thành công.
     *
     * @param request là yêu cầu người dùng.
     * @param là id của review.
     */
    private void handleEditComment(HttpServletRequest request, String reviewID) {

        // Kiểm tra nếu id comment rỗng.
        if (InputValidate.checkEmptyInput((String) (reviewID))) {
            request.getSession().setAttribute("errorEditComment", new ErrorMessage(MessageConstants.ERROR_EDIT_COMMENT_MESSAGE));

            // Kiểm tra nếu id comment không hợp lệ.
        } else if (InputValidate.checkValidIntegerNumber(reviewID)) {
            request.getSession().setAttribute("errorEditComment", new ErrorMessage(MessageConstants.UNKNOWN_COMMENT_MESSAGE));

            //  Kiểm tra nếu comment mới bị null hoặc rỗng.
        } else if (InputValidate.checkEmptyInput(request.getParameter("newComment"))) {
            request.getSession().setAttribute("errorEditComment", new ErrorMessage(MessageConstants.EMPTY_COMMENT_INPUT_MESSAGE));

            // Kiểm tra nếu chỉnh sửa thành công.
        } else {
            reviewDao.edit(Integer.parseInt(reviewID), request.getParameter("newComment"));
            request.getSession().setAttribute("successEditComment", MessageConstants.SUCCESS_EDIT_COMMENT_MESSAGE);
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
