<%-- 
    Document   : category
    Created on : Jun 20, 2025, 3:59:52 PM
    Author     : Vu Minh Khang - CE191371
--%>

<%@page import="model.Product"%>
<%@page import="java.util.List"%>
<%@page import="model.Category"%>
<%@page import="model.Category"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <%@include file="../include/head.jsp" %>
    <body>
        <%@include file="../include/header.jsp" %>

        <c:choose>

            <c:when test="${empty categoryList}">
                <div class="container-fluid">
                    <div>
                        <h1 class="fw-bold">Error</h1>
                        <div class="row">
                            <div>There are no product to display.</div>
                        </div>
                    </div>
                </div>
            </c:when>

            <c:otherwise>
                <div class="container-fluid">
                    <div class="ms-5 me-5"> 
                        <div class="row">
                            <div class="col-3">
                                <h1 class="fw-bold">Other category</h1>
                                <div class="overflow-auto h-50">
                                    <c:forEach var="cate" items="${requestScope.categoryList}">
                                        <div class="col-12 d-flex justify-content-center my-3 border bg-dark py-2">
                                            <form action="${pageContext.request.contextPath}/user-category" method="get">
                                                <input type="hidden" name="view" value="category">
                                                <input type="hidden" name="categoryID" value="${cate.categoryID}">
                                                <button class="btn p-0 border-0 text-white">
                                                    <div class="row">
                                                        <div class="col-12 d-flex justify-content-center">
                                                            ${cate.categoryName}
                                                        </div>
                                                    </div>
                                                </button>
                                            </form>
                                        </div>
                                    </c:forEach>
                                </div>
                            </div>
                            <div class="col-9">
                                <div class="ms-5 me-5">

                                    <c:choose>

                                        <c:when test="${requestScope.categoryType == null}">
                                            <h1 class="fw-bold">Unavailable category</h1>
                                            <div class="row">
                                                <div class="col-12">There are no available product in this category currently.</div>
                                                <div class="col-12">Please try another.</div>
                                            </div>
                                        </c:when>

                                        <c:otherwise>

                                            <h1 class="fw-bold">${requestScope.categoryType.categoryName}</h1>
                                            <div class="row"> 

                                                <c:choose>

                                                    <c:when test="${empty requestScope.productList}">
                                                        <div class="col-3 d-flex justify-content-center my-3 border border-secondary">
                                                            <p>There are currently no available product in this category.</p>
                                                        </div>
                                                    </c:when>

                                                    <c:otherwise>

                                                        <c:forEach var="prod" items="${requestScope.productList}">
                                                            <div class="col-3 d-flex justify-content-center my-3">
                                                                <form action="${pageContext.request.contextPath}/user-product" method="get">
                                                                    <input type="hidden" name="view" value="product">
                                                                    <input type="hidden" name="productID" value="${prod.productID}">
                                                                    <button class="btn btn-secondary border border-secondary">
                                                                        <div class="row p-2">
                                                                            <div class="col-12 d-flex justify-content-center">
                                                                                <img src="assets/images/placeHolder.jpg" alt="placeholder">
                                                                            </div>
                                                                            <div class="col-12 d-flex justify-content-center">
                                                                                <p class="fw-bold">Product code:</p>&nbsp;
                                                                                ${prod.productCode}
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
                                                    </c:otherwise>
                                                </c:choose>
                                            </div>
                                        </c:otherwise>
                                    </c:choose>
                                </div>                            
                            </div>

                        </div>
                    </div>
                </div>
            </c:otherwise>
        </c:choose>
        <%@include file="../include/category_pagination.jsp" %>
        <jsp:include page="../include/footer.jsp" />
    </body>
</html>
