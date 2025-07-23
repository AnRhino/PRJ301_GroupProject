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
import model.User;
import utils.PaginationUtil;
import validate.InputValidate;

/**
 *
 * @author Vu Minh Khang - CE191371
 */
@WebServlet(name = "UserProductServlet", urlPatterns = {"/user-product"})
public class UserProductServlet extends HttpServlet {

    private ProductDAO productDao;
    private CategoryDAO categoryDao;
    private ReviewDAO reviewDao;
    private User loggedUser;

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
        String productIDParam = request.getParameter("productID");
        String categoryIDParam = request.getParameter("categoryID");

        // Nếu view null hoặc rỗng.
        if (view == null || view.isBlank()) {
            handleErrorWhenExcute(request, response);

            // Nếu người dùng có yêu cầu từ view.
        } else {

            // Nếu id của sản phẩm và id của danh mục đều không hợp lệ.
            if (checkEmptyID(productIDParam, categoryIDParam)) {
                handleErrorWhenExcute(request, response);
                return;
            }

            // Tạo DAO.
            productDao = new ProductDAO();
            categoryDao = new CategoryDAO();
            reviewDao = new ReviewDAO();

            // Xử lí yêu càu của người dùng.
            switch (view) {

                case "category": // Hiện ra 1 danh mục.
                    // Chuyển hướng đến CategoryServlet.
                    response.sendRedirect(request.getContextPath() + "/user-category?id=" + categoryIDParam);
                    break;

                case "product": // Hiện ra 1 sản phẩm.
                    // Check id của sản phẩm có hợp lệ không.
                    if (!checkValidProductID(productIDParam)) {
                        handleErrorWhenExcute(request, response);
                        return;
                    }
                    // Hiện ra sản phẩm nếu danh mục hợp lệ.
                    showProduct(request, response, Integer.parseInt(productIDParam));
                    break;

                default: // Không có yêu cầu thì dẫn người dùng về product.jsp.
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
     * Kiểm tra id của product.
     *
     * @param categoryIDParam là id của product.
     *
     * @return True nếu id hợp lệ. False nếu id không hợp lệ.
     */
    private boolean checkValidProductID(String productIDParam) {

        // Kiểm tra id của category có null hoặc rỗng không.
        if (InputValidate.checkEmptyInput(productIDParam)) {
            return false;

            // Kiểm tra id của category có phải là số không.
        } else if (InputValidate.checkValidIntegerNumber(productIDParam)) {
            return false;

            // Kiểm tra id của category có trong phạm vi hợp lệ không.
        } else if (InputValidate.checkIntegerNumberInRange(Integer.parseInt(productIDParam), InputValidate.ZERO_VALUE, productDao.getMaxID())) {
            return false;

            // Nếu tất cả hợp lệ.
        } else {
            return true;
        }
    }

    /**
     * Hiện ra sản phẩm cho người dùng.
     *
     * @param request là yêu cầu của người dùng.
     * @param response là phản hồi của người dùng.
     * @param productID là id của sản phẩm.
     *
     * @throws ServletException
     * @throws IOException
     */
    private void showProduct(HttpServletRequest request, HttpServletResponse response, int productID) throws ServletException, IOException {
        getProductInfo(request, productID);
        request.getRequestDispatcher("/WEB-INF/products/product.jsp").forward(request, response);
    }

    /**
     * Chuyển hướng người dùng đến trang lỗi.
     *
     * @param response servlet response
     *
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private void handleErrorWhenExcute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Chuyển hướng người dùng đến nơi muốn hiện lỗi.
        response.sendRedirect(request.getContextPath() + "/error-page");
    }

    /**
     * Lấy thông tin của sản phẩm và trả về cho người dùng.
     *
     * @param request servlet request
     * @param productID là id của sản phẩm.
     */
    private void getProductInfo(HttpServletRequest request, int productID) {
        loggedUser = (User) request.getSession().getAttribute("loggedUser");
        int countItem = reviewDao.countReviewByProductId(productID);
        int totalPages = PaginationUtil.getTotalPagesForReviews(countItem);
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
        
        // Check người dùng có thể review hay không
        boolean canReview = false;
        if(loggedUser != null && reviewDao.canReview(loggedUser.getId(), productID)) {
            canReview = true;
        }

        request.setAttribute("product", productDao.getAvailableProductById(productID));
        request.setAttribute("productList", productDao.getProductsByCategory(categoryDao.getCategoryByProductID(productID).getCategoryID()));
        request.setAttribute("rateScore", productDao.getRateScore(productID));
        request.setAttribute("reviewList", reviewDao.getByProductIDForPagination(productID, page));
        request.setAttribute("canReview", canReview);

        // Kiểm tra xem có thông báo lỗi hay thành công nào không.
        getErrorOrSuccessInAddToCartIfExists(request);
        getErrorOrSuccessInAddCommentIfExists(request);
        getErrorOrSuccessInDeleteCommentIfExists(request);
        getErrorOrSuccessInEditCommentIfExists(request);
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

        // Nếu có lỗi.
        if (request.getSession().getAttribute("errorCart") != null) {
            request.setAttribute("errorCartMsg", request.getSession().getAttribute("errorCart"));
            request.getSession().removeAttribute("errorCart");

            // Nếu thành công.
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

        // Nếu có lỗi.
        if (request.getSession().getAttribute("errorComment") != null) {
            request.setAttribute("errorComment", request.getSession().getAttribute("errorComment"));
            request.getSession().removeAttribute("errorComment");
        }
    }

    /**
     * Kiểm tra thông báo thành công hay thất bại nếu người dùng xóa 1 comment.
     * Kiểm tra nếu người dùng tạo ra lỗi thì lấy lỗi từ session và ném về
     * request. Kiểm tra nếu người dùng xóa comment thành công thì lấy thông báo
     * thành công từ session và ném về request. Còn không có lỗi hay thông báo
     * thành công thì không làm gì.
     *
     * @param request là yêu cầu xóa 1 comment của người dùng.
     */
    private void getErrorOrSuccessInDeleteCommentIfExists(HttpServletRequest request) {

        // Nếu có lỗi.
        if (request.getSession().getAttribute("errorDeleteComment") != null) {
            request.setAttribute("errorDeleteComment", request.getSession().getAttribute("errorDeleteComment"));
            request.getSession().removeAttribute("errorDeleteComment");

            // Nếu thành công.
        } else if (request.getSession().getAttribute("successDeleteComment") != null) {
            request.setAttribute("successDeleteComment", request.getSession().getAttribute("successDeleteComment"));
            request.getSession().removeAttribute("successDeleteComment");
        }
    }

    /**
     * Kiểm tra thông báo thành công hay thất bại nếu người dùng chỉnh sửa 1
     * comment. Kiểm tra nếu người dùng tạo ra lỗi thì lấy lỗi từ session và ném
     * về request. Kiểm tra nếu người dùng chỉnh sửa comment thành công thì lấy
     * thông báo thành công từ session và ném về request. Còn không có lỗi hay
     * thông báo thành công thì không làm gì.
     *
     * @param request là yêu cầu xóa 1 comment của người dùng.
     */
    private void getErrorOrSuccessInEditCommentIfExists(HttpServletRequest request) {

        // Nếu có lỗi.
        if (request.getSession().getAttribute("errorEditComment") != null) {
            request.setAttribute("errorEditComment", request.getSession().getAttribute("errorEditComment"));
            request.getSession().removeAttribute("errorEditComment");

            // Nếu thành công.
        } else if (request.getSession().getAttribute("successEditComment") != null) {
            request.setAttribute("successEditComment", request.getSession().getAttribute("successEditComment"));
            request.getSession().removeAttribute("successEditComment");
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
        String productIDParam = request.getParameter("productID");

        // Xử lí view trống.
        if (view == null || view.isBlank()) {
            handleErrorWhenExcute(request, response);

            // Xử lí theo view của người dùng.
        } else {

            // Nếu id sản phẩm không hợp lệ.
            if (!checkValidProductID(productIDParam)) {
                handleErrorWhenExcute(request, response);
                return;
            }

            // Chuyển hướng về chính jsp hiện tại.
            response.sendRedirect(request.getContextPath() + "/user-product?view=product&productID=" + productIDParam);
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
