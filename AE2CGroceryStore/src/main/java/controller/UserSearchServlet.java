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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;
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

        String searchInputParam = request.getParameter("key").trim();

        // Nếu search input của người dùng null hoặc rỗng thì chuyển người dùng đến trang lỗi.
        if (InputValidate.checkEmptyInput(searchInputParam)) {
            handleErrorWhenExcute(request, response);

            // Nếu không lỗi thì hiện kết quả cho người dùng.
        } else {
            addSearchUserInputToCookie(request, response, searchInputParam);
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
        
        key = key.trim();
        
        if (key.length() == 0) {
            return;
        }

        // Lấy tất cả cookie của 
        Cookie[] allCookies = request.getCookies();
        Cookie keySearchCookie = getKeySearchCookie(allCookies);
        
        if (keySearchCookie == null) {

            // Nếu chưa có cookie thì tạo mới
            keySearchCookie = new Cookie("key", key);
            
        } else {

            // Lấy giá trị cũ và thêm từ khóa mới vào cuối
            String[] keySearch = keySearchCookie.getValue().split("\\|");
            
            if (checkExistKeySearch(keySearch, key)) {
                keySearchCookie.setValue(sortKeySearch(keySearch, key));
                
            } else {
                
                StringBuilder newValue = new StringBuilder();
                Queue q = new LinkedList();
                
                for (String s : keySearch) {
                    q.add(s);
                }
                
                if (q.size() >= 5) {
                    newValue.append(key).append("|");

                    // Bỏ phần tử đầu tiên và thêm phần tử mới cuối
                    for (int i = q.size() ; i > 1 ; i--) {
                        newValue.append(q.poll()).append("|");
                    }
                    
                    newValue.deleteCharAt(newValue.length()-1);
                    
                } else {
                    newValue.append(key).append("|");
                    
                    // Thêm tất cả phần tử cũ + phần tử mới
                    while (!q.isEmpty()) {
                        newValue.append(q.poll()).append("|");
                    }
                    newValue.deleteCharAt(newValue.length()-1);
                }
                
                keySearchCookie.setValue(newValue.toString());
                
            }
        }

        // Set thời gian tồn tại cookie (30 ngày)
        keySearchCookie.setMaxAge(60 * 60 * 24 * 30);
        response.addCookie(keySearchCookie);

        // Bỏ vào request/session để sử dụng trong dropdown.
        addSearchList(request, keySearchCookie);
    }
    
    private String sortKeySearch(String[] keySearch, String key) {
        
        Queue q = new LinkedList();
        StringBuilder newValue = new StringBuilder();
        
        for (int i = 0; i < keySearch.length; i++) {       
            if (!keySearch[i].equals(key)) {
                q.add(keySearch[i]);
            } 
        }
        
        newValue.append(key).append("|");
        
        while (!q.isEmpty()) {
            newValue.append(q.poll()).append("|");
        }
        newValue.deleteCharAt(newValue.length()-1);
        
        return newValue.toString();
    }
    
    private boolean checkExistKeySearch(String[] keySearch, String key) {
        
        for (String string : keySearch) {
            if (string.equals(key)) {
                return true;
            }
        }
        
        return false;
    }
    
    private void addSearchList(HttpServletRequest request, Cookie searchInputCookie) {

        // Lấy tất cả tìm kiếm của người dùng và bỏ vào array.
        String[] keySearch = searchInputCookie.getValue().split("\\|");
        List<String> keySearchList = new ArrayList<>();

        // Thêm dữ liệu vào danh sách.
        for (String key : keySearch) {

            // Kiểm tra giá trị rỗng.
            if (!key.trim().isEmpty()) {
                keySearchList.add(key.trim());
            }
        }
        
        request.getSession().setAttribute("keySearchList", keySearchList);
    }

    /**
     * Lấy lịch sử tìm kiếm của người dùng.
     *
     * @param allCookies là tất cả cookie hiện có.
     *
     * @return Cookie chứa lịch sử tìm kiếm của người dùng. Null nếu lịch sử tìm
     * kiếm trống.
     */
    private Cookie getKeySearchCookie(Cookie[] allCookies) {

        // Kiểm tra nếu không tồn tại cookie nào.
        if (allCookies == null || allCookies.length == 0) {
            return null;
        }

        // Duyệt qua hết tất cả cookie để tìm cookie chứ lịch sử tìm kiếm của người dùng.
        for (Cookie cookie : allCookies) {
            if (cookie.getName().equals("key")) {

                // Nếu có tồn tại thì trả về cookie đó.
                return cookie;
            }
        }

        // Không có thì trả về null.
        return null;
    }

    /**
     * Lấy lịch sử tìm kiếm mới của người dùng.
     *
     * @param userCooky là cookie chứ lịch sử tìm kiếm của người dùng.
     * @param key là tìm kiếm mới của người dùng.
     *
     * @return Chuỗi chứa tất tìm kiếm của người dùng.
     */
    private String getNewKeySearchCookie(Cookie userCooky, String key) {

        // Lấy tất cả tìm kiếm của người dùng và bỏ vào array.
        String[] keySearch = userCooky.getValue().split("\\|");
        
        String newKeySearch = null;

        // Nếu số lượng lịch sử tìm kiếm của người dùng quá 5 thì xóa đi cái cũ nhất và thêm cái mới nhất vào cuối cùng.
        // Cách nhau bởi dấu phẩy.
        if (keySearch.length == 5) {
            newKeySearch = keySearch[1] + "|"
                    + keySearch[2] + "|"
                    + keySearch[3] + "|"
                    + keySearch[4] + "|"
                    + key;
            // Thêm lịch sử tìm kiếm mới vào cuối.
        } else {
            
            newKeySearch = "";
            
            for (String keySearch1 : keySearch) {
                newKeySearch += keySearch1 + "|";
            }
            
            newKeySearch += key.trim();
        }

        // Trả về lịch sử tìm kiếm mới của người dùng.
        return newKeySearch;
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
