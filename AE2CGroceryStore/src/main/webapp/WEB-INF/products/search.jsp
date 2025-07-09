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
                        <h1 class="fw-bold">Search results for keyword '${requestScope.searchValue}'</h1>
                        <div class="row">
                            <p>There are no product to display. Try search for another product.</p>
                        </div>
                    </div>
                </div>
            </c:when>

            <c:otherwise>
                <div class="container-fluid">
                    <div class="m-5">
                        <h1 class="fw-bold">Search results for keyword '${requestScope.searchValue}'</h1>
                        <div class="row">
                            <c:forEach var="prod" items="${requestScope.productSearchList}">
                                <div class="col-3 d-flex justify-content-center my-3 border border-secondary">
                                    <form action="${pageContext.request.contextPath}/user-product" method="get">
                                        <input type="hidden" name="view" value="product">
                                        <input type="hidden" name="productID" value="${prod.productID}">
                                        <button class="btn-secondary border-secondary">
                                            <div class="row">
                                                <div class="col-12 d-flex justify-content-center">
                                                    <img src="assets/images/placeHolder.jpg" alt="placeholder">
                                                </div>
                                                <div class="col-12 d-flex justify-content-center">
                                                    ${prod.productName}
                                                </div>
                                                <div class="col-12 d-flex justify-content-center">
                                                    Price: 
                                                    <fmt:formatNumber value="${prod.price}" type="number" groupingUsed="true" /> VND
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

