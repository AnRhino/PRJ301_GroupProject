<%-- 
    Document   : delete
    Created on : Jun 28, 2025, 10:19:44 PM
    Author     : Phan Duc Tho - CE191246
--%>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
    <%@ include file="../include/head.jsp" %>
    <body>
        <%@ include file="../include/header.jsp" %>

        <div class="container p-5">
            <h3>Delete Cart</h3>

            <form id="form-delete" method="post" action="${pageContext.request.contextPath}/cart">
                <input type="hidden" id="cartId" name="cartId" value="${cart.cartItemID}"/>
                <input type="hidden" name="action" value="delete"/>

                <table class="table">
                    <tr><th>Image</th><td><img src="${pageContext.request.contextPath}/assets/images/lonely.png" style="width: 100px;" /></td></tr>
                    <tr><th>Product</th><td>${cart.product.productName}</td></tr>
                    <tr><th>Price</th><td>${cart.product.price}</td></tr>
                    <tr><th>Quantity</th><td>${cart.product.quantity}</td></tr>
                </table>

                <button type="submit" class="btn btn-danger">Delete</button>
                <a href="${pageContext.request.contextPath}/cart" class="btn btn-secondary">Back</a>
            </form>
        </div>

        <%@ include file="../include/footer.jsp" %>
        <script>
            $("#form-delete").validate({
                rules: {
                    cartId: {
                        required: true,
                        range: [$("#cartId").val(), $("#cartId").val()]
                    }
                }
            })
        </script>
    </body>
</html>
