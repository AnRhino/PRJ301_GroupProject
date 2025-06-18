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

            // Kiểm tra null và coi có rỗng không.
            // Nếu có thì hiện thông báo.
            if (productList == null || productList.isEmpty()) {
        %>

        <div class="container-fluid">
            <div>
                <h1 class="fw-bold">Category</h1>
                <div class="bg-light row">
                    <div>There are no category to display.</div>
                </div>
            </div>
        </div>

        <%
            // Nếu có sản phẩm thì hiện ra.
        } else {
        %>

        <div class="container-fluid">
            <div>
                <h1 class="fw-bold">Category</h1>
                <div class="bg-light row">
                    <%
                        for (int i = 0; i < categoryList.size(); i++) {

                            for (int j = 0; j < 4; j++) {
                    %>   

                    <div class="col">
                        <%= categoryList.get(i).getCategoryName()%>
                    </div>

                    <% }
                        }%>
                </div>
            </div>

            <%
                }
            %>
            <jsp:include page="../include/footer.jsp" />
    </body>
</html>
