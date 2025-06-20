<%-- 
    Document   : index
    Created on : Jun 20, 2025, 1:30:22 AM
    Author     : Le Thien Tri - CE191249
--%>

<%@page import="model.Product"%>
<%@page import="DAO.ProductDAO"%>
<%@page import="DAO.CategoryDAO"%>
<%@page import="model.Category"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <%@include file="WEB-INF/include/head.jsp" %>
    <body>
        <%@include file="/WEB-INF/include/header.jsp" %>
        <%
            // Lấy danh sách danh mục và sản phầm từ db.
            CategoryDAO categoryDao = new CategoryDAO();
            ProductDAO productDao = new ProductDAO();

            request.setAttribute("categoryList", categoryDao.getAll());
            request.setAttribute("productList", productDao.getAll());

            List<Product> productList = (List) request.getAttribute("productList");
            List<Category> categoryList = (List) request.getAttribute("categoryList");

            System.out.println(categoryList.size());

            // Kiểm tra null và coi có rỗng không.
            // Nếu có thì hiện thông báo.
            if (productList == null || categoryList == null || productList.isEmpty() || categoryList.isEmpty()) {
        %>

        <div class="container-fluid">
            <div>
                <h1 class="fw-bold">Category</h1>
                <div class="row">
                    <div>There are no category and product to display.</div>
                </div>
            </div>
        </div>

        <%
            // Nếu có sản phẩm thì hiện ra.
        } else {
        %>

        <div class="container-fluid">
            <div class="ms-5 me-5">
                <h1 class="fw-bold">Category</h1>
                <div class="row">
                    <%
                        for (int i = 0; i < categoryList.size() / 4; i++) {

                            for (int j = 0; j < 4; j++) {
                    %>   

                    <div class="col-3 d-flex justify-content-center my-3 border border-primary">
                        <div class="row">
                            <div class="col-12 d-flex justify-content-center">
                                <img src="assets/images/placeHolder.jpg" alt="placeholder">
                            </div>
                            <div class="col-12 d-flex justify-content-center">
                                <a class="text-decoration-none text-dark" href="login"> <%= categoryList.get(i * 4 + j).getCategoryName()%> </a>
                            </div>
                        </div>
                    </div>

                    <% }
                        }%>
                </div>
            </div>

            <div class="ms-5 me-5">
                <h1 class="fw-bold">Product</h1>
                <div class="row">
                    <%
                        for (int i = 0; i < productList.size() / 6; i++) {

                            for (int j = 0; j < 6; j++) {
                    %>   

                    <div class="col-3 d-flex justify-content-center my-3 border border-secondary">
                        <div class="row">
                            <div class="col-12 d-flex justify-content-center">
                                <img src="assets/images/placeHolder.jpg" alt="placeholder">
                            </div>
                            <div class="col-12 d-flex justify-content-center">
                                <a class="text-decoration-none text-dark" href="login"> <%= productList.get(i * 6 + j).getProductName()%> </a>
                            </div>
                            <div class="col-12 d-flex justify-content-center">
                                Price: 
                                <%= productList.get(i * 6 + j).getPrice()%>
                            </div>
                        </div>
                    </div>

                    <% }
                        }%>
                </div>
            </div>
        </div>
        <%
            }
        %>
        <%@include file="/WEB-INF/include/footer.jsp" %>
    </body>
</html>
