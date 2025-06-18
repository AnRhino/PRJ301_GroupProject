<%-- 
    Document   : show
    Created on : Jun 18, 2025, 10:30:31 AM
    Author     : Vu Minh Khang - CE191371
--%>

<%@page import="model.Category"%>
<%@page import="model.Product"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../include/header.jsp" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link href="../../assets/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
        <script src="../../assets/js/bootstrap.bundle.min.js" type="text/javascript"></script>
    </head>
    <body>
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
    </body>
</html>
<jsp:include page="../include/footer.jsp" />
