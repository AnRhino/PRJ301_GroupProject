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
                <h1 class="mt-5">Edit Product</h1>

                <%
                    List<String> errors = (List<String>) request.getAttribute("errors");
                    if (errors != null && !errors.isEmpty()) {
                %>
                <div class="alert alert-danger">
                    <ul>
                        <% for (String err : errors) { %>
                        <li><%= err %></li>
                        <% } %>
                    </ul>
                </div>
                <% } %>

                <%
                    Product pro = (Product) request.getAttribute("pro");
                    String oldCode = request.getAttribute("oldCode") != null ? request.getAttribute("oldCode").toString() : (pro != null ? pro.getProductCode() : "");
                    String oldName = request.getAttribute("oldName") != null ? request.getAttribute("oldName").toString() : (pro != null ? pro.getProductName() : "");
                    String oldQuantity = request.getAttribute("oldQuantity") != null ? request.getAttribute("oldQuantity").toString() : (pro != null ? String.valueOf(pro.getQuantity()) : "");
                    String oldPrice = request.getAttribute("oldPrice") != null ? request.getAttribute("oldPrice").toString() : (pro != null ? String.valueOf(pro.getPrice()) : "");
                    String oldCate = request.getAttribute("oldCate") != null ? request.getAttribute("oldCate").toString() : (pro != null ? String.valueOf(pro.getCategory().getCategoryID()) : "");
                %>

                <form class="form" method="post" action="<%= request.getContextPath() %>/product">
                    <input type="hidden" name="action" value="edit" />
                    <input type="hidden" name="id" value="<%= pro != null ? pro.getProductID() : "" %>" />

                    <p>
                        <label for="productCore">Product Code</label>
                        <input class="form-control" type="text" id="productCore" name="productCore" value="<%= oldCode %>" required>
                    </p>

                    <p>
                        <label for="productName">Product Name</label>
                        <input class="form-control" type="text" id="productName" name="productName" value="<%= oldName %>" required>
                    </p>

                    <p>
                        <label for="quantity">Quantity</label>
                        <input class="form-control" type="number" min="0" id="quantity" name="quantity" value="<%= oldQuantity %>" required>
                    </p>

                    <p>
                        <label for="price">Price</label>
                        <input class="form-control" type="number" min="0" id="price" name="price" value="<%= oldPrice %>" required>
                    </p>

                    <%
                        List<Category> categories = (List<Category>) request.getAttribute("cate");
                        if (categories != null && !categories.isEmpty()) {
                    %>
                    <p>
                        <label for="categogy">Category</label>
                        <select class="form-select" name="categogy" id="categogy">
                            <% for (Category cate : categories) { %>
                                <option value="<%= cate.getCategoryID() %>" <%= String.valueOf(cate.getCategoryID()).equals(oldCate) ? "selected" : "" %>>
                                    <%= cate.getCategoryName() %>
                                </option>
                            <% } %>
                        </select>
                    </p>
                    <% } else { %>
                    <p class="text-danger">No categories found!</p>
                    <% } %>

                    <p>
                        <button class="btn btn-primary" type="submit">Update</button>
                        <button type="reset" class="btn btn-secondary">Clear</button>
                    </p>
                </form>
            </div>
        </main>

        <%@include file="/WEB-INF/include/footer.jsp" %>
    </body>
</html>
