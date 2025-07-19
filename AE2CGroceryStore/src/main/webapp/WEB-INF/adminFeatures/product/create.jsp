<%-- 
    Document   : create
    Created on : Jun 16, 2025, 4:04:35 PM
    Author     : Phan Duc Tho - CE191246
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.Category"%>
<%@page import="java.util.List"%>
<!DOCTYPE html>
<html>
    <%@include file="/WEB-INF/include/head.jsp" %>
    <body>
        <%@include file="/WEB-INF/include/header.jsp" %>
        <main class="flex-shrink-0">
            <div class="container">
                <h1 class="mt-5">Create Product</h1>
                <script>
                    function previewImage(event) {
                        const reader = new FileReader();
                        const imagePreview = document.getElementById('imagePreview');
                        const file = event.target.files[0];

                        if (file) {
                            reader.onload = function (e) {
                                imagePreview.src = e.target.result;
                                imagePreview.style.display = "block";
                            };
                            reader.readAsDataURL(file);
                        }
                    }
                </script>
                <%
                    List<String> errorMessages = (List<String>) request.getAttribute("errorMessage");
                    String message = (String) request.getAttribute("msg");
                %>

                <% if (errorMessages != null && !errorMessages.isEmpty()) { %>
                <div class="alert alert-danger">
                    <ul>
                        <% for (String err : errorMessages) {%>
                        <li><%= err%></li>
                            <% } %>
                    </ul>
                </div>
                <% } else if (message != null && !message.isEmpty()) {%>
                <div class="alert alert-danger"><%= message%></div>
                <a class="btn btn-success" href="product?view=create">Retry</a>
                <a class="btn btn-primary" href="product?view=list">Home</a>
                <% }%>

                <form id="form-create" method="post" action="<%= request.getContextPath()%>/admin/product" enctype="multipart/form-data">
                    <input type="hidden" name="action" value="create" />

                    <p>
                        <label for="productCode">Product Code</label>
                        <input class="form-control" type="text" id="productCode" name="productCode" required />
                    </p>
                    <p>
                        <label for="productName">Product Name</label>
                        <input class="form-control" type="text" id="productName" name="productName" required />
                    </p>
                    <p>
                        <label for="quantity">Quantity</label>
                        <input class="form-control" type="number" id="quantity" name="quantity" required />
                    </p>
                    <p>
                        <label for="price">Price</label>
                        <input class="form-control" type="number" id="price" name="price" required />
                    </p>

                    <%
                        List<Category> categories = (List<Category>) request.getAttribute("cate");
                        if (categories != null && !categories.isEmpty()) {
                    %>
                    <p>
                        <label for="category">Category</label>
                        <select class="form-select" name="category" id="category">
                            <% for (Category cate : categories) {%>
                            <option value="<%= cate.getCategoryID()%>"><%= cate.getCategoryName()%></option>
                            <% } %>
                        </select>
                    </p>
                    <% } else { %>
                    <p class="text-danger">Category list is empty!</p>
                    <% }%>

                    <input type="file"
                           name="coverImg"
                           accept="image/*"
                           class="form-control"
                           id="coverImg"
                           onchange="previewImage(event)"
                           />
                    <img id="imagePreview" src="#" alt="Image Preview" style="display: none; max-width: 200px; margin-top: 10px;" />

                    <p>
                        <button class="btn btn-success" type="submit">Add</button>
                        <button type="reset" class="btn btn-secondary">Clear</button>
                    </p>
                </form>
            </div>
        </main>
        <%@include file="/WEB-INF/include/footer.jsp" %>
        <script>
            $("#form-create").validate({
                rules: {
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
