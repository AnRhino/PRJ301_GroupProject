<%-- 
    Document   : list
    Created on : Jun 16, 2025, 4:04:20 PM
    Author     : PC
--%>

<%@page import="java.util.List"%>
<%@page import="model.Product"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/include/header.jsp" %>
<main class="flex-shrink-0">
    <div class="container">
        <h1 class="mt-5">Product</h1>
        <a class=" btn btn-success float-end " href="product?view=create">create</a>
        <!-- Button trigger modal -->
        <% List<Product> product = (List) request.getAttribute("list"); %>
        <% if(product.isEmpty()) { %>
        <div>Dell co dau con </div>
        <% } else { %>
        <table class="table table-striped">
            <thead>
                <tr>
                    <th>ProductID</th>
                    <th>ProductCore</th>
                    <th>ProductName</th>
                    <th>Quantity</th>
                    <th>Price</th>
                    <th>CategoryID</th>
                    <th>Action</th>

                </tr>



            </thead>
            <tbody>
              <% 
                  for(Product pr: product){
                  
                  %>
                <tr>
                    <td><%= pr.getProductID() %></td>
                    <td><%= pr.getProductCode() %></td>
                    <td><%= pr.getProductName()%></td>
                    <td><%= pr.getQuantity() %></td>
                    <td><%= pr.getPrice() %></td>
                    <td><%= pr.getCategoryID().getCategoryID() %></td>
                    
                    <td>
                        <a href="#" class="btn btn-primary btn-sm">edit</a>
                        <a href="#" class="btn btn-danger btn-sm">delete</a>

                    </td>


                </tr>
<% } 
}%>
            </tbody>


        </table>
    </div>
</main>







<%@include file="/WEB-INF/include/footer.jsp" %>
