/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import DAO.UserDAO;
import adminController.CategoriesCreateServlet;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.File;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.ErrorMessage;
import model.User;
import utils.FileIOUtil;
import utils.MessageConstants;

/**
 *
 * @author Vu Minh Khang - CE191371
 */
@WebServlet(name = "ImageProfileServlet", urlPatterns = {"/image-profile"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50 // 50MB
)
public class ImageProfileServlet extends HttpServlet {

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
            out.println("<title>Servlet ImageProfileServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ImageProfileServlet at " + request.getContextPath() + "</h1>");
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

        doPost(request, response);
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

        UserDAO dao = new UserDAO();
        String errorMessage = null;
        String imagePath = null;
        boolean hasErrors = false;

        // Get user ID.
        int userID = ((User) request.getSession().getAttribute("loggedUser")).getId();

        // Process image upload if no errors
        if (!hasErrors) {
            try {
                Part coverImgPart = request.getPart("coverImg");
                
                // Check if an image was uploaded
                if (coverImgPart != null && coverImgPart.getSize() > 0) {

                    // Process the image upload
                    String result = processImageUpload(coverImgPart, userID);
                    if (result != null) {
                        // If there was an error processing the image
                        hasErrors = true;
                        errorMessage = result;
                    } else {
                        // Set the image path for the new category
                        imagePath = "users/" + userID
                                + coverImgPart.getSubmittedFileName()
                                        .substring(coverImgPart.getSubmittedFileName().lastIndexOf("."))
                                        .toLowerCase();
                    }
                }

            } catch (Exception e) {
                hasErrors = true;
                errorMessage = MessageConstants.ERROR_IMAGE_UPLOAD_MESSAGE + e.getMessage();
                Logger.getLogger(CategoriesCreateServlet.class.getName())
                        .log(Level.SEVERE, "Error in image upload", e);
            }
        }

        // If there are any errors, return to the create form
        if (hasErrors) {
            request.getSession().setAttribute("errorMessage", new ErrorMessage(errorMessage));
        } else {
            removeOldImage(((User) request.getSession().getAttribute("loggedUser")).getCoverImg());
            dao.setImgUrl(userID, imagePath);
            ((User) request.getSession().getAttribute("loggedUser")).setCoverImg(imagePath);
        }
        
        response.sendRedirect(request.getContextPath() + "/user-profile");
    }
    
    private void removeOldImage(String imagePath) {
        
        File oldImageFile = new File(FileIOUtil.getRootPath() + "images/" + imagePath);

        if (oldImageFile.exists()) {
            oldImageFile.delete();
        }
    }

    private String processImageUpload(Part coverImgPart, int userID) {
        try {
            String[] allowedExtensions = {".jpg", ".png", ".jpeg"};
            File folder = new File(FileIOUtil.getRootPath() + "images/users");

            // Create directory if it doesn't exist
            if (!folder.exists() && !folder.mkdirs()) {
                return MessageConstants.UNEXIST_DIRECTORY_MESSAGE;
            }

            // Validate file name
            String submittedFileName = coverImgPart.getSubmittedFileName();
            if (submittedFileName == null || submittedFileName.isBlank()) {
                return MessageConstants.NO_NAME_FILE_MESSAGE;
            }

            // Validate file extension
            int dotIndex = submittedFileName.lastIndexOf(".");
            if (dotIndex <= 0) {
                return MessageConstants.INVALID_FILE_NAME_MESSAGE + submittedFileName;
            }

            String fileExtension = submittedFileName.substring(dotIndex).toLowerCase();
            if (!Arrays.asList(allowedExtensions).contains(fileExtension)) {
                return MessageConstants.PREFIX_UNALLOWED_EXTENSION_MESSAGE + Arrays.toString(allowedExtensions) + MessageConstants.POSTFIX_UNALLOWED_EXTENSION_MESSAGE;
            }

            // Process file upload
            String newFilename = userID + fileExtension;
            File newFile = new File(folder, newFilename);

            // Delete existing file if it exists
            if (newFile.exists() && !newFile.delete()) {
                return MessageConstants.FAILED_DELETE_FILE_MESSAGE;
            }

            // Save new file
            FileIOUtil.fileUploader(coverImgPart, newFile);

            return null; // No error

        } catch (Exception e) {
            Logger.getLogger(CategoriesCreateServlet.class.getName())
                    .log(Level.SEVERE, "Error processing image upload", e);
            return MessageConstants.UNKNOWN_FILE_ERROR_MESSAGE + e.getMessage();
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
