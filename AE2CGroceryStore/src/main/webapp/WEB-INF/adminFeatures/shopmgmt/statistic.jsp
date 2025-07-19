<%-- 
    Document   : statisticMonth
    Created on : Jul 17, 2025, 4:41:00 PM
    Author     : Vu Minh Khang - CE191371
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <%@include file="../../include/head.jsp" %>
    <body>
        <%@include file="../../include/header.jsp" %>

        <div class="container-fluid my-1">

            <h1>Product sales statistics recently.</h1>

            <div class="d-flex gap-2">
                <div class="d-flex justify-content-start align-items-center">
                    <a class="btn btn-primary" href="<c:url value="/admin"/>">Back to dashboard</a>
                </div>

                <div class="d-flex justify-content-end align-items-center">
                    <form action="<c:url value="/admin/statistic"/>" method="post">
                        <button type="submit" class="btn btn-success" ${empty requestScope.orderList ? "disabled" : ""}>Export</button>
                    </form>   
                </div>
            </div>
            <c:choose>

                <c:when test="${empty requestScope.orderList}">
                    <p class="m-5">There are curently no order to show.</p>
                </c:when>

                <c:otherwise>
                    <div class="m-5">
                        <table class="table table-striped table-hover table-bordered border-dark table-light">
                            <thead>
                                <tr>
                                    <th scope="col" class="col-1">OrderID</th>
                                    <th scope="col">OrderDate</th>
                                    <th scope="col">DeliveryDate</th>
                                    <th scope="col">Product Name</th>
                                    <th scope="col">StatusDescription</th>
                                    <th scope="col">Quantity</th>
                                    <th scope="col">UnitPrice</th>
                                    <th scope="col">Total</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="order" items="${requestScope.orderList}">

                                    <tr>
                                        <th class="d-flex justify-content-center">${order.id}</th>
                                        <th>${order.orderDate.toLocalDate()}</th>
                                        <th>${order.deliveryDate.toLocalDate()}</th>

                                        <c:forEach var="orderItem" items="${order.orderItems}">
                                            <th>
                                                <a class="text-decoration-none text-dark" href="
                                                   <c:url value="/user-product">
                                                       <c:param name="view" value="product"/>
                                                       <c:param name="productID" value="${orderItem.product.productID}"/>
                                                   </c:url>">
                                                    ${orderItem.product.productName}</a>
                                            </th>
                                            <c:choose>
                                                <c:when test="${order.status.id == 4}">              
                                                    <th class="text-success">${order.status.description}</th>
                                                    </c:when>
                                                    <c:when test="${order.status.id == 3}">              
                                                    <th class="text-primary">${order.status.description}</th>
                                                    </c:when>
                                                    <c:when test="${order.status.id == 2}">              
                                                    <th class="text-secondary">${order.status.description}</th>
                                                    </c:when>
                                                    <c:when test="${order.status.id == 1}">              
                                                    <th class="text-warning">${order.status.description}</th>
                                                    </c:when>
                                                    <c:otherwise>
                                                    <th class="text-danger">${order.status.description}</th>
                                                    </c:otherwise>
                                                </c:choose>
                                            <th>${orderItem.quantity}</th>
                                            <th><fmt:formatNumber value="${orderItem.unitPrice}"/> VND</th>
                                            <th><fmt:formatNumber value="${orderItem.totalPrice}"/> VND</th>
                                            </c:forEach>
                                    </tr>

                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>

        <%@include file="../../include/footer.jsp" %>
    </body>
</html>
