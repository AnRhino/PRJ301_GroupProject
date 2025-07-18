<%-- 
    Document   : delete
    Created on : Jun 16, 2025, 4:04:35 PM
    Author     : Phan Duc Tho - CE191246
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
        <%@include file="/WEB-INF/include/head.jsp" %>
    <body>
        <%@include file="/WEB-INF/include/header.jsp" %>

        <main class="flex-shrink-0">
            <div class="container">
                <h1 class="mt-5">Hide Product</h1>
                <p>Are you sure you want to hide this product?</p>

                <form method="post" action="product">
                    <input type="hidden" name="id" value="<%= request.getParameter("id")%>" />

                    <p>
                        <button class="btn btn-danger" type="submit" name="action" value="delete">hide</button>
                        <a href="product?view=list" class="btn btn-secondary">Cancel</a>
                    </p>
                </form>
            </div>
        </main>

        <%@include file="/WEB-INF/include/footer.jsp" %>
    </body>
</html>
