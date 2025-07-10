/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import DAO.ProductDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.PaginationUtil;
import validate.InputValidate;

/**
 *
 * @author Vu Minh Khang - CE191371
 */
@WebServlet(name = "UserSearchServlet", urlPatterns = {"/user-search"})
public class UserSearchServlet extends HttpServlet {

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
            out.println("<title>Servlet UserSearchServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UserSearchServlet at " + request.getContextPath() + "</h1>");
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

        String searchInputParam = request.getParameter("key");

        // Nếu search input của người dùng null hoặc rỗng thì chuyển người dùng đến trang lỗi.
        if (InputValidate.checkEmptyInput(searchInputParam)) {
            handleErrorWhenExcute(request, response);

            // Nếu không lỗi thì hiện kết quả cho người dùng.
        } else {
//            addSearchUserInputToCookie(request, response, searchInputParam);
            handleSearchInputFromUser(request, response, searchInputParam);
        }
    }

    /**
     * Xử lí và lấy kết quả sản phẩm tìm kiếm và hiển thị ra cho người dùng.
     *
     * @param request là yêu cầu người dùng.
     * @param response là phản hồi người dùng.
     * @param searchInput là sản phẩm cần tìm kiếm của người dùng.
     *
     * @throws IOException
     * @throws ServletException
     */
    private void handleSearchInputFromUser(HttpServletRequest request, HttpServletResponse response, String searchInput) throws IOException, ServletException {
        getDataFromUserSearchInput(request, searchInput);
        request.getRequestDispatcher("/WEB-INF/products/search.jsp").forward(request, response);
    }

    /**
     * Lấy tổng số trang sản phẩm tìm được.
     *
     * @param request là yêu cầu của người dùng.
     * @param searchInput là sản phảm cần tìm.
     *
     * @return số lượng trang chứa sản phẩm cần tìm.
     */
    private int getPage(HttpServletRequest request, String searchInput) {

        // Đếm tổng số sản phẩm.
        int countItem = productDao.countSearchItemMatchWithSearchInput(searchInput);

        // Tính tổng số trang.
        int totalPages = PaginationUtil.getTotalPages(countItem);

        // Set tổng số page.
        request.setAttribute("totalPages", totalPages);

        return getValidPage(request.getParameter("page"), totalPages);
    }

    /**
     * Lấy trang hợp lệ (số nguyên, không nhỏ hơn 1 và lớn hơn tổng số trang).
     * 
     * @param pageParam là trang hiện tại.
     * @param totalPages tổng số trang.
     * 
     * @return trang hợp lệ.
     */
    private int getValidPage(String pageParam, int totalPages) {

        // Kiểm tra trang có null hoặc rỗng không.
        if (InputValidate.checkEmptyInput(pageParam)) {
            return PaginationUtil.MIN_NUMBER_PAGE;

            // Kiểm tra trang có phải số nguyên hợp lệ hay không.
        } else if (InputValidate.checkValidIntegerNumber(pageParam)) {
            return PaginationUtil.MIN_NUMBER_PAGE;

            // Kiểm tra trang có nhỏ hơn 1 không.
        } else if (InputValidate.checkIntegerNumberHaveSmallerValueThanOther(Integer.parseInt(pageParam), PaginationUtil.MIN_NUMBER_PAGE)) {
            return PaginationUtil.MIN_NUMBER_PAGE;

            // Kiểm tra trang có lớn hơn tống số trang hiện tại không.
        } else if (InputValidate.checkIntegerNumberHaveSmallerValueThanOther(totalPages, Integer.parseInt(pageParam))) {
            return totalPages;

            // Nếu không có lỗi thì trả về trang.
        } else {
            return Integer.parseInt(pageParam);
        }
    }

    /**
     * Lấy danh sách phẩm khớp với yêu cầu người dùng.
     *
     * @param request là yêu cầu của người dùng.
     * @param searchInput là sản phẩm cần tìm.
     */
    private void getDataFromUserSearchInput(HttpServletRequest request, String searchInput) {
        request.setAttribute("searchValue", searchInput);
        request.setAttribute("productSearchList", productDao.getSearchProductForEachPage(InputValidate.removeSpaceFromString(searchInput.trim().toLowerCase()), getPage(request, searchInput)));
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

        redirectToHome(request, response);
    }

    /**
     * Chuyển hướng người dùng về trang chủ.
     *
     * @param request là yêu cầu người dùng.
     * @param response là phản hồi cùa người dùng.
     *
     * @throws IOException
     */
    private void redirectToHome(HttpServletRequest request, HttpServletResponse response) throws IOException {

        // Chuyển hướng người dùng đến nơi muốn hiện lỗi.
        response.sendRedirect(request.getContextPath() + "/home");
    }

    /**
     * Xử lí người dùng tạo lỗi.
     *
     * @param response là phản hồi của người dùng.
     *
     * @throws IOException
     * @throws ServletException
     */
    private void handleErrorWhenExcute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        // Chuyển hướng người dùng đến nơi muốn hiện lỗi.
        response.sendRedirect(request.getContextPath() + "/error-page");
    }

    /**
     * Thêm tìm kiếm vào cookie của người dùng.
     * 
     * @param request là yêu cầu của người dùng.
     * @param response là phản hồi của người dùng.
     * @param key là tìm kiếm của người dùng.
     */
    private void addSearchUserInputToCookie(HttpServletRequest request, HttpServletResponse response, String key) {

        // Lấy tất cả cookie của 
        Cookie[] allSearchInputUserCookies = request.getCookies();

        // Thêm tìm kiếm mới của người dùng vào cookie.
        if (!checkExistCookieValue(allSearchInputUserCookies, key)) {
            
            // Tạo cookie mới.
            Cookie newSearchInputUserCookie = new Cookie("key" + String.valueOf(allSearchInputUserCookies.length), key);

            // Chỉ cho những cookie này hoạt động trong servlet này.
            newSearchInputUserCookie.setPath("/user-search");

            // Set thời gian tồn tại của cookie là 30 ngày.
            newSearchInputUserCookie.setMaxAge(60 * 60 * 24 * 30);

            // Lưu lại cookie của người dùng.
            response.addCookie(newSearchInputUserCookie);
        }
    }

    /**
     * Kiểm tra xem tìm kiếm này của người dùng tồn tại hay chưa.
     *
     * @param allUserCookies là danh sách các cookie.
     * @param value là tìm kiếm mới của người dùng.
     *
     * @return True nếu tìm kiếm này đã tồn tại rồi. False nếu tìm kiếm này
     * không tồn tại.
     */
    private boolean checkExistCookieValue(Cookie[] allUserCookies, String value) {

        // Nếu không tồn tại cookie nào thì coi như tìm kiếm này là tìm kiếm mới.
        if (allUserCookies.length == 0) {
            return false;
        }

        // Kiểm tra từng giá trị của cookie coi có khớp với tìm kiếm không.
        for (Cookie userCookie : allUserCookies) {
            if (InputValidate.removeSpaceFromString(userCookie.getValue().trim().toLowerCase()).equals(InputValidate.removeSpaceFromString(value.trim().toLowerCase()))) {
                return true;
            }
        }

        // Nếu chưa từng tồn tại tìm kiếm này thì coi như là tìm kiếm mới và lưu vào cookie.
        return false;
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
