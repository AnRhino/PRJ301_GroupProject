<%-- 
    Document   : create
    Created on : Jul 8, 2025, 7:18:01 PM
    Author     : Nguyen Ho Phuoc An - CE190747
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@page import="java.time.LocalDate"%>
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
                                            <fmt:formatNumber value="${item.product.price}" type="number" groupingUsed="true" /> VND
                                        </td>
                                        <td>${item.quantity}</td>
                                        <td>
                                            <fmt:formatNumber value="${item.totalPrice}" type="number" groupingUsed="true" /> VND
                                        </td>
                                    </tr>
                                    <c:set var="subtotal" value="${subtotal + (item.totalPrice)}" />
                                </c:forEach>
                            </tbody>
                        </table>


                        <div class="text-end fw-bold mb-4">
                            <div>Total Price: <fmt:formatNumber value="${subtotal}" type="number" groupingUsed="true" /> VND</div>
                            <c:set var="deliveryFee" value="${subtotal/10}"/>
                            <div>Delivery Fee: <fmt:formatNumber value="${deliveryFee}" type="number" groupingUsed="true" /> VND</div>
                            <c:if test="${not empty chosenDiscountCode}">
                                <c:choose>
                                    <c:when test="${chosenDiscountCode.type eq 0}">
                                        <c:set var="discountValue" value="${subtotal * chosenDiscountCode.value / 100}"/>
                                    </c:when>
                                    <c:when test="${chosenDiscountCode.type eq 1}">
                                        <c:set var="discountValue" value="${chosenDiscountCode.value > subtotal ? subtotal : chosenDiscountCode.value}"/>
                                    </c:when>
                                    <c:when test="${chosenDiscountCode.type eq 2}">
                                        <c:set var="discountValue" value="${deliveryFee}"/>
                                    </c:when>
                                </c:choose>
                                <div>Discount: - <fmt:formatNumber value="${discountValue}" type="number" groupingUsed="true" /> VND</div>
                            </c:if>
                            <div class="h4">Total Pay: <fmt:formatNumber value="${subtotal + deliveryFee - discountValue}" type="number" groupingUsed="true" /> VND</div>
                        </div>                     
                        <form id="form-create" method="post" action="${pageContext.request.contextPath}/new-order">
                            <div class="mb-3">
                                <label for="discount-code-id" class="form-label">Discount Code:</label>
                                <input type="text" name="discount-code-id" value="${chosenDiscountCode.code}" class="form-control" placeholder="None" disabled />
                                <a class="btn btn-warning" href="<c:url value="discount-code"/>">Choose Discount</a>
                            </div>
                            
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

                            <div class="text-end">
                                <button type="submit" class="btn btn-success">Create Order</button>
                            </div>
                        </form>
                    </c:otherwise>
                </c:choose>
            </div>
        </main>

        <%@include file="../include/footer.jsp" %>
        <script>
            $("#form-create").validate({
                rules: {
                    "delivery-date": {
                        required: true,
                        dateISO: true
                    },
                    "phone-number": {
                        required: true
                    },
                    address: {
                        required: true
                    }
                },
                messages: {
                    "delivery-date": {
                        required: "Please choose delivery date"
                    },
                    "phone-number": {
                        required: "Please enter phone number"
                    },
                    address: {
                        required: "Please enter address"
                    }
                }
            });
        </script>
    </body>
</html>
