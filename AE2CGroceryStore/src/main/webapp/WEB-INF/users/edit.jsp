<%-- 
    Document   : edit
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
            <h3>Edit Cart</h3>

            <c:if test="${not empty checkQuantity}">
                <div class="alert alert-danger">
                    <ul>
                        <c:forEach var="error" items="${checkQuantity}">
                            <li>${error}</li>
                            </c:forEach>
                    </ul>
                </div>
            </c:if>

            <form method="post" action="${pageContext.request.contextPath}/cart">
                <input type="hidden" name="cartId" value="${cart.cartItemID}"/>
                <input type="hidden" name="action" value="edit"/>

                <table class="table">
                    <tr>
                        <th>Image</th>
                        <td><img src="${pageContext.request.contextPath}/assets/images/lonely.png" style="width: 100px;" /></td>
                    </tr>
                    <tr>
                        <th>Product</th>
                        <td>${cart.product.productName}</td>
                    </tr>
                    <tr>
                        <th>Price</th>
                        <td>${cart.product.price}</td>
                    </tr>
                    <tr>
                        <th>Quantity</th>
                        <td><input type="number" name="quantity" min="1" value="${cart.quantity}" required/></td>
                    </tr>
                </table>

                <button type="submit" class="btn btn-success">Update</button>
                <button type="reset" class="btn btn-secondary">Clear</button>
            </form>
        </div>

        <%@ include file="../include/footer.jsp" %>
    </body>
</html>
