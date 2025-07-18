<%-- 
    Document   : create
    Created on : Jun 16, 2025, 4:04:35 PM
    Author     : Phan Duc Tho - CE191246
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.Product"%>
<%@page import="model.Category"%>
<%@page import="java.util.List"%>
<!DOCTYPE html>
<html>
    <%@include file="/WEB-INF/include/head.jsp" %>
    <body>
        <%@include file="/WEB-INF/include/header.jsp" %>

        <main class="flex-shrink-0">
            <div class="container">
                <h1 class="mt-5">Edit Product</h1>

                <%
                    List<String> errors = (List<String>) request.getAttribute("errorMessage");
                    if (errors != null && !errors.isEmpty()) {
                %>
                <div class="alert alert-danger">
                    <ul>
                        <% for (String err : errors) {%>
                        <li><%= err%></li>
                            <% } %>
                    </ul>
                </div>
                <% } %>

                <%
                    Product pro = (Product) request.getAttribute("pro");
                    String pCode = request.getAttribute("oldCode") != null ? (String) request.getAttribute("oldCode") : pro.getProductCode();
                    String pName = request.getAttribute("oldName") != null ? (String) request.getAttribute("oldName") : pro.getProductName();
                    String pQuantity = request.getAttribute("oldQuantity") != null ? (String) request.getAttribute("oldQuantity") : String.valueOf(pro.getQuantity());
                    String pPrice = request.getAttribute("oldPrice") != null ? (String) request.getAttribute("oldPrice") : String.valueOf(pro.getPrice());
                    String pCate = request.getAttribute("oldCate") != null ? (String) request.getAttribute("oldCate") : String.valueOf(pro.getCategory().getCategoryID());
                %>

                <form id="form-edit" method="post" action="<%= request.getContextPath()%>/admin/product">
                    <input type="hidden" name="action" value="edit" />
                    <input type="hidden" id="id" name="ProductID" value="<%= pro.getProductID()%>" />

                    <p>
                        <label for="productCode">Product Code</label>
                        <input class="form-control" type="text" id="productCode" name="productCode" value="<%= pCode%>" required />
                    </p>

                    <p>
                        <label for="productName">Product Name</label>
                        <input class="form-control" type="text" id="productName" name="productName" value="<%= pName%>" required />
                    </p>

                    <p>
                        <label for="quantity">Quantity</label>
                        <input class="form-control" id="quantity" name="quantity" value="<%= pQuantity%>" required />
                    </p>

                    <p>
                        <label for="price">Price</label>
                        <input class="form-control" id="price" name="price" value="<%= pPrice%>" required />
                    </p>

                    <%
                        List<Category> categories = (List<Category>) request.getAttribute("cate");
                        if (categories != null && !categories.isEmpty()) {
                    %>
                    <p>
                        <label for="category">Category</label>
                        <select class="form-select" name="category" id="category">
                            <% for (Category cate : categories) {%>
                            <option value="<%= cate.getCategoryID()%>" <%= String.valueOf(cate.getCategoryID()).equals(pCate) ? "selected" : ""%>>
                                <%= cate.getCategoryName()%>
                            </option>
                            <% } %>
                        </select>
                    </p>
                    <% } else { %>
                    <p class="text-danger">No categories available</p>
                    <% }%>

                    <p>
                        <button class="btn btn-primary" type="submit">Update</button>
                        <button type="reset" class="btn btn-secondary">Clear</button>
                    </p>
                </form>
            </div>
        </main>

        <%@include file="/WEB-INF/include/footer.jsp" %>
        <script>
            $("#form-edit").validate({
                rules: {
                    id: {
                        required: true,
                        range: [$("#id").val(), $("#id").val()]
                    },
                    productCode: {
                        required: true
                    },
                    productName: {
                        required: true
                    },
                    quantity: {
                        required: true,
                        digits: true,
                        min: 1
                    },
                    price: {
                        required: true,
                        digits: true,
                        min: 1000
                    }
                },
                messages: {
                    productCode: {
                        required: "Please enter product code"
                    },
                    productName: {
                        required: "Please enter product name"
                    },
                    quantity: {
                        required: "Please enter quantity"
                    },
                    price: {
                        required: "Please enter price"
                    }
                }
            });
        </script>
    </body>
</html>
