<%-- 
    Document   : list
    Created on : Jul 19, 2025, 2:07:41 AM
    Author     : Nguyen Ho Phuoc An - CE190747
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@include file="../include/head.jsp" %>
        <title>JSP Page</title>
    </head>
    <body>
        <%@include file="../include/header.jsp" %>
        <c:forEach items="${orders}" var="order">
            <div class="bg-light mx-auto my-3 p-2 container">
                <div class="row">
                    <div class="col-3">
                        <div><strong>Order Date:</strong> ${order.orderDateToString}</div>
                        <div><strong>Delivery Date:</strong> ${order.deliveryDateToString}</div>
                    </div>
                    <div class="col-6">
                        <div><strong>Phone number:</strong> ${order.phoneNumber}</div>
                        <div><strong>Address:</strong> ${order.address}</div>
                    </div>
                    <div class="col-3">
                        <div><strong>Status:</strong> ${order.status.description}</div>
                        <div>
                            <form id="form-unhide" action="order" method="post">
                                <input type="hidden" id="orderId" name="orderId" value="${order.id}">
                                <c:choose>
                                    <c:when test="${(order.status.id eq 1) or (order.status.id eq 2)}">
                                        <input type="hidden" name="action" value="cancel">
                                        <button class="btn btn-danger" type="submit">Cancel</button>
                                    </c:when>
                                    <c:when test="${(order.status.id eq 0) or (order.status.id eq 4)}">
                                        <input type="hidden" name="action" value="hide">
                                        <a class="btn btn-primary" href="<c:url value="re-order">
                                               <c:param name="orderId" value="${order.id}"/>
                                           </c:url>">Re-order</a>
                                        <button class="btn btn-danger" type="submit">Delete</button>
                                    </c:when>
                                </c:choose>
                            </form>
                        </div>
                    </div>
                </div>
                <table class="table">
                    <thead>
                        <tr>
                            <th>Image</th>
                            <th>Product</th>
                            <th>Quantity</th>
                            <th>Price</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${order.orderItems}" var="orderItem">
                            <tr>
                                <td><img src="<c:url value="/get-image/${orderItem.product.coverImg}"/>" height="80" width="80" alt="${orderItem.product.productName}"></td>
                                <td>${orderItem.product.productName}</td>
                                <td>${orderItem.quantity}</td>
                                <td><fmt:formatNumber type="number" groupingUsed="true" value="${orderItem.totalPrice}"/> VND</td>
                            </tr>                            
                        </c:forEach>
                    </tbody>
                    <tfoot>
                        <tr>
                            <td colspan="3">Delivery Fee</td>
                            <td><fmt:formatNumber type="number" groupingUsed="true" value="${order.deliveryFee}"/> VND</td>
                        </tr>
                        <tr>
                            <td colspan="3">Discount</td>
                            <td><fmt:formatNumber type="number" groupingUsed="true" value="${-order.discountValue}"/> VND</td>
                        </tr>
                        <tr>
                            <th colspan="3">Total payment</th>
                            <th><fmt:formatNumber type="number" groupingUsed="true" value="${order.totalPayment}"/> VND</th>
                        </tr>
                    </tfoot>
                </table>
            </div>
        </c:forEach>
        <%@include file="../include/footer.jsp" %>
        <script>
            $("#form-update").validate({
                rules: {
                    orderId: {
                        required: true,
                        number: true,
                        range: [$("#orderId").val(), $("#orderId").val()]
                    }
                }
            });
        </script>
    </body>
</html>
