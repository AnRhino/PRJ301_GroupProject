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
            Category c = (Category) request.getAttribute("categoryType");

            // Kiểm tra null và coi có rỗng không.
            // Nếu có thì hiện thông báo.
            if (categoryList == null || productList == null || productList.isEmpty() || categoryList.isEmpty()) {
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
                    <div class="col-3">
                        <h1 class="fw-bold">Other category</h1>
                        <%
                            for (Category cate : categoryList) {
                        %>   

                        <div class="col-12 d-flex justify-content-center my-3 border bg-dark py-2">
                            <form action="<%= request.getContextPath()%>/user-product" method="get">
                                <input type="hidden" name="view" value="category">
                                <input type="hidden" name="id" value="<%= cate.getCategoryID()%>">
                                <button class="btn p-0 border-0 text-white">
                                    <div class="row">
                                        <div class="col-12 d-flex justify-content-center">
                                            <%= cate.getCategoryName()%>
                                        </div>
                                    </div>
                                </button>
                            </form>
                        </div>

                        <%}%>
                    </div>
                    <div class="col-9">
                        <div class="ms-5 me-5">
                            <h1 class="fw-bold"><%= c.getCategoryName()%></h1>
                            <div class="row">
                                <%
                                    for (Product prod : productList) {
                                %>   

                                <div class="col-3 d-flex justify-content-center my-3 border border-secondary">
                                    <form action="<%= request.getContextPath()%>/user-product" method="get">
                                        <input type="hidden" name="view" value="product">
                                        <input type="hidden" name="id" value="<%= prod.getProductID()%>">
                                        <button class="btn-secondary border-secondary">
                                            <div class="row">
                                                <div class="col-12 d-flex justify-content-center">
                                                    <img src="assets/images/placeHolder.jpg" alt="placeholder">
                                                </div>
                                                <div class="col-12 d-flex justify-content-center">
                                                    <%= prod.getProductName()%>
                                                </div>
                                                <div class="col-12 d-flex justify-content-center">
                                                    Price: 
                                                    <%= prod.getPrice()%> VND
                                                </div>
                                            </div>
                                        </button>
                                    </form>
                                </div>

                                <% } %>
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
