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

                <form id="form-hide" method="post" action="product">
                    <input type="hidden" id="id" name="ProductID" value="<%= request.getParameter("id")%>" />

                    <p>
                        <button class="btn btn-danger" type="submit" name="action" value="hide">Hide</button>
                        <a href="product?view=list" class="btn btn-secondary">Cancel</a>
                    </p>
                </form>
            </div>
        </main>

        <%@include file="/WEB-INF/include/footer.jsp" %>
        <script>
            $("#form-hide").validate({
                rules: {
                    ProductID: {
                        required: true,
                        number: true,
                        range: [$("#id").val(), $("#id").val()]
                    }
                }
            });
        </script>
    </body>
</html>
