/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import DAO.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.ErrorMessage;
import model.User;
import validate.UserCredsValidate;

/**
 *
 * @author Dinh Cong Phuc - CE190770
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

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
//            out.println("<!DOCTYPE html>");
//            out.println("<html>");
//            out.println("<head>");
//            out.println("<title>Servlet LoginServlet</title>");  
//            out.println("</head>");
//            out.println("<body>");
//            out.println("<h1>Servlet LoginServlet at " + request.getContextPath () + "</h1>");
//            out.println("</body>");
//            out.println("</html>");
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
        request.getRequestDispatcher("WEB-INF/credentials/login.jsp").forward(request, response);
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
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        ErrorMessage usernameMsg = UserCredsValidate.usernameValid(username);
        ErrorMessage passwordMsg = UserCredsValidate.passwordValid(password);

        if (usernameMsg != null || passwordMsg != null) { // If input is not valid
            // Store validation error msg in request
            request.setAttribute("usernameMsg", usernameMsg);
            request.setAttribute("passwordMsg", passwordMsg);

            // Keep the form data so user doesn't need to retype
            request.setAttribute("username", username);
            request.setAttribute("password", password);
        } else { // All validity check passed, proceed to login
            // Log user in
            UserDAO dao = new UserDAO();
            boolean isAuthenticated = dao.authenticate(username, password);

            // Redirect after login
            if (!isAuthenticated) { // If login fail
                request.setAttribute("loginError", "Please check your Username/Password");

                // Keep the form data so user doesn't need to retype
                request.setAttribute("username", username);
                request.setAttribute("password", password);

                // Redirect to login
                response.sendRedirect(request.getContextPath());
                return;
            } else { // If login success
                // Create new session
                User loggedUser = dao.getUserByUsername(username);
                HttpSession session = request.getSession();
                session.setAttribute("loggedUser", loggedUser);
                session.setAttribute("roleId", loggedUser.getRoleId());
               
                // Get infomation for profile
                session.setAttribute("profileUser", loggedUser);

                // Redirect to homepage
                response.sendRedirect(request.getContextPath());
                return;
            }
        }
        request.getRequestDispatcher("WEB-INF/credentials/login.jsp").forward(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Login";
    }// </editor-fold>

}
