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
import model.ErrorMessage;
import model.Product;
import model.User;
import utils.MessageConstants;
import validate.InputValidate;
import validate.ProductValidation;

/**
 * Servlet xử lý các thao tác liên quan đến giỏ hàng của người dùng.
 *
 * @author Vu Minh Khang - CE191371
 */
@WebServlet(name = "CartServlet", urlPatterns = {"/cart"})
public class CartServlet extends HttpServlet {

    private final CartDAO cartDao = new CartDAO();
    private final CategoryDAO categoryDao = new CategoryDAO();
    private final ProductDAO productDao = new ProductDAO();
    private String view;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("loggedUser");

        // Nếu người dùng chưa đăng nhập, chuyển hướng đến trang login
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        view = request.getParameter("view");

        // Hiển thị danh sách sản phẩm trong giỏ hàng
        if (view == null || view.isBlank()) {
            request.setAttribute("listCanBuy", cartDao.getCanBuy(user.getId()));
            request.setAttribute("listOutOfStock", cartDao.getOutOfStock(user.getId()));
            request.setAttribute("ListProductIsHidden", cartDao.getProductIsHidden(user.getId()));
            request.getRequestDispatcher("/WEB-INF/users/cart.jsp").forward(request, response);
            return;
            // Hiển thị trang edit số lượng sản phẩm đã thêm vào giỏ hàng
        } else if ("edit".equals(view)) {
            int cartId = Integer.parseInt(request.getParameter("id"));
            request.setAttribute("cart", cartDao.getCartByID(cartId));
            request.getRequestDispatcher("/WEB-INF/users/edit.jsp").forward(request, response);
            return;
            // Hiển thị trang delete sản phẩm đã thêm vào giỏ hàng
        } else if ("delete".equals(view)) {
            int cartId = Integer.parseInt(request.getParameter("id"));
            request.setAttribute("cart", cartDao.getCartByID(cartId));
            request.getRequestDispatcher("/WEB-INF/users/delete.jsp").forward(request, response);
            return;
            // Hiển thị trang show các sản phẩm chọn để mua và tạo 1 cái order
        } else if ("order".equals(view)) {
            if (session.getAttribute("wantedCartList") == null) {
                session.setAttribute("wantedCartList", new ArrayList<Cart>());
            }
            request.getRequestDispatcher("/WEB-INF/users/order.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("edit".equals(action)) {
            // Xử lý chỉnh sửa số lượng sản phẩm trong giỏ hàng
            String cartid = request.getParameter("cartId");
            String quantity = request.getParameter("quantity");
            ProductValidation pr = new ProductValidation();
            List<String> checkQuantity = pr.checkQuantity(quantity);

            // kiểm tra xem người dùng nhập số lượng có hợp lệ không
            if (!checkQuantity.isEmpty()) {
                request.setAttribute("cart", cartDao.getCartByID(Integer.parseInt(cartid)));
                request.setAttribute("checkQuantity", checkQuantity);
                request.getRequestDispatcher("/WEB-INF/users/edit.jsp").forward(request, response);
                return;
            }

            if (Integer.parseInt(quantity) == 0) {
                cartDao.delete(Integer.parseInt(cartid));
            } else {
                cartDao.edit(Integer.parseInt(cartid), Integer.parseInt(quantity));
            }
            response.sendRedirect(request.getContextPath() + "/cart");
            return;

        } else if ("delete".equals(action)) {
            // Xử lý xóa sản phẩm khỏi giỏ hàng
            int cartId = Integer.parseInt(request.getParameter("cartId"));
            cartDao.delete(cartId);
            response.sendRedirect(request.getContextPath() + "/cart");
            return;

        } else if ("order".equals(action)) {
            // Xử lý chọn sản phẩm để đặt hàng
            User user = (User) request.getSession().getAttribute("loggedUser");
            List<Cart> listCanBuy = cartDao.getCanBuy(user.getId());
            List<Cart> wantedCartList = new ArrayList<>();
            //Vòng lặp để kiểm tra các sản phẩm người dùng chọn đề mua và lấy dữ liệu về
            for (Cart cart : listCanBuy) {
                String CheckedBox = "isBuy" + cart.getCartItemID();
                if (request.getParameter(CheckedBox) != null) {
                    wantedCartList.add(cart);
                }
            }
            request.getSession().setAttribute("wantedCartList", wantedCartList);
            response.sendRedirect(request.getContextPath() + "/cart?view=order");
            return;

        } else if ("cart".equals(action)) {

            String productIDParam = request.getParameter("productID");

            System.out.println(productIDParam);

            // Nếu id sản phẩm không hợp lệ.
            if (!checkValidProductID(productIDParam)) {
                handleUnavailableProductID(response);
                return;
            }

            handleCartInput(request);

            response.sendRedirect(request.getContextPath() + "/user-product?view=product&productID=" + productIDParam);
            return;
        }

        response.sendRedirect(request.getContextPath() + "/cart");
    }

    /**
     * Chuyển hướng người dùng đến trang lỗi.
     *
     * @param response servlet response
     *
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private void handleUnavailableProductID(HttpServletResponse response) throws ServletException, IOException {

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

        //  Kiểm tra nếu số lượng sản phẩm thêm vào giỏ hàng rỗng.
        if (InputValidate.checkEmptyInput(quantity)) {
            request.getSession().setAttribute("errorCart", new ErrorMessage(MessageConstants.EMPTY_INPUT_MESSAGE));

            // Kiểm tra nếu số lượng sản phẩm thêm vào giỏ hàng không phải là số nguyên.
        } else if (InputValidate.checkValidIntegerNumber(quantity)) {
            request.getSession().setAttribute("errorCart", new ErrorMessage(MessageConstants.INVALID_INTEGER_INPUT_MESSAGE));

            // Kiểm tra nếu số lượng sản phẩm thêm vào giỏ hàng nhỏ hơn 1 hoặc lớn hơn số lượng tối đa của sản phẩm đó.
        } else if (InputValidate.checkIntegerNumberInRange(Integer.parseInt(quantity), InputValidate.ZERO_VALUE, productDao.getMaxQuantity(Integer.parseInt(request.getParameter("productID"))))) {
            request.getSession().setAttribute("errorCart", new ErrorMessage(MessageConstants.INVALID_CART_INPUT_MESSAGE));

            // Nếu không có lỗi thì thêm vào giỏ hàng cho khách hàng.
        } else {
            cartDao.addNewProductToCart(((User) request.getSession().getAttribute("loggedUser")).getId(), Integer.parseInt(request.getParameter("productID")), Integer.parseInt(quantity));
            request.getSession().setAttribute("successCart", MessageConstants.SUCCESS_CART_INPUT_MESSAGE);
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
