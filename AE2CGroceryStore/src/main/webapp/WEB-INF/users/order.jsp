<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@include file="../include/head.jsp" %>
<body>
    <%@include file="../include/header.jsp" %>

    <div class="container p-5">
        <h3>Order Summary</h3>
        <table class="table">
            <thead>
                <tr>
                    <th>#</th>
                    <th>Image</th>
                    <th>Product</th>
                    <th>Quantity</th>
                    <th>Thanh Tien</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="item" items="${sessionScope.Order}" varStatus="loop">

                    <tr>
                        <td>${loop.index + 1}</td>
                        <td><img src="${pageContext.request.contextPath}/assets/images/lonely.png" style="width: 80px;"></td>
                        <td>${item.product.productName}</td>
                        <td>${item.quantity}</td>
                        <td>${item.quantity * item.product.price}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <h5>Total Quantity: ${sessionScope.totalQuantity}</h5>
        <h5>Total Price: ${sessionScope.totalPrice}</h5>

        <button class="btn btn-success">Buy</button>
    </div>

    <%@include file="../include/footer.jsp" %>
</body>
</html>
