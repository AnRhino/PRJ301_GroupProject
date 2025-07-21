<%-- 
    Document   : search
    Created on : Jul 9, 2025, 6:45:53 PM
    Author     : Vu Minh Khang - CE191371
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <%@include file="../include/head.jsp" %>
    <body>
        <%@include file="../include/header.jsp" %>

        <c:choose>

            <c:when test="${empty requestScope.productSearchList}">
                <div class="container-fluid">
                    <div class="m-5">
                        <h1 class="fw-bold">Search results for keyword '<c:out value="${requestScope.searchValue}"/>'</h1>
                        <div class="row">
                            <p>There are no product to display. Try search for another product.</p>
                        </div>
                    </div>
                </div>
            </c:when>

            <c:otherwise>
                <div class="container-fluid">
                    <div class="m-5">
                        <h1 class="fw-bold">Search results for keyword '<c:out value="${requestScope.searchValue}"/>'</h1>
                        <div class="row">
                            <c:forEach var="prod" items="${requestScope.productSearchList}">
                                <div class="col-3 d-flex justify-content-center my-3">
                                    <form action="${pageContext.request.contextPath}/user-product" method="get">
                                        <input type="hidden" name="view" value="product">
                                        <input type="hidden" name="productID" value="${prod.productID}">
                                        <button class="btn btn-secondary border border-secondary">
                                            <div class="row gap-1">
                                                <div class="col-12 d-flex justify-content-center">
                                                    <c:choose>
                                                        <c:when test="${empty prod.coverImg}">
                                                            <img src="assets/images/placeHolder.jpg" alt="placeholder" id="img">
                                                        </c:when>
                                                        <c:otherwise>
                                                            <img src="<c:url value="/get-image/${prod.coverImg}"/>" alt="${prod.productName}" id="img">
                                                        </c:otherwise>
                                                    </c:choose>      
                                                </div>
                                                <div class="col-12 d-flex justify-content-center">
                                                    <p class="fw-bold">Name:</p>&nbsp;
                                                    ${prod.productName}
                                                </div>
                                                <div class="col-12 d-flex justify-content-center">
                                                    <p class="fw-bold">Price:</p>&nbsp;
                                                    <fmt:formatNumber value="${prod.price}" type="number" groupingUsed="true" /> VND
                                                </div>
                                                <div class="col-12 d-flex justify-content-center">
                                                    <p class="fw-bold">Stock:</p>&nbsp;
                                                    ${prod.quantity}
                                                </div>
                                            </div>
                                        </button>
                                    </form>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                </div>
            </c:otherwise>
        </c:choose>

        <%@include file="/WEB-INF/include/search_pagination.jsp" %>
        <%@include file="/WEB-INF/include/footer.jsp" %>
    </body>
</html>

