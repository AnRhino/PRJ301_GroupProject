<%-- 
    Document   : list
    Created on : Jul 19, 2025, 9:07:37 PM
    Author     : Nguyen Ho Phuoc An - CE190747
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <%@include file="../../include/head.jsp" %>
    <body>
        <%@include file="../../include/header.jsp" %>
        <main class="container">
            <h1>Manage Order</h1>
            <table class="table table-striped">
                <thead>
                    <tr>
                        <th>Customer</th>
                        <th>Email</th>
                        <th>Phone number</th>
                        <th>Address</th>
                        <th>Order Date</th>
                        <th>Delivery Date</th>
                        <th>Status</th>
                        <th>Detail</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${orders}" var="order">
                        <tr>
                            <td>${order.user.fullName}</td>
                            <td>${order.user.email}</td>
                            <td>${order.phoneNumber}</td>
                            <td>${order.address}</td>
                            <td>${order.orderDateToString}</td>
                            <td>${order.deliveryDateToString}</td>
                            <td class="
                                <c:choose>
                                    <c:when test="${order.isCompleted()}">
                                        text-success
                                    </c:when>
                                    <c:when test="${order.isCancelled()}">
                                        text-danger
                                    </c:when>
                                </c:choose>
                                ">
                                ${order.status.description}
                            </td>
                            <td>
                                <a class="btn btn-primary" href="<c:url value="/admin/order/detail">
                                       <c:param name="orderId" value="${order.id}"/>
                                   </c:url>">Here</a>
                            </td>
                        </tr>
                    </c:forEach>

                </tbody>
            </table>
        </main>
        <%@include file="../../include/footer.jsp" %>
    </body>
</html>
