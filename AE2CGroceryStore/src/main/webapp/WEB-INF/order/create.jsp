<%-- 
    Document   : create
    Created on : Jul 8, 2025, 7:18:01 PM
    Author     : Nguyen Ho Phuoc An - CE190747
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Create New Order</title>
        <%@include file="../include/head.jsp" %>
    </head>
    <body>
        <%@include file="../include/header.jsp" %>
        <main>

            <div class="container p-5">
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
                        <table class="table p-3">
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
                                <c:set var="subtotal" value="0" />
                                <c:forEach items="${items}" var="item">
                                    <tr>
                                        <td>
                                            <img src="${pageContext.request.contextPath}/get-image/${item.product.coverImg}" style="width: 80px;" />
                                        </td>
                                        <td>${item.product.productName}</td>
                                        <td>
                                            <fmt:formatNumber value="${item.product.price}" type="currency" currencySymbol="VND" />
                                        </td>
                                        <td>${item.quantity}</td>
                                        <td>
                                            <fmt:formatNumber value="${item.totalPrice}" type="currency" currencySymbol="VND" />
                                        </td>
                                    </tr>
                                    <c:set var="subtotal" value="${subtotal + (item.totalPrice)}" />
                                </c:forEach>
                            </tbody>
                        </table>


                        <div class="text-end fs-5 fw-bold mb-4">
                            Total Price: <fmt:formatNumber value="${subtotal}" type="currency" currencySymbol="VND" />
                        </div>                     
                        <form method="post" action="${pageContext.request.contextPath}/new-order">
                            <div class="mb-3">
                                <label for="delivery-date" class="form-label">Delivery Date:</label>
                                <input type="date" name="delivery-date" class="form-control" required />
                            </div>

                            <div class="mb-3">
                                <label for="phone-number" class="form-label">Phone Number:</label>
                                <input type="text" name="phone-number" class="form-control" required />
                            </div>

                            <div class="mb-3">
                                <label for="address" class="form-label">Address:</label>
                                <input type="text" name="address" class="form-control" required />
                            </div>

                            <div class="mb-3">
                                <label for="discount-code-id" class="form-label">Discount Code:</label>
                                <input type="text" name="discount-code-id" class="form-control" required />
                            </div>

                            <div class="text-end">
                                <button type="submit" class="btn btn-success">Create Order</button>
                            </div>
                        </form>
                    </c:otherwise>
                </c:choose>
            </div>
        </main>

        <%@include file="../include/footer.jsp" %>
    </body>
</html>
