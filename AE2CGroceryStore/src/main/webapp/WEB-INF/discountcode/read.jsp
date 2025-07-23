<%-- 
    Document   : view
    Created on : Jun 22, 2025, 7:21:48 PM
    Author     : Nguyen Ho Phuoc An - CE190747
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <%@include file="../include/head.jsp" %>
    <body>
        <%@include file="../include/header.jsp" %>
        <main>
            <div class="container">
                <div class="row">
                    <h1>Discount Code</h1>
                </div>
            </div>
            <div class="container">
                <table class="table table-striped mb-5">
                    <thead>
                    <th>Code</th>
                    <th>Description</th>
                    <th>Quantity</th>
                    <th>Min Order Value</th>
                    <th>Expiry Date</th>
                    <th></th>
                    </thead>
                    <div class="fs-3">Free Shipping Codes</div>
                    <c:if test="${empty listShippingCode}">
                        <div class="row">There is no Free Shipping codes.</div>
                    </c:if>
                    <c:forEach items="${listShippingCode}" var="shippingCode">
                        <tr>
                            <td>${shippingCode.code}</td>
                            <td>Free Shipping 100%</td>
                            <td>${shippingCode.quantity}</td>
                            <td><fmt:formatNumber type="number" value="${shippingCode.minOrderValue}" groupingUsed="true"></fmt:formatNumber> VND</td>
                            <td>${shippingCode.expiryDate}</td>
                            <td><a class="btn btn-success" href="<c:url value="/discount-code/use">
                                       <c:param name="id" value="${shippingCode.id}"/>
                                   </c:url>">Use</a></td>
                        </tr> 
                    </c:forEach>
                </table>

                <table class="table table-striped mb-5">
                    <thead>
                    <th>Code</th>
                    <th>Description</th>
                    <th>Quantity</th>
                    <th>Min Order Value</th>
                    <th>Expiry Date</th>
                    <th></th>
                    </thead>
                    <div class="fs-3">Discount Codes</div>
                    <c:if test="${empty listPriceCode}">
                        <div class="row">There is no Discount codes.</div>
                    </c:if>
                    <c:forEach items="${listPriceCode}" var="priceCode">
                        <tr>
                            <td>${priceCode.code}</td>
                            <td>
                                Discount: 
                                <c:choose>
                                    <c:when test="${priceCode.type == 0}">
                                        ${priceCode.value} %
                                    </c:when>
                                    <c:otherwise>
                                        <fmt:formatNumber type="number" value="${priceCode.value}" groupingUsed="true"></fmt:formatNumber> VND
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>${priceCode.quantity}</td>
                            <td><fmt:formatNumber type="number" value="${priceCode.minOrderValue}" groupingUsed="true"></fmt:formatNumber> VND</td>
                            <td>${priceCode.expiryDate}</td>
                            <td><a class="btn btn-success" href="<c:url value="/discount-code/use">
                                       <c:param name="id" value="${priceCode.id}"/>
                                   </c:url>">Use</a></td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </main>
        <%@include file="../include/footer.jsp" %>
    </body>
</html>
