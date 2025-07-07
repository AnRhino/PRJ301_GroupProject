<%-- 
    Document   : cart
    Created on : Jun 28, 2025, 10:19:44 PM
    Author     : Vu Minh Khang - CE191371
--%>

<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <%@include file="../include/head.jsp" %>
    <body>
        <%@include file="../include/header.jsp" %>

        <div class="container-fluid p-5 gap-3">
            <form method="post" action="${pageContext.request.contextPath}/cart">
                <input type="hidden" name="action" value="order"/>

                <c:set var="choose" value="${checkBox}" />

                <table class="table">
                    <thead>
                        <tr>
                            <th>#</th>
                            <th>Image</th>

                            <th>Product Name</th>
                            <th>Quantity</th>
                            <th>Price</th>
                            <th>Action</th>
                            <th>Buy</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="cart" items="${cartList}" varStatus="status">
                            <tr>
                                <th scope="row">${status.index + 1}</th>
                                <td> <img src="${pageContext.request.contextPath}/assets/images/lonely.png" alt="alt" style="width: 80px; height: auto;" />
                                </td>

                                <td>${cart.product.productName}</td> 
                                <td>${cart.quantity}</td>
                                <td>${cart.product.price*cart.quantity}</td>
                                <td>
                                    <a href="${pageContext.request.contextPath}/cart?view=edit&id=${cart.cartItemID}" class="btn btn-primary btn-sm">edit</a>
                                    <a href="${pageContext.request.contextPath}/cart?view=delete&id=${cart.cartItemID}" class="btn btn-danger btn-sm">delete</a>


                                </td>
                                <td>
                                    <c:choose>
                                        <c:when test="${choose != null && choose.contains(cart.cartItemID)}">
                                            <input type="checkbox" name="isBuy${cart.cartItemID}" checked />
                                        </c:when>
                                        <c:otherwise>
                                            <input type="checkbox" name="isBuy${cart.cartItemID}" />
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                          
                        </tr>
                    </c:forEach>
                </tbody>

            </table>
            <button type="submit" class="btn btn-success float-end">Order</button>
        </form>
    </div>



</div>

<jsp:include page="../include/footer.jsp" />
</body>
</html>
