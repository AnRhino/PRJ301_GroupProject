/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package adminController;

import DAO.OrderDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.OutputStream;
import java.time.format.DateTimeFormatter;
import java.util.List;
import model.Order;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import utils.FileIOUtil;

/**
 *
 * @author Vu Minh Khang - CE191371
 */
@WebServlet(name = "StatisticServlet", urlPatterns = {"/admin/statistic"})
public class StatisticServlet extends HttpServlet {

    private OrderDAO orderDao;

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
            out.println("<title>Servlet StatisticServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet StatisticServlet at " + request.getContextPath() + "</h1>");
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

        // Tạo DAO.
        orderDao = new OrderDAO();

        // Lấy data.
        getData(request);

        // Show ra jsp.
        request.getRequestDispatcher("/WEB-INF/adminFeatures/shopmgmt/statistic.jsp").forward(request, response);
    }

    /**
     * Lấy dữ liệu từ database.
     * 
     * @param request là yêu cầu người dùng.
     */
    private void getData(HttpServletRequest request) {
        request.setAttribute("orderList", orderDao.getAllOrderData());
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

        // Tạo DAO.
        orderDao = new OrderDAO();

        // Lưu data về máy dưới dạng file excel.
        exportToExcelFile(response);

        // Chuyển hướng về chính servlet này.
        response.sendRedirect("/admin/statistic");
    }

    private void exportToExcelFile(HttpServletResponse response) throws IOException {

        // Lấy data.
        List<Order> orderData = orderDao.getAllOrderData();

        // Tạo excel file.
        Workbook workbook = new XSSFWorkbook();
        // Tạo sheet 1 cho the excel file.
        Sheet sheet = workbook.createSheet("Sheet1");

        // Tạo cột header cho sheet.
        createTableHeader(sheet);

        // Thêm data và từng cột.
        addDataToSheet(sheet, orderData);

        // Thiết lập header HTTP để buộc tải về.
        // Thiết lập là dạng file excel. Tên file là report.xlsx.
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=\"report.xlsx\"");

        // Ghi workbook vào response output stream.
        try ( OutputStream os = response.getOutputStream()) {
            workbook.write(os);
        }
        
        // Đóng cổng truyền data sau khi đã ghi vào response.
        workbook.close();
    }

    /**
     * Tạo header cho sheet.
     * 
     * @param sheet là sheet trong file cần ghi header.
     */
    private void createTableHeader(Sheet sheet) {
        
        // Đặt cột đầu tiên trong sheet là header và set giá trị cho từng cột.
        Row headerRow = sheet.createRow(0);
        String[] header = {"OrderID", "Order Date", "Delivery Date", "ProductID",
            "Product Code", "Product Name", "Status", "Quantity",
            "UnitPrice", "Total"};
        
        // Đặt tên cho từng header.
        for (int i = 0; i < header.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(header[i]);
        }
    }

    /**
     * Thêm dữ liệu vào sheet.
     * 
     * @param sheet là sheet cần thêm data.
     * @param orderData là data lấy từ cơ sở dữ liệu
     */
    private void addDataToSheet(Sheet sheet, List<Order> orderData) {

        for (int i = 1; i <= orderData.size(); i++) {

            // Tạo từng hàng.
            Row row = sheet.createRow(i);

            // Thêm data vào từng hàng.
            addDataToEachRow(row, orderData.get(i - 1));
        }

    }

    /**
     * Thêm data cho từng cell trong từng hàng.
     * 
     * @param row là hàng cần thêm data.
     * @param order là 1 record tương ứng với 1 hàng.
     */
    private void addDataToEachRow(Row row, Order order) {

        // Thêm vào id của order.     
        row.createCell(0).setCellValue(order.getId());
        row.createCell(1).setCellValue(order.getOrderDate() != null ? order.getOrderDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")) : "Unknown day");
        row.createCell(2).setCellValue(order.getDeliveryDate() != null ? order.getDeliveryDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")) : "Unknown day");
        row.createCell(3).setCellValue(order.getOrderItems().get(0).getProduct().getProductID());
        row.createCell(4).setCellValue(order.getOrderItems().get(0).getProduct().getProductCode());
        row.createCell(5).setCellValue(order.getOrderItems().get(0).getProduct().getProductName());
        row.createCell(6).setCellValue(order.getStatus().getDescription());
        row.createCell(7).setCellValue(order.getOrderItems().get(0).getQuantity());
        row.createCell(8).setCellValue(order.getOrderItems().get(0).getUnitPrice());
        row.createCell(9).setCellValue(order.getOrderItems().get(0).getTotalPrice());
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
