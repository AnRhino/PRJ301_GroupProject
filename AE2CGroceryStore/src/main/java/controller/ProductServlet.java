/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import DAO.CategoryDAO;
import DAO.ProductDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.Category;
import model.Product;

/**
 *
 * @author Phan Duc Tho - CE191246
 */
@WebServlet(name = "ProductServlet", urlPatterns = {"/product"})
public class ProductServlet extends HttpServlet {

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
            out.println("<title>Servlet ProductServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ProductServlet at " + request.getContextPath() + "</h1>");
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
        ProductDAO dao = new ProductDAO();
        String view = request.getParameter("view");
        if (view == null || view.isBlank() || view.equals("list")) {
            List<Product> list = dao.getAll();
            request.setAttribute("list", list);

            request.getRequestDispatcher("/WEB-INF/product/list.jsp").forward(request, response);
        } else if (view.equals("create")) {
            CategoryDAO cateDAO = new CategoryDAO();
            List<Category> cate = cateDAO.getAll();
            request.setAttribute("cate", cate);
            request.getRequestDispatcher("/WEB-INF/product/create.jsp").forward(request, response);

        } else if (view.equals("delete")) {
            int id = Integer.parseInt(request.getParameter("id"));
            Product product = dao.getById(id);
            request.setAttribute("pro", product);
            request.getRequestDispatcher("/WEB-INF/product/delete.jsp").forward(request, response);

        } else if (view.equals("edit")) {
            int id = Integer.parseInt(request.getParameter("id"));
            Product pr = dao.getById(id);
            System.out.println(pr);
            request.setAttribute("pro", pr);
            CategoryDAO cateDAO = new CategoryDAO();
            List<Category> cate = cateDAO.getAll();
            request.setAttribute("cate", cate);
            request.getRequestDispatcher("/WEB-INF/product/edit.jsp").forward(request, response);

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
        String action = request.getParameter("action");
        ProductDAO productDAO = new ProductDAO();
        if (action.equals("create")) {

            String proCore = request.getParameter("productCore");
            String proName = request.getParameter("productName");
            int quan = Integer.parseInt(request.getParameter("quantity"));
            double price = Double.parseDouble(request.getParameter("price"));
            int cateid = Integer.parseInt(request.getParameter("categogy").trim());
            System.out.println("|" + cateid + "|");
            productDAO.create(proCore, proName, quan, price, cateid);
        } else if (action.equals("delete")) {
            int id = Integer.parseInt(request.getParameter("id"));
            productDAO.delete(id);

        } else if (action.equals("edit")) {
           int id = Integer.parseInt(request.getParameter("id"));
            String proCore = request.getParameter("productCore");
            String proName = request.getParameter("productName");
            int quan = Integer.parseInt(request.getParameter("quantity"));
            double price = Double.parseDouble(request.getParameter("price"));
            int cateid = Integer.parseInt(request.getParameter("categogy").trim());

            productDAO.edit(id, proCore, proName, quan, price, cateid);

        }
        doGet(request, response);
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
