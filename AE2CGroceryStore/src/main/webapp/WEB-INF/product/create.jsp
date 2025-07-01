<%-- 
    Document   : create
    Created on : Jun 16, 2025, 4:04:35 PM
    Author     : Phan Duc Tho - CE191246
--%>

<%@page import="model.Category"%>
<%@page import="model.Product"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
    <%@include file="../include/head.jsp" %>
    <body>
        <%@include file="/WEB-INF/include/header.jsp" %>
        <main class="flex-shrink-0">
            <div class="container">
                <h1 class="mt-5">Create product</h1>

              
                <%
                    List<String> errorMessage = (List<String>) request.getAttribute("errorMessage");
                    String message = "";
                    Object msg = request.getAttribute("msg");
                    if (msg != null) {
                        message = msg.toString();
                    }

                    if (errorMessage != null && !errorMessage.isEmpty()) {
                %>
                <div class="alert alert-danger">
                    <ul>
                        <% for (String err : errorMessage) {%>
                        <li><%= err%></li>
                            <% } %>
                    </ul>
                </div>
                <% } else if (!message.isEmpty()) {%>
                <div class="alert alert-danger"><%= message%></div>
                <a class="btn btn-success" href="product?view=create">Retry</a>
                <a class="btn btn-primary" href="product?view=list">Home</a>
                <% }%>

              
                <form class="form" method="post" action="<%= request.getContextPath()%>/product">
                    <input type="hidden" name="action" value="create" />

                    <p>
                        <label for="name">Product code</label>
                        <input class="form-control" type="text" id="productCore" name="productCore"
                               value="<%= request.getAttribute("PCode") != null ? request.getAttribute("PCode") : ""%>" required >
                    </p>
                    <p>
                        <label for="name">Product Name</label>
                        <input class="form-control" type="text" id="productName" name="productName"
                               value="<%= request.getAttribute("PName") != null ? request.getAttribute("PName") : ""%>" required>
                    </p>
                    <p>
                        <label for="name">Quantity</label>
                        <input class="form-control" type="text" min="0" id="quantity" name="quantity"
                               value="<%= request.getAttribute("PQuantity") != null ? request.getAttribute("PQuantity") : ""%>" required>
                    </p>
                    <p>
                        <label for="name">Price</label>
                        <input class="form-control" type="text" min="0" id="price" name="price"
                               value="<%= request.getAttribute("PPrice") != null ? request.getAttribute("PPrice") : ""%>" required>
                    </p>

                    <% List<Category> categogy = (List) request.getAttribute("cate");
                        if (categogy != null) {
                    %>
                    <p>
                        <label for="artist">Category</label>
                        <select class="form-select" name="categogy" id="categogy">
                            <% for (Category cate : categogy) {%>
                            <option value="<%= cate.getCategoryID()%>"><%= cate.getCategoryName()%></option>
                            <% } %>
                        </select>
                    </p>
                    <% } else { %>
                    <p class="text-danger">list is empty</p>
                    <% }%>

                    <p>
                        <button class="btn btn-success" type="submit">Add</button>
                        <button type="reset" class="btn btn-secondary">Clear</button>
                    </p>
                </form>
            </div>
        </main>
        <%@include file="/WEB-INF/include/footer.jsp" %>
    </body>
</html>
