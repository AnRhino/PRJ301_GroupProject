<%-- 
    Document   : list
    Created on : Jun 16, 2025, 4:04:20 PM
    Author     : Phan Duc Tho - CE191246
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.Product"%>
<%@page import="java.util.List"%>
<!DOCTYPE html>
<html>
    <%@include file="/WEB-INF/include/head.jsp" %>
    <body>
        <%@include file="/WEB-INF/include/header.jsp" %>

        <main class="flex-shrink-0">
            <div class="container">
                <h1 class="mt-5">Product List</h1>
                <a class="btn btn-success float-end mb-3" href="product?view=create">Create New Product</a>

                <%
                    List<Product> productList = (List<Product>) request.getAttribute("list");
                    if (productList == null || productList.isEmpty()) {
                %>
                <div class="alert alert-info">No products found.</div>
                <% } else { %>
                <table class="table table-striped">
                    <thead>
                        <tr>
                            <th>Product Code</th>
                            <th>Product Image</th>
                            <th>Product Name</th>
                            <th>Quantity</th>
                            <th>Price</th>
                            <th>Category</th>
                       
                            
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% for (Product pr : productList) {%>
                        <tr>
                            <td><%= pr.getProductCode()%></td>
                            <td><img width="100" src="/ae2c/assets/images/<%= pr.getCategory().getCoverImg()%>"/>  </td>                         
                            <td><%= pr.getProductName()%></td>
                            <td><%= pr.getQuantity()%></td>
                            <td><%= pr.getPrice()%></td>
                            <td><%= pr.getCategory().getCategoryName()%></td>
                            
                            
                            <td>
                                
                                <a href="<%= request.getContextPath()%>/admin/product?view=edit&id=<%= pr.getProductID()%>" class="btn btn-primary btn-sm">Edit</a>
                                
                                <% if(pr.isIsHidden()==false){ %>
                                 <a href="<%= request.getContextPath()%>/admin/product?view=delete&id=<%= pr.getProductID()%>" class="btn btn-danger btn-sm">UnHidden</a>
                                 <%   } else {%>
                                <a href="<%= request.getContextPath()%>/admin/product?view=hidden&id=<%= pr.getProductID()%>" class="btn btn-primary btn-sm">Hidden</a>
                                <% } %>
                            </td>
                        </tr>
                        <% } %>
                    </tbody>
                </table>
                <% }%>
            </div>
        </main>

        <%@include file="/WEB-INF/include/footer.jsp" %>
    </body>
</html>
