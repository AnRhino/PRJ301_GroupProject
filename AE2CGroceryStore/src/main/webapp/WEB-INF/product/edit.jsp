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
        <form class="form" method="post" action="<%= request.getContextPath()%>/product">
            <input type="hidden" name="action" value="create" />

            <p>
                <label for="name">Product core</label>
                <input  class="form-control" type="text" id="productCore" name="productCore" required>

            </p>
            <p>
                <label for="name">Product Name</label>
                <input  class="form-control" type="text" id="productName" name="productName" required>

            </p>
          
            <p>
                <label for="name">Quantity</label>
                <input  class="form-control" type="text" id="quantity" name="quantity" required>

            </p>
            <p>
                <label for="name">Price</label>
                <input  class="form-control" type="text" id="price" name="price" required>

            </p>
            <% List<Category> categogy = (List) request.getAttribute("cate"); %>
            <p>
                <label for="artist">Category</label>
                <select class="form-select" name="categogy" id="categogy">
                    <% for (Category cate: categogy) { %>
                    
                        
                   <option value="<%=cate.getCategoryName() %> "><%=cate.getCategoryName() %></option>
               <% } %>
                </select>

            </p>
            <p>
                <button class="btn btn-success" type="submit"  name="action" value="create">Add</button>
                <button type="clear" class="btn btn-secondary">Clear</button>

            </p>



        </form>


        </main>
        <%@include file="/WEB-INF/include/footer.jsp" %>
    </body>
</html>