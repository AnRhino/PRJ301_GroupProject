<%-- 
    Document   : show
    Created on : Jun 18, 2025, 10:30:31 AM
    Author     : Vu Minh Khang - CE191371
--%>

<%@page import="model.Category"%>
<%@page import="model.Product"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <%@include file="../include/head.jsp" %>
    <body>
        <%@include file="../include/header.jsp" %>
        <%
            // Lấy danh sách danh mục và sản phầm từ db.
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
                <div class="bg-light row">
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
                <div class="bg-secondary row">
                    <%
                        for (int i = 0; i < categoryList.size() / 4; i++) {

                            for (int j = 0; j < 4; j++) {
                    %>   

                    <div class="col-3 d-flex justify-content-center my-3">
                        <div class="row">
                            <div class="col-12 d-flex justify-content-center">
                                <img src="assets/images/placeHolder.jpg" alt="placeholder">
                            </div>
                            <div class="col-12 d-flex justify-content-center">
                                <%= categoryList.get(i * 4 + j).getCategoryName()%>
                            </div>
                        </div>
                    </div>

                    <% }
                        }%>
                </div>
            </div>

            <div class="ms-5 me-5">
                <h1 class="fw-bold">Product</h1>
                <div class="bg-secondary row">
                    <%
                        for (int i = 0; i < productList.size() / 6; i++) {

                            for (int j = 0; j < 6; j++) {
                    %>   

                    <div class="col-3 d-flex justify-content-center my-3">
                        <div class="row">
                            <div class="col-12 d-flex justify-content-center">
                                <img src="assets/images/placeHolder.jpg" alt="placeholder">
                            </div>
                            <div class="col-12 d-flex justify-content-center">
                                <%= productList.get(i * 6 + j).getProductName()%>
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
        <jsp:include page="../include/footer.jsp" />
    </body>
</html>
