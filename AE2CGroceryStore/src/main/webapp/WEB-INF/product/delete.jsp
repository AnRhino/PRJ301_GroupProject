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
        <h1 class="mt-5">Delete product</h1>
      
        <form class="form" method="post" action="artist"product">
            <p>
               
                <input  type="hidden" id="id" name="id" readonly
                        value="<%= request.getParameter("id")%>">

            </p>
            <p>
                <label for="name">Artist name</label>
               
                <input  type="text" id="name" name="name" readonly
                        value="">

            </p>
            <p>
                <button class="btn btn-success" type="submit"  name="action" value="delete">delete</button>
                <button type="clear" class="btn btn-secondary">Clear</button>

            </p>



        </form>


        </main>
        <%@include file="/WEB-INF/include/footer.jsp" %>
    </body>
</html>