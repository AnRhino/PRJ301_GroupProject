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
import model.ErrorMessage;
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
            ErrorMessage message = new ErrorMessage();
            String check = "";
            check = request.getParameter("id");
            if (check != null && check != "") {
                if (check.equalsIgnoreCase("1") || check.equalsIgnoreCase("2")) {

                    message.setMessage("Must enter 1 character");
                    request.setAttribute("message", message.getMessage());
                } else if (check.equalsIgnoreCase("3") || check.equalsIgnoreCase("4")) {

                    message.setMessage("Must enter number");
                    request.setAttribute("message", message.getMessage());
                }
            }

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
            ErrorMessage message = new ErrorMessage();
            String check = "";
            check = request.getParameter("id");
            if (check != null && check != "") {
                if (check.equalsIgnoreCase("1") || check.equalsIgnoreCase("2")) {

                    message.setMessage("Must enter 1 character");
                    request.setAttribute("message", message.getMessage());
                } else if (check.equalsIgnoreCase("3") || check.equalsIgnoreCase("4")) {

                    message.setMessage("Must enter number");
                    request.setAttribute("message", message.getMessage());
                }
            }

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

            String proCode = productDAO.checkCharacter(request.getParameter("productCore"));
            String proName = productDAO.checkCharacter(request.getParameter("productName"));
            String quan = productDAO.CheckNumber(request.getParameter("quantity"));
            String price = productDAO.CheckNumber(request.getParameter("price"));
            String cateid = productDAO.CheckNumber(request.getParameter("categogy").trim());
            if (proCode.equals("Invalid")) {

                response.sendRedirect(request.getContextPath() + "/product?view=create&id=1");
            } else if (proName.equals("Invalid")) {

                response.sendRedirect(request.getContextPath() + "/product?view=create&id=2");

            } else if (quan.equals("Invalid")) {
                response.sendRedirect(request.getContextPath() + "/product?view=create&id=3");

            } else if (price.equals("Invalid")) {
                response.sendRedirect(request.getContextPath() + "/product?view=create&id=4");

            } else {
                productDAO.create(proCode, proName, Integer.parseInt(quan), Integer.parseInt(price), Integer.parseInt(cateid));
                response.sendRedirect(request.getContextPath() + "/product?view=list");
            }

        } else if (action.equals("delete")) {
            int id = Integer.parseInt(request.getParameter("id"));
            productDAO.delete(id);
            response.sendRedirect(request.getContextPath() + "/product?view=list");
        } else if (action.equals("edit")) {
            int id = Integer.parseInt(request.getParameter("id"));
            String proCode = productDAO.checkCharacter(request.getParameter("productCore"));
            String proName = productDAO.checkCharacter(request.getParameter("productName"));
            String quan = productDAO.CheckNumber(request.getParameter("quantity"));
            String price = productDAO.CheckNumber(request.getParameter("price"));
            String cateid = productDAO.CheckNumber(request.getParameter("categogy").trim());
            if (proCode.equals("Invalid")) {

                response.sendRedirect(request.getContextPath() + "/product?view=edit&id=1");
            } else if (proName.equals("Invalid")) {

                response.sendRedirect(request.getContextPath() + "/product?view=edit&id=2");

            } else if (quan.equals("Invalid")) {
                response.sendRedirect(request.getContextPath() + "/product?view=edit&id=3");

            } else if (price.equals("Invalid")) {
                response.sendRedirect(request.getContextPath() + "/product?view=edit&id=4");

            } else {
                productDAO.edit(id, proCode, proName, Integer.parseInt(quan), Integer.parseInt(price), Integer.parseInt(cateid));
                response.sendRedirect(request.getContextPath() + "/product?view=list");
            }

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
