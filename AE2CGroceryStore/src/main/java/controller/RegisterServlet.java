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
import model.UserInputValidate;

/**
 *
 * @author Dinh Cong Phuc - CE190770
 */
@WebServlet(name = "RegisterServlet", urlPatterns = { "/register" })
public class RegisterServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet RegisterServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet RegisterServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the
    // + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // processRequest(request, response);
        request.getRequestDispatcher("WEB-INF/credentials/register.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // processRequest(request, response);
        String email = request.getParameter("email");
        String fullName = request.getParameter("fullName");
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        ErrorMessage emailMsg = UserInputValidate.emailValid(email);
        ErrorMessage fullNameMsg = UserInputValidate.fullNameValid(fullName);
        ErrorMessage usernameMsg = UserInputValidate.usernameValid(username);
        ErrorMessage passwordMsg = UserInputValidate.passwordValid(password);

        if (emailMsg != null || fullNameMsg != null || usernameMsg != null || passwordMsg != null) {
            // Store validation error messages in request
            request.setAttribute("emailMsg", emailMsg);
            request.setAttribute("fullNameMsg", fullNameMsg);
            request.setAttribute("usernameMsg", usernameMsg);
            request.setAttribute("passwordMsg", passwordMsg);
            
            // Keep the form data so user doesn't have to retype
            request.setAttribute("email", email);
            request.setAttribute("fullName", fullName);
            request.setAttribute("username", username);
            request.setAttribute("password", password);
        } else {
            // All validations passed, proceed with registration
            UserDAO dao = new UserDAO();
            // Reg new user
            User registerUser = dao.register(username, password, fullName, email);

            if (registerUser == null) { // If user null because of information already exist, invalid character,...
                request.setAttribute("registerError", "Registration failed, Username or Email already exists");

            } else { // if user valid (register success)
                HttpSession session = request.getSession(); // get new session
                session.setAttribute("loggedUser", registerUser);

                response.sendRedirect(request.getContextPath() + "/index.jsp"); // redirect to homepage
                return;
            }
        }
        request.getRequestDispatcher("WEB-INF/credentials/register.jsp").forward(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Register";
    }// </editor-fold>

}
