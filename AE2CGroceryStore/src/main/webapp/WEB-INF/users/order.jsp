<%-- 
    Document   : order
    Created on : Jun 28, 2025, 10:19:44 PM
    Author     : Phan Duc Tho - CE191246
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@include file="../include/head.jsp" %>
<body>
    <%@include file="../include/header.jsp" %>

    <div class="container p-5">
        <form action="${pageContext.request.contextPath}/new-order" method="post">
            <h3>Order Summary</h3>
            <table class="table">
                <thead>
                    <tr>
                        <th>#</th>
                        <th>Image</th>
                        <th>Product</th>
                        <th>Quantity</th>
                        <th>Total price</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="item" items="${sessionScope.wantedCartList}" varStatus="loop">

                        <tr>
                            <td>${loop.index + 1}</td>
                            <td><img src="${pageContext.request.contextPath}/assets/images/lonely.png" style="width: 80px;"></td>
                            <td>${item.product.productName}</td>
                            <td>${item.quantity}</td>
                            <td>
                                <fmt:formatNumber value="${item.quantity * item.product.price}" type="number" groupingUsed="true" /> VND
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>





            <button  type="submit" class="btn btn-success">Create new order</button>
        </form>

    </div>

    <%@include file="../include/footer.jsp" %>
</body>
</html>
