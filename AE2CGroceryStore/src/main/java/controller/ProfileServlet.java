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
import validate.ProfileValidate;
import validate.UserCredsValidate;

/**
 *
 * @author Le Thien Tri - CE191249
 */
@WebServlet(name = "ProfileServlet", urlPatterns = {"/user-profile"})
public class ProfileServlet extends HttpServlet {

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
            out.println("<title>Servlet ProfileServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ProfileServlet at " + request.getContextPath() + "</h1>");
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

        if (view == null || view.isBlank()) {

        } else if (view.equals("editFullName")) {
            boolean editFullName = true;
            request.setAttribute("editFullName", editFullName);

        } else if (view.equals("editEmail")) {
            boolean editEmail = true;
            request.setAttribute("editEmail", editEmail);

        } else {
            request.getSession().removeAttribute("fullNameError");
            request.getSession().removeAttribute("emailError");
        }
        request.getRequestDispatcher("WEB-INF/users/profile.jsp").forward(request, response);

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

        User userProfile = (User) request.getSession().getAttribute("loggedUser");

        String fullName = ProfileValidate.checkSpacing(request.getParameter("fullname"));
        String email = request.getParameter("email");
        
        boolean whoError = (fullName == null);
        UserDAO dao = new UserDAO();

        if (ProfileValidate.checkEmptyInput(email) && ProfileValidate.checkEmptyInput(fullName)) {
            // If empty save old name
            dao.updateFullName(userProfile.getFullName(), userProfile.getEmail(), userProfile.getUsername()); // Update DAO
            request.getSession().setAttribute("loggedUser", dao.getUserByUsername(userProfile.getUsername())); // Set Attribute
            if (whoError) {
                request.getSession().setAttribute("emailError", new ErrorMessage("Email cannot be empty."));
            } else {
                request.getSession().setAttribute("fullNameError", new ErrorMessage("Full name cannot be empty."));
            }

        } else if (email == null) {
            if (ProfileValidate.maxAndMinFullNameLength(fullName)) { // Check length
                request.getSession().setAttribute("fullNameError", new ErrorMessage("FullName lenght can't be lower than 1 and upper than 50."));

            } else if (ProfileValidate.fullNameValidate(fullName)) { // Check validate 
                request.getSession().setAttribute("fullNameError", new ErrorMessage("This name cannot be used."));

            } else {
                // change Name
                dao.updateFullName(fullName, userProfile.getEmail(), userProfile.getUsername());
                request.getSession().setAttribute("loggedUser", dao.getUserByUsername(userProfile.getUsername()));

            }
        } else if (fullName == null) { // Change email
            if (ProfileValidate.emailValidate(email)) { // Check email validate
                request.getSession().setAttribute("emailError", new ErrorMessage("The email address you entered is invalid."));

            } else {
                // Thay đổi Email
                dao.updateFullName(userProfile.getFullName(), email, userProfile.getUsername());
                request.getSession().setAttribute("loggedUser", dao.getUserByUsername(userProfile.getUsername()));

            }
        }
        response.sendRedirect(request.getContextPath() + "/user-profile");
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
