<%-- 
    Document   : cart
    Created on : Jun 28, 2025, 10:19:44 PM
    Author     : Vu Minh Khang - CE191371
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <%@include file="../include/head.jsp" %>
    <body>
        <%@include file="../include/header.jsp" %>

        <div class="container-fluid p-5 gap-3">

            <table class="table">
                <thead>
                    <tr>
                        <th>#</th>
                        <th>Cart ID</th>
                        <th>User</th>
                        <th>Product Name</th>
                        <th>Quantity</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="cart" items="${cartList}" varStatus="status">
                        <tr>
                            <th scope="row">${status.index + 1}</th>
                            <td>${cart.cartItemID}</td>
                            <td>${cart.user.username}</td>
                            <td>${cart.product.productName}</td> <!-- ✅ Chỉ hiển thị tên sản phẩm -->
                            <td>${cart.quantity}</td>
                        </tr>
                    </c:forEach>
                </tbody>

            </table>
        </div>



</div>

<jsp:include page="../include/footer.jsp" />
</body>
</html>
