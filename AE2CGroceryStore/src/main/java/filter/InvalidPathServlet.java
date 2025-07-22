/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Vu Minh Khang - CE191371
 */
public class InvalidPathServlet implements Filter {

    // Những đường dẫn hợp lệ.
    private static final List<String> VALID_PATHS = Arrays.asList(
            "/", "/home", "/products", "/login", "/logout", "/register", "/admin",
            "/admin/categories", "/admin/categories/create", "/admin/categories/delete", "/admin/categories/edit", "/admin/categories/visibility",
            "/admin/discount-code", "/admin/discount-code/create", "/admin/discount-code/delete", "/admin/discount-code/edit", "/admin/discount-code/visibility",
            "/admin/order", "/admin/order/detail",
            "/admin/product",
            "/admin/statistic",
            "/cart", "/discount-code", "/error-page", "/get-image/", "/home",
            "/image-profile", "/new-order", "/order", "/user-profile", "/re-order",
            "/review", "/discount-code/use", "/user-category", "/user-product", "/user-search"
    );

    @Override
    public void doFilter(ServletRequest sr, ServletResponse sr1, FilterChain fc) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) sr;
        HttpServletResponse res = (HttpServletResponse) sr1;

        String uri = req.getRequestURI();
        String contextPath = req.getContextPath();
        String path = uri.substring(contextPath.length());

        if (path.startsWith("/assets/") || path.endsWith(".css") || path.endsWith(".js") || path.endsWith(".png") || path.endsWith(".jpg")) {
            fc.doFilter(sr, sr1);
            return;
        }

        if (!existPath(path)) {
            res.sendRedirect(contextPath + "/error-page");
        } else {

            fc.doFilter(sr, sr1);
        }
    }

    private boolean existPath(String path) {

        for (String currPath : VALID_PATHS) {
            if (path.equals(currPath)) {
                return true;
            }
        }

        return false;
    }

}
