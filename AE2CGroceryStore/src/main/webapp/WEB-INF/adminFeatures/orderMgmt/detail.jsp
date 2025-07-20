<%-- 
    Document   : detail
    Created on : Jul 19, 2025, 9:11:19 PM
    Author     : Nguyen Ho Phuoc An - CE190747
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <%@include file="../../include/head.jsp" %>
    <body>
        <%@include file="../../include/header.jsp" %>
        <main class="container">
            <div class="row mt-2">
                <div class="col-10">
                    <h1>Order Detail</h1>
                </div>
                <div class="col-2">
                    <a class="btn btn-secondary float-end" href="<c:url value="/admin/order"/>">Back</a>
                </div>
            </div>
            <div class="row">
                <div class="col-6">
                    <div><strong>Order Date:</strong> ${order.orderDateToString}</div>
                    <div><strong>Delivery Date:</strong> ${order.deliveryDateToString}</div>
                </div>
                <div class="col-6">
                    <c:choose>
                        <c:when test="${order.status.id eq 0}">
                            <div><strong>Status: </strong> <span class="text-danger">${order.status.description}</span></div>
                            </c:when>
                            <c:otherwise>
                            <div>
                                <form id="form-update" class="form" action="#" method="post">
                                    <input type="hidden" id="orderId" name="orderId" value="${order.id}">
                                    <label class="form-label" for="status"><strong>Status: </strong></label>
                                    <select class="form-select-sm" id="status" name="statusId">
                                        <c:forEach items="${statuses}" var="status">
                                            <option value="${status.id}" <c:if test="${status.id eq order.status.id}">selected</c:if>>
                                                ${status.description}
                                            </option>
                                        </c:forEach>
                                    </select>
                                    <div>
                                        <button class="btn btn-sm btn-success" type="submit">Save</button>
                                    </div>
                                </form>
                            </div>  
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
            <table class="table table-striped">
                <thead>
                    <tr>
                        <th>Code</th>
                        <th>Image</th>
                        <th>Product</th>
                        <th>Quantity</th>
                        <th>Price</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${order.orderItems}" var="orderItem">
                        <tr>
                            <td>${orderItem.product.productCode}</td>
                            <td><img src="<c:url value="/get-image/${orderItem.product.coverImg}"/>" height="80" width="80" alt="${orderItem.product.productName}"></td>
                            <td>${orderItem.product.productName}</td>
                            <td>${orderItem.quantity}</td>
                            <td><fmt:formatNumber type="number" groupingUsed="true" value="${orderItem.totalPrice}"/> VND</td>
                        </tr>                            
                    </c:forEach>
                </tbody>
                <tfoot>
                    <tr>
                        <td colspan="4">Delivery Fee</td>
                        <td><fmt:formatNumber type="number" groupingUsed="true" value="${order.deliveryFee}"/> VND</td>
                    </tr>
                    <tr>
                        <td colspan="4">Discount</td>
                        <td><fmt:formatNumber type="number" groupingUsed="true" value="${-order.discountValue}"/> VND</td>
                    </tr>
                    <tr>
                        <th colspan="4">Total payment</th>
                        <th><fmt:formatNumber type="number" groupingUsed="true" value="${order.totalPayment}"/> VND</th>
                    </tr>
                </tfoot>
            </table>
            <div class="bg-light p-2 text-center">
                <h3>Customer information</h3>
                <p><strong>Name:</strong> ${order.user.fullName}</p>
                <p><strong>Email:</strong> ${order.user.email}</p>
                <p><strong>Phone number:</strong> ${order.phoneNumber}</p>
                <p><strong>Address:</strong> ${order.address}</p>
            </div>
        </main>
        <%@include file="../../include/footer.jsp" %>
        <script>
            $("#form-update").validate({
                rules: {
                    orderId: {
                        required: true,
                        number: true,
                        range: [$("#orderId").val(), $("#orderId").val()]
                    },
                    statusId: {
                        required: true,
                        digits: true,
                        range: [1, 4]
                    }
                }
            });
        </script>
    </body>
</html>
