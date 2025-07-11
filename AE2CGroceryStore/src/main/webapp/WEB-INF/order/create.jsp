<%-- 
    Document   : create
    Created on : Jul 8, 2025, 7:18:01 PM
    Author     : Nguyen Ho Phuoc An - CE190747
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <%@include file="../include/head.jsp" %>
    </head>
    <body>
        <%@include file="../include/header.jsp" %>
        <main>
            <h1>New Order</h1>
            <c:set var="items" value="${sessionScope.wantedCartList}"/>
            <c:choose>
                <c:when test="${empty items}">
                    <div class="d-grid justify-content-center">
                        <h5>Please choose any product to buy!</h5>
                        <a class="btn btn-secondary" href="<c:url value="/cart"/>">Go to Cart</a>
                    </div>
                </c:when>
                <c:otherwise>
                    <table>
                        <thead>
                            <tr>
                                <th>Image</th>
                                <th>Product name</th>
                                <th>Unit price</th>
                                <th>Quantity</th>
                                <th>Amount</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${items}" var="item">
                                <tr>
                                    <td><img src="#" width="80" height="80"/></td>
                                    <td><a href="<c:url value=""/>">${item.product.productName}</a></td>
                                    <td>${item.product.price}</td>
                                    <td>${item.quantity}</td>
                                    <td>${item.product.price * item.quantity}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </c:otherwise>
            </c:choose>
        </main>
        <%@include file="../include/footer.jsp" %>
    </body>
</html>
