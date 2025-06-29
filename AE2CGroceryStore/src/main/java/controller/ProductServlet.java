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
import java.util.ArrayList;
import java.util.List;
import model.Category;
import model.ErrorMessage;
import model.Product;
import validate.ProductValidation;

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

    // <editor-fP defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
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
        ProductValidation pr = new ProductValidation();
         List<Product> list = productDAO.getAll();
        if (action.equals("create")) {
            List<String> errorMessage = new ArrayList<>();

            String Code = request.getParameter("productCore");
            String Name = request.getParameter("productName");
            String Quantity = request.getParameter("quantity");
            String Price = request.getParameter("price");
            String Cate = request.getParameter("categogy");

            List<Product> allProducts = productDAO.getAll();
            errorMessage.addAll(pr.checkProductCode(Code, allProducts));

            errorMessage.addAll(pr.checkProductName(Name));

            errorMessage.addAll(pr.checkQuantity(Quantity));

            errorMessage.addAll(pr.checkPrice(Price));

            errorMessage.addAll(pr.checkCategoryID(Cate));

            if (!errorMessage.isEmpty()) {
                CategoryDAO cateDAO = new CategoryDAO();
                request.setAttribute("cate", cateDAO.getAll());
                request.setAttribute("errorMessage", errorMessage);

                request.setAttribute("PCode", Code);
                request.setAttribute("PName", Name);
                request.setAttribute("PQuantity", Quantity);
                request.setAttribute("PPrice", Price);
                request.setAttribute("PCate", Cate);

                request.getRequestDispatcher("/WEB-INF/product/create.jsp").forward(request, response);
                return;
            }

            productDAO.create(Code, Name, Integer.parseInt(Quantity), Double.parseDouble(Price), Integer.parseInt(Cate));
            response.sendRedirect(request.getContextPath() + "/product?view=list");
        } else if (action.equals(
                "delete")) {
            int id = Integer.parseInt(request.getParameter("id"));
            productDAO.delete(id);
            response.sendRedirect(request.getContextPath() + "/product?view=list");
        } else if (action.equals("edit")) {
            int id = Integer.parseInt(request.getParameter("id"));

            String Code = request.getParameter("productCore");
            String Name = request.getParameter("productName");
            String Quantity = request.getParameter("quantity");
            String Price = request.getParameter("price");
            String Cate = request.getParameter("categogy");

            List<String> errorMessage = new ArrayList<>();
            errorMessage.addAll(pr.checkProductCodeEdit(Code, id, list));

            errorMessage.addAll(pr.checkProductName(Name));
            errorMessage.addAll(pr.checkQuantity(Quantity));

            errorMessage.addAll(pr.checkPrice(Price));

            errorMessage.addAll(pr.checkCategoryID(Cate));

            if (!errorMessage.isEmpty()) {

                CategoryDAO cateDAO = new CategoryDAO();
                request.setAttribute("cate", cateDAO.getAll());

                request.setAttribute("errorMessage", errorMessage);

                request.setAttribute("oldCode", Code);
                request.setAttribute("oldName", Name);
                request.setAttribute("oldQuantity", Quantity);
                request.setAttribute("oldPrice", Price);
                request.setAttribute("oldCate", Cate);
                request.setAttribute("pro", productDAO.getById(id));

                request.getRequestDispatcher("/WEB-INF/product/edit.jsp").forward(request, response);
                return;
            }

            productDAO.edit(id, Code, Name, Integer.parseInt(Quantity), Double.parseDouble(Price), Integer.parseInt(Cate));
            response.sendRedirect(request.getContextPath() + "/product?view=list");
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
    }// </editor-fP>

}
