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
                <c:if test="${sessionScope.loggedUser.id == 1}">
                    <a class="btn btn-success float-end mb-2" href="artist?view=create">Create</a>
                </c:if>
                <table class="table table-striped">
                    <thead>
                    <th>Code</th>
                    <th>Description</th>
                    <th>Quantity</th>
                    <th>Expiry Date</th>
                    </thead>
                    <c:if test="${empty discountCodes}">
                        <div class="row">There is no Discount Code.</div>
                    </c:if>
                    <c:forEach items="${discountCodes}" var="discountCode">

                        <tr>
                            <td>${discountCode.code}</td>
                            <td>
                                Discount ${discountCode.value}
                                <c:choose>
                                    <c:when test="${discountCode.type == 0}">
                                        %
                                    </c:when>
                                    <c:otherwise>
                                        VND
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>${discountCode.quantity}</td>
                            <td>
                                <a class="btn btn-primary" href="artist?view=edit&id=">Edit</a>
                                <a class="btn btn-danger" href="artist?view=delete&id=">Delete</a>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </main>
        <%@include file="../include/footer.jsp" %>
    </body>
</html>
