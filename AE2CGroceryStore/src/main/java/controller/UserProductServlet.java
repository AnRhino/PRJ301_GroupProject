/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import DAO.CartDAO;
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
import java.time.LocalDateTime;
import model.ErrorMessage;
import model.User;
import utils.MessageConstants;
import validate.InputValidate;

/**
 *
 * @author Vu Minh Khang - CE191371
 */
@WebServlet(name = "UserProductServlet", urlPatterns = {"/user-product"})
public class UserProductServlet extends HttpServlet {

    private final ProductDAO productDao = new ProductDAO();
    private final CategoryDAO categoryDao = new CategoryDAO();
    private final ReviewDAO reviewDao = new ReviewDAO();
    private final CartDAO cartDao = new CartDAO();

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
            getIndexInfo(request);
            request.getRequestDispatcher("index.jsp").forward(request, response);

        } else {

            try {

                String id = request.getParameter("id");
                int productID = Integer.parseInt(id);
                request.getSession().setAttribute("productID", id);

                switch (view) {

                    case "category":
                        getCategoryInfo(request);
                        request.getRequestDispatcher("/WEB-INF/products/category.jsp").forward(request, response);
                        break;

                    case "product":
                        getProductInfo(request, productID);
                        request.getRequestDispatcher("/WEB-INF/products/product.jsp").forward(request, response);
                        break;

                    default:
                        getProductInfo(request, productID);
                        request.getRequestDispatcher("/WEB-INF/products/product.jsp").forward(request, response);
                        break;
                }

            } catch (NumberFormatException e) {
                handleUnavailableProductID(request, response);
            }
        }
    }

    /**
     * Xử lí người dùng chỉnh lại id trong html.
     *
     * Lưu id của sản phẩm vào session nếu người dùng truy cập và xem 1 sản phẩm
     * nào đó. Nếu truy cập id sản phẩm mới thì thay đổi id sản phẩm trong
     * session. Không thì không thay đổi nếu người dùng có chỉnh lại id sản
     * phẩm.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private void handleUnavailableProductID(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession().getAttribute("productID") == null || ((String) request.getSession().getAttribute("productID")).isBlank()) {
            request.getRequestDispatcher("index.jsp").forward(request, response);
        } else {
            getProductInfo(request, Integer.parseInt((String) request.getSession().getAttribute("productID")));
            request.getRequestDispatcher("/WEB-INF/products/product.jsp").forward(request, response);
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
     * Lấy thông tin của sản phẩm và trả về cho người dùng.
     *
     * @param request servlet request
     * @param productID là id của sản phẩm.
     */
    private void getProductInfo(HttpServletRequest request, int productID) {
        request.setAttribute("product", productDao.getById(productID));
        request.setAttribute("productList", productDao.getProductsByCategory(categoryDao.getCategoryByProductID(productID).getCategoryID()));
        request.setAttribute("rateScore", productDao.getRateScore(productID));
        request.setAttribute("reviewList", reviewDao.getByProductID(productID));

        getErrorOrSuccessInAddToCartIfExists(request);
        getErrorOrSuccessInAddCommentIfExists(request);
        getErrorOrSuccessInDeleteCommentIfExists(request);
    }

    /**
     * Lấy thông tin của index và trả về người dùng.
     *
     * @param request servlet request
     */
    private void getIndexInfo(HttpServletRequest request) {
        request.setAttribute("categoryList", categoryDao.getAll());
        request.setAttribute("productList", productDao.getAll());
    }

    /**
     * Kiểm tra thông báo thành công hay thất bại nếu khách hàng thêm mới sản
     * phẩm vào giỏ hàng. Kiểm tra nếu khách hàng tạo ra lỗi thì lấy lỗi từ
     * session và ném về request. Kiểm tra nếu khách hàng thêm thành công thì
     * lấy thông báo thành công từ session và ném về request. Còn không có lỗi
     * hay thông báo thành công thì không làm gì.
     *
     * @param request là yêu cầu thêm vào sản phẩm của hàng hàng.
     */
    private void getErrorOrSuccessInAddToCartIfExists(HttpServletRequest request) {
        if (request.getSession().getAttribute("errorCart") != null) {
            request.setAttribute("errorCartMsg", request.getSession().getAttribute("errorCart"));
            request.getSession().removeAttribute("errorCart");
        } else if (request.getSession().getAttribute("successCart") != null) {
            request.setAttribute("successCartMsg", request.getSession().getAttribute("successCart"));
            request.getSession().removeAttribute("successCart");
        }
    }

    /**
     * Kiểm tra thông báo thành công hay thất bại nếu khách hàng thêm mới sản
     * phẩm vào giỏ hàng. Kiểm tra nếu khách hàng tạo ra lỗi thì lấy lỗi từ
     * session và ném về request. Kiểm tra nếu khách hàng thêm thành công thì
     * lấy thông báo thành công từ session và ném về request. Còn không có lỗi
     * hay thông báo thành công thì không làm gì.
     *
     * @param request là yêu cầu thêm vào sản phẩm của hàng hàng.
     */
    private void getErrorOrSuccessInAddCommentIfExists(HttpServletRequest request) {
        if (request.getSession().getAttribute("errorComment") != null) {
            request.setAttribute("errorComment", request.getSession().getAttribute("errorComment"));
            request.getSession().removeAttribute("errorComment");
        }
    }

    private void getErrorOrSuccessInDeleteCommentIfExists(HttpServletRequest request) {
        if (request.getSession().getAttribute("errorDeleteComment") != null) {
            request.setAttribute("errorDeleteComment", request.getSession().getAttribute("errorDeleteComment"));
            request.getSession().removeAttribute("errorDeleteComment");
        } else if (request.getSession().getAttribute("successDeleteComment") != null) {
            request.setAttribute("successDeleteComment", request.getSession().getAttribute("successDeleteComment"));
            request.getSession().removeAttribute("successDeleteComment");
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

        String view = request.getParameter("view");

        // Xử lí view trống.
        if (view == null || view.isBlank()) {
            response.sendRedirect(request.getContextPath() + "/user-product?view=product&id=" + request.getSession().getAttribute("id"));

            // Xử lí theo view của người dùng.
        } else {

            // Xử lí yêu cầu của người dùng.
            switch (view) {

                case "cart":
                    handleCartInput(request);
                    break;

                case "comment":
                    handleCommentInput(request);
                    break;

                case "removeComment":
                    handleDeleteComment(request);
                    break;

                default:
                    break;
            }

            request.getSession().setAttribute("productID", request.getParameter("id"));
            response.sendRedirect(request.getContextPath() + "/user-product?view=product&id=" + request.getSession().getAttribute("productID"));
        }
    }

    /**
     * Xử lí thêm vào cart của người dùng.
     *
     * Nếu số lượng thêm vào cùa người dùng xảy ra lỗi thì sẽ thêm lỗi đó vào
     * session của người dùng. Nếu thêm số lượng phù hợp mà không xảy ra lỗi thì
     * sẽ thêm thông báo thành công vào session của người dùng.
     *
     * @param request là yêu cầu của người dùng.
     */
    private void handleCartInput(HttpServletRequest request) {
        String quantity = request.getParameter("quantity");

        if (InputValidate.checkEmptyInput(quantity)) {
            request.getSession().setAttribute("errorCart", new ErrorMessage(MessageConstants.EMPTY_INPUT_MESSAGE));

        } else if (InputValidate.checkValidIntegerNumber(quantity)) {
            request.getSession().setAttribute("errorCart", new ErrorMessage(MessageConstants.INVALID_INTEGER_INPUT_MESSAGE));

        } else if (InputValidate.checkIntegerNumberInRange(Integer.parseInt(quantity), InputValidate.ZERO_VALUE, productDao.getMaxQuantity(Integer.parseInt(request.getParameter("id"))))) {
            request.getSession().setAttribute("errorCart", new ErrorMessage(MessageConstants.INVALID_CART_INPUT_MESSAGE));

        } else {
            cartDao.addNewProductToCart(((User) request.getSession().getAttribute("loggedUser")).getId(), Integer.parseInt((String) (request.getSession().getAttribute("productID"))), Integer.parseInt(quantity));
            request.getSession().setAttribute("successCart", MessageConstants.SUCCESS_CART_INPUT_MESSAGE);
        }
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

        if (InputValidate.checkEmptyInput(comment)) {
            request.getSession().setAttribute("errorComment", new ErrorMessage(MessageConstants.EMPTY_COMMENT_INPUT_MESSAGE));

        } else if (InputValidate.checkEmptyInput(rating)) {
            request.getSession().setAttribute("errorComment", new ErrorMessage(MessageConstants.EMPTY_RATING_INPUT_MESSAGE));

        } else if (InputValidate.checkValidIntegerNumber(rating)) {
            request.getSession().setAttribute("errorComment", new ErrorMessage(MessageConstants.INVALID_RATING_INPUT_MESSAGE));

        } else if (InputValidate.checkIntegerNumberInRange(Integer.parseInt(rating), InputValidate.MIN_RATING_VALUE, InputValidate.MAX_RATING_VALUE)) {
            request.getSession().setAttribute("errorComment", new ErrorMessage(MessageConstants.OUT_OF_RANGE_RATING_INPUT_MESSAGE));

        } else {
            reviewDao.add(((User) request.getSession().getAttribute("loggedUser")).getId(), Integer.parseInt((String) (request.getSession().getAttribute("productID"))), Integer.parseInt(rating), comment, LocalDateTime.now());
        }
    }

    /**
     * Xử lí xóa comment của người dùng.
     *
     * Nếu comment của người dùng lỗi sẽ ném ra thông báo lỗi. Nếu không có lỗi
     * sẽ thông báo thành công.
     *
     * @param request là yêu cầu người dùng.
     */
    private void handleDeleteComment(HttpServletRequest request) {

        if (InputValidate.checkEmptyInput((String) (request.getParameter("reviewID")))) {
            request.getSession().setAttribute("errorDeleteComment", new ErrorMessage(MessageConstants.UNKNOWN_ERROR_MESSAGE));

        } else if (InputValidate.checkValidIntegerNumber((String) (request.getParameter("reviewID")))) {
            request.getSession().setAttribute("errorDeleteComment", new ErrorMessage(MessageConstants.UNKNOWN_ERROR_MESSAGE));

        } else {
            reviewDao.delete(Integer.parseInt((String) (request.getParameter("reviewID"))));
            request.getSession().setAttribute("successDeleteComment", MessageConstants.SUCCESS_DELETE_COMMENT_MESSAGE);
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
