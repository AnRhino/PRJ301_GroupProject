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
                    String pCode = (String) (request.getAttribute("pCode") != null ? request.getAttribute("pCode") : (pro != null ? pro.getProductCode() : ""));
                    String pName = (String) (request.getAttribute("pName") != null ? request.getAttribute("pName") : (pro != null ? pro.getProductName() : ""));
                    String pQuantity = (String) (request.getAttribute("pQuantity") != null ? request.getAttribute("pQuantity") : (pro != null ? String.valueOf(pro.getQuantity()) : ""));
                    String pPrice = (String) (request.getAttribute("pPrice") != null ? request.getAttribute("pPrice") : (pro != null ? String.valueOf(pro.getPrice()) : ""));
                    String pCate = (String) (request.getAttribute("pCate") != null ? request.getAttribute("pCate") : (pro != null ? String.valueOf(pro.getCategory().getCategoryID()) : ""));
                %>

                <form class="form" method="post" action="<%= request.getContextPath()%>/product">
                    <input type="hidden" name="action" value="edit" />
                    <input type="hidden" name="id" value="<%= pro != null ? pro.getProductID() : ""%>" />


                    <p>
                        <label for="productCore">Product Code</label>
                        <input class="form-control" type="text" id="productCore" name="productCore" value="<%= pCode%>" >
                    </p>

                    <p>
                        <label for="productName">Product Name</label>
                        <input class="form-control" type="text" id="productName" name="productName" value="<%= pName%>" >
                    </p>

                    <p>
                        <label for="quantity">Quantity</label>
                        <input class="form-control" type="text" id="quantity" name="quantity" value="<%= pQuantity%>" >
                    </p>

                    <p>
                        <label for="price">Price</label>
                        <input class="form-control" type="text" id="price" name="price" value="<%= pPrice%>" >
                    </p>

                    <%
                        List<Category> categories = (List<Category>) request.getAttribute("cate");
                        if (categories != null && !categories.isEmpty()) {
                    %>
                    <p>
                        <label for="categogy">Category</label>
                        <select class="form-select" name="categogy" id="categogy">
                            <% for (Category cate : categories) {%>
                            <option value="<%= cate.getCategoryID()%>" <%= String.valueOf(cate.getCategoryID()).equals(pCate) ? "selected" : ""%>>
                                <%= cate.getCategoryName()%>
                            </option>
                            <% } %>
                        </select>
                    </p>
                    <% } else { %>
                    <p class="text-danger">No categories found!</p>
                    <% }%>

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
