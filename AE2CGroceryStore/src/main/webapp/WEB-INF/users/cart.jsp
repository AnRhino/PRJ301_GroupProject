<%-- 
    Document   : cart
    Created on : Jun 28, 2025, 10:19:44 PM
    Author     : Vu Minh Khang - CE191371
--%>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
    <%@ include file="../include/head.jsp" %>
    <body>
        <%@ include file="../include/header.jsp" %>

        <div class="container-fluid p-5 gap-3">
            <c:choose>

                <c:when test="${empty listCanBuy and empty listOutOfStock and empty ListProductIsHidden}">
                    <div class="text-center mt-5">
                        <h4>Your cart is currently empty.</h4>
                        <a href="${pageContext.request.contextPath}/home" class="btn btn-primary mt-3">Shop</a>
                    </div>
                </c:when>


                <c:otherwise>
                    <form method="post" action="${pageContext.request.contextPath}/cart">
                        <input type="hidden" name="action" value="order"/>
                        <c:set var="selectedItems" value="${checkBox}" />
                        <%
                            int index = 1;
                        %>

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
                                <c:forEach var="item" items="${listCanBuy}">
                                    <tr>
                                        <th scope="row"><%= index++%></th>
                                        <td><img src="${pageContext.request.contextPath}/get-image/${item.product.coverImg}" style="width: 80px;" /></td>
                                        <td><a href="<c:url value="user-product">
                                                   <c:param name="view" value="product"/>
                                                   <c:param name="productID" value="${item.product.productID}"/>
                                        </c:url>">${item.product.productName}</a></td>
                                        <td>${item.quantity}</td>
                                        <td>${item.product.price * item.quantity}</td>
                                        <td>
                                            <a href="${pageContext.request.contextPath}/cart?view=edit&id=${item.cartItemID}" class="btn btn-primary btn-sm">Edit</a>
                                            <a href="${pageContext.request.contextPath}/cart?view=delete&id=${item.cartItemID}" class="btn btn-danger btn-sm">Delete</a>
                                        </td>
                                        <td>
                                            <input type="checkbox" name="isBuy${item.cartItemID}" 
                                                   <c:if test="${selectedItems != null && selectedItems.contains(item.cartItemID)}">checked</c:if> />
                                            </td>
                                        </tr>
                                </c:forEach>

                                <c:forEach var="item" items="${listOutOfStock}">
                                    <tr>
                                        <th scope="row"><%= index++%></th>
                                        <td><img src="${pageContext.request.contextPath}/assets/images/lonely.png" style="width: 80px;" /></td>
                                        <td>${item.product.productName}</td>
                                        <td>${item.quantity}</td>
                                        <td>${item.product.price * item.quantity}</td>
                                        <td>
                                            <a href="${pageContext.request.contextPath}/cart?view=edit&id=${item.cartItemID}" class="btn btn-primary btn-sm">Edit</a>
                                            <a href="${pageContext.request.contextPath}/cart?view=delete&id=${item.cartItemID}" class="btn btn-danger btn-sm">Delete</a>
                                        </td>
                                        <td>Out of stock</td>
                                    </tr>
                                </c:forEach>

                                <c:forEach var="item" items="${ListProductIsHidden}">
                                    <tr>
                                        <th scope="row"><%= index++%></th>
                                        <td><img src="${pageContext.request.contextPath}/assets/images/lonely.png" style="width: 80px;" /></td>
                                        <td>${item.product.productName}</td>
                                        <td>${item.quantity}</td>
                                        <td>${item.product.price * item.quantity}</td>
                                        <td>
                                            <a href="${pageContext.request.contextPath}/cart?view=edit&id=${item.cartItemID}" class="btn btn-primary btn-sm">Edit</a>
                                            <a href="${pageContext.request.contextPath}/cart?view=delete&id=${item.cartItemID}" class="btn btn-danger btn-sm">Delete</a>
                                        </td>
                                        <td>Is Hidden</td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>

                        <button type="submit" class="btn btn-success float-end">Order</button>
                    </form>
                </c:otherwise>
            </c:choose>
        </div>

        <jsp:include page="../include/footer.jsp" />
    </body>
</html>
