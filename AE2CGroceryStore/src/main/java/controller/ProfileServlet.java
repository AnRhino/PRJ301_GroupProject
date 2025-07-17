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
import utils.MessageConstants;
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

        // lấy thông tin người dùng
        User userProfile = (User) request.getSession().getAttribute("loggedUser");

        // set giá trị
        String fullName = ProfileValidate.checkSpacing(request.getParameter("fullname"));
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String oldPassword = request.getParameter("oldPassword");

        // Dùng để lọc xem cái nào cập nhật empty
        boolean firstFillter = (fullName == null);
        boolean secondFillter = (email == null);

        UserDAO dao = new UserDAO();

        if (ProfileValidate.checkEmptyInput(email) && ProfileValidate.checkEmptyInput(fullName) && ProfileValidate.checkEmptyInput(password)) {
            // If empty save old name

            request.getSession().setAttribute("loggedUser", dao.getUserByUsername(userProfile.getUsername())); // Set Attribute
            if (firstFillter) {

                // check xem cái nào null
                if (secondFillter) {
                    request.getSession().setAttribute("passwordError", new ErrorMessage(MessageConstants.EMPTY_PASSWORD));
                } else {
                    request.getSession().setAttribute("emailError", new ErrorMessage(MessageConstants.EMPTY_EMAIL));
                }
            } else {
                request.getSession().setAttribute("fullNameError", new ErrorMessage(MessageConstants.EMPTY_FULLNAME));
            }

        } else if (email == null && password == null) { // change name
            if (ProfileValidate.maxAndMinFullNameLength(fullName)) { // Check length
                request.getSession().setAttribute("fullNameError", new ErrorMessage(MessageConstants.INVALID_FULLNAME_LENGHT));

            } else if (ProfileValidate.fullNameValidate(fullName)) { // Check validate 
                request.getSession().setAttribute("fullNameError", new ErrorMessage(MessageConstants.INVALID_FULLNAME));

            } else {
                // change Name
                dao.updateProfile(fullName, userProfile.getEmail(), userProfile.getUsername());
                request.getSession().setAttribute("loggedUser", dao.getUserByUsername(userProfile.getUsername()));

            }
        } else if (fullName == null && password == null) { // Change email
            if (ProfileValidate.emailValidate(email)) { // Check email validate
                request.getSession().setAttribute("emailError", new ErrorMessage(MessageConstants.INVALID_EMAIL));

            } else {
                // Thay đổi Email
                dao.updateProfile(userProfile.getFullName(), email, userProfile.getUsername());
                request.getSession().setAttribute("loggedUser", dao.getUserByUsername(userProfile.getUsername()));

            }
        } else {
            if (!dao.hashMd5(oldPassword).equals(userProfile.getPassword())) { // check old password
                request.getSession().setAttribute("passwordError", new ErrorMessage(MessageConstants.WRONG_OLD_PASSWORD));
            } else if (ProfileValidate.passwordValidate(password)) { // Check password validate
                request.getSession().setAttribute("passwordError", new ErrorMessage(MessageConstants.INVALID_PASSWORD));
            } else {
                // Thay đổi Password
                dao.updatePassword(userProfile.getUsername(), password);
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
