<%-- 
    Document   : category
    Created on : Jun 20, 2025, 3:59:52 PM
    Author     : Vu Minh Khang - CE191371
--%>

<%@page import="model.Product"%>
<%@page import="java.util.List"%>
<%@page import="model.Category"%>
<%@page import="model.Category"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <%@include file="../include/head.jsp" %>
    <body>
        <%@include file="../include/header.jsp" %>

        <%
            // Lấy danh sách danh mục và sản phầm từ db.
            List<Category> categoryList = (List) request.getAttribute("categoryList");
            List<Product> productList = (List) request.getAttribute("productList");
            String name = (String) request.getAttribute("categoryType");

            System.out.println(categoryList.size());

            // Kiểm tra null và coi có rỗng không.
            // Nếu có thì hiện thông báo.
            if (categoryList == null || productList == null || name == null || productList.isEmpty() || categoryList.isEmpty()) {
        %>

        <div class="container-fluid">
            <div>
                <h1 class="fw-bold">Error</h1>
                <div class="row">
                    <div>There are no product to display.</div>
                </div>
            </div>
        </div>

        <%
            // Nếu có sản phẩm thì hiện ra.
        } else {
        %>

        <div class="container-fluid">
            <div class="ms-5 me-5"> 
                <div class="row">
                    <div class="col-4">
                        <%
                            for (int i = 0; i < categoryList.size() / 4; i++) {


                        %>   

                        <div class="col-3 d-flex justify-content-center my-3 border border-primary">
                            <form action="/userProduct" method="get">
                                <input type="hidden" name="view" value="category">
                                <input type="hidden" name="id" value="<%= categoryList.get(i).getCategoryID()%>">
                                <input type="hidden" name="name" value="<%= categoryList.get(i).getCategoryName()%>">
                                <button class="btn btn-link p-0 border-0 bg-transparent">
                                    <div class="row">
                                        <div class="col-12 d-flex justify-content-center">
                                            <%= categoryList.get(i).getCategoryName()%>
                                        </div>
                                    </div>
                                </button>
                            </form>
                        </div>

                        <%}%>
                    </div>
                    <div class="col-8">
                        <div class="ms-5 me-5">
                            <h1 class="fw-bold"><%= name%></h1>
                            <div class="row">
                                <%
                                    for (int i = 0; i < productList.size() / 4; i++) {

                                        for (int j = 0; j < 4; j++) {
                                %>   

                                <div class="col-3 d-flex justify-content-center my-3 border border-secondary">
                                    <div class="row">
                                        <div class="col-12 d-flex justify-content-center">
                                            <img src="assets/images/placeHolder.jpg" alt="placeholder">
                                        </div>
                                        <div class="col-12 d-flex justify-content-center">
                                            <%= productList.get(i * 4 + j).getProductName()%>
                                        </div>
                                        <div class="col-12 d-flex justify-content-center">
                                            Price: 
                                            <%= productList.get(i * 4 + j).getPrice()%>
                                        </div>
                                    </div>
                                </div>

                                <% }
                                    }%>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <%
                }
            %>

            <jsp:include page="../include/footer.jsp" />
    </body>
</html>
