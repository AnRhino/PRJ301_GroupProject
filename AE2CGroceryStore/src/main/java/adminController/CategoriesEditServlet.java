/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

 /*
Not done
doPost
 */
package adminController;

import DAO.CategoryDAO;
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
import model.Category;
import model.ErrorMessage;
import utils.FileIOUtil;
import utils.MessageConstants;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Dinh Cong Phuc - CE190770
 */
@WebServlet(name = "CategoriesEditServlet", urlPatterns = {"/admin/categories/edit"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50 // 50MB
)
public class CategoriesEditServlet extends HttpServlet {

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
            out.println("<title>Servlet CategoriesEditServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CategoriesEditServlet at " + request.getContextPath() + "</h1>");
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
    int categoryID;
    CategoryDAO dao;
    Category category;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//        processRequest(request, response);
        categoryID = Integer.parseInt(request.getParameter("categoryID"));
        dao = new CategoryDAO();
        category = dao.getOneByID(categoryID);
        request.setAttribute("category", category);
        request.getRequestDispatcher("/WEB-INF/adminFeatures/categoriesMgmt/categoriesEdit.jsp").forward(request, response);
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
        // Initialize variables
        boolean hasErrors = false;
        String errorMessage = null;

        try {
            boolean changedName = (!category.getCategoryName().equals(request.getParameter("categoryName")));
            boolean changedHidden = (category.checkIsHidden() != Boolean.parseBoolean(request.getParameter("isHidden")));

            // Process name change
            if (changedName) {
                if (dao.updateCategoryName(categoryID, request.getParameter("categoryName")) <= 0) {
                    hasErrors = true;
                    errorMessage = "Failed to update category name.";
                }
            }

            // Process hidden status
            if (changedHidden && !hasErrors) {
                int hiddenStatus = Boolean.parseBoolean(request.getParameter("isHidden")) ? 1 : 0;
                if (dao.updateHidden(categoryID, hiddenStatus) <= 0) {
                    hasErrors = true;
                    errorMessage = "Failed to update category visibility.";
                }
            }

            // Process image upload
            Part coverImgPart = request.getPart("coverImg");
            boolean changedCoverImg = (coverImgPart != null && coverImgPart.getSize() > 0);

            if (changedCoverImg && !hasErrors) {
                String result = processImageUpload(coverImgPart, categoryID, request);
                if (result != null) {
                    hasErrors = true;
                    errorMessage = result;
                }
            }

            if (hasErrors) {
                request.setAttribute("errorMessage", new ErrorMessage(errorMessage));
                request.getRequestDispatcher("/WEB-INF/adminFeatures/categoriesMgmt/categoriesEdit.jsp")
                        .forward(request, response);
                return;
            }

            // If everything is successful
            response.sendRedirect(request.getContextPath() + "/admin/categories");

        } catch (Exception e) {
            Logger.getLogger(CategoriesEditServlet.class.getName()).log(Level.SEVERE, "Error updating category", e);
            request.setAttribute("errorMessage", new ErrorMessage("An unexpected error occurred."));
            request.getRequestDispatcher("/WEB-INF/adminFeatures/categoriesMgmt/categoriesEdit.jsp")
                    .forward(request, response);
        }
    }

    private String processImageUpload(Part coverImgPart, int categoryID, HttpServletRequest request) {
        try {
            String[] allowedExtensions = {".jpg", ".png", ".jpeg"};
            File folder = new File(FileIOUtil.getRootPath() + "images/category");

            // Create directory if it doesn't exist
            if (!folder.exists() && !folder.mkdirs()) {
                return "Failed to create upload directory.";
            }

            // Validate file name
            String submittedFileName = coverImgPart.getSubmittedFileName();
            if (submittedFileName == null || submittedFileName.isBlank()) {
                return "Uploaded file has no name.";
            }

            // Validate file extension
            int dotIndex = submittedFileName.lastIndexOf(".");
            if (dotIndex <= 0) {
                return "Invalid file name: " + submittedFileName;
            }

            String fileExtension = submittedFileName.substring(dotIndex).toLowerCase();
            if (!Arrays.asList(allowedExtensions).contains(fileExtension)) {
                return "Only " + Arrays.toString(allowedExtensions) + " files are allowed.";
            }

            // If category has coverImg then delete it

            if (!category.getCoverImg().isEmpty() || !category.getCoverImg().isBlank() || category.getCoverImg() == null) {
                int oldDotIndex = category.getCoverImg().lastIndexOf("/");
                String oldFilename = category.getCoverImg().substring(oldDotIndex + 1);
                File oldFile = new File(folder, oldFilename);
                // Delete old file
                if (oldFile.exists() && !oldFile.delete()) {
                    return "Failed to delete old image.";
                }
            }
            
            // Process file upload
            String newFilename = categoryID + fileExtension;
            File newFile = new File(folder, newFilename);

            // Delete existing file if it exists
            if (newFile.exists() && !newFile.delete()) {
                return "Failed to replace existing image.";
            }

            // Save new file
            FileIOUtil.fileUploader(coverImgPart, newFile);

            // Update database
            String imgPath = "category/" + newFilename;
            if (dao.updateCoverImg(categoryID, imgPath) <= 0) {
                return "Failed to update image in database.";
            }

            return null; // No error

        } catch (Exception e) {
            Logger.getLogger(CategoriesEditServlet.class.getName()).log(Level.SEVERE, "Error processing image upload", e);
            return "Error processing image: " + e.getMessage();
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
