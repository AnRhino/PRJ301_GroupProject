<%-- 
    Document   : index
    Created on : Jun 20, 2025, 1:30:22 AM
    Author     : Le Thien Tri - CE191249
--%>

<%@page import="model.Product"%>
<%@page import="DAO.ProductDAO"%>
<%@page import="DAO.CategoryDAO"%>
<%@page import="model.Category"%>
<%@page import="java.util.List"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <%@include file="WEB-INF/include/head.jsp" %>
    <body>
        <%@include file="/WEB-INF/include/header.jsp" %>

        <div id="carouselExampleIndicators" class="carousel slide">
            <div class="carousel-indicators">
                <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="0" class="active" aria-current="true" aria-label="Slide 1"></button>
                <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="1" aria-label="Slide 2"></button>
                <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="2" aria-label="Slide 3"></button>
                <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="3" aria-label="Slide 4"></button>
                <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="4" aria-label="Slide 5"></button>
            </div>
            <div class="carousel-inner">
                <div class="carousel-item active">
                    <img src="assets/images/members/CreditAnNHP.png" class="d-block w-100" alt="1" id="carousel-img">
                </div>
                <div class="carousel-item">
                    <img src="assets/images/members/CreditKhangVM.png" class="d-block w-100" alt="2" id="carousel-img">
                </div>
                <div class="carousel-item">
                    <img src="assets/images/members/CreditPhucDC.png" class="d-block w-100" alt="3" id="carousel-img">
                </div>
                <div class="carousel-item">
                    <img src="assets/images/members/CreditThoPD.png" class="d-block w-100" alt="4" id="carousel-img">
                </div>
                <div class="carousel-item">
                    <img src="assets/images/members/CreditTriLT.png" class="d-block w-100" alt="5" id="carousel-img">
                </div>
            </div>
            <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide="prev">
                <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                <span class="visually-hidden">Previous</span>
            </button>
            <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide="next">
                <span class="carousel-control-next-icon" aria-hidden="true"></span>
                <span class="visually-hidden">Next</span>
            </button>
        </div>

        <c:choose>

            <c:when test="${empty requestScope.categoryList}">
                <div class="container-fluid">
                    <div>
                        <h1 class="fw-bold">Category</h1>
                        <div class="row">
                            <p>There are no category and product to display.</p>
                        </div>
                    </div>
                </div>
            </c:when>

            <c:otherwise>
                <div class="mb-4" style="background-color: #555555;">
                    <h1 class="fw-bold ms-5 text-light">Category</h1>
                </div>
                <div class="container-fluid">
                    <div class="ms-5 me-5">
                        <div class="d-flex overflow-auto"> <% // cái này overflow dùng để scrollable %>
                            <c:forEach var="cate" items="${categoryList}">
                                <div class="flex-shrink-0 me-3" style="width: 180px;"> <% // ngăn item tự thu nhỏ khi ko đủ chổ %>
                                    <form action="${pageContext.request.contextPath}/user-category" method="get">
                                        <input type="hidden" name="view" value="category">
                                        <input type="hidden" name="categoryID" value="${cate.categoryID}">
                                        <button class="btn p-0 border-0 bg-transparent w-100">
                                            <div class="d-flex flex-column align-items-center">
                                                <img src="assets/images/category/${cate.categoryID}.png"
                                                     alt="placeholder"
                                                     class="img-fluid"
                                                     style="width: 150px; height: 100px; object-fit: cover;"> <% // ảnh tự thu nhỏ vừa khung.%>
                                                <div class="fw-bold text-uppercase text-center mt-2" style="font-size: 0.9rem;">
                                                    ${cate.categoryName}
                                                </div>
                                            </div>
                                        </button>
                                    </form>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                </div>

                <c:choose>

                    <c:when test="${empty requestScope.productList}">
                        <div class="my-4" style="background-color: #555555;">
                            <h1 class="fw-bold text-light ms-5">Product</h1>
                        </div>
                        <div class="ms-5 me-5">
                            <p>There are no product to display.</p>
                        </div>
                    </c:when>

                    <c:otherwise>
                        <div class="my-4" style="background-color: #555555;">
                            <h1 class="fw-bold text-light ms-5">Product</h1>
                        </div>
                        <div class="ms-5 me-5">
                            <div class="row">
                                <c:forEach var="pro" items="${productList}">
                                    <div class="col-3 d-flex justify-content-center">
                                        <form action="${pageContext.request.contextPath}/user-product" method="get">
                                            <input type="hidden" name="view" value="product">
                                            <input type="hidden" name="productID" value="${pro.productID}">
                                            <button class="btn">
                                                <div class="row d-flex justify-content-center">
                                                    <div class="col-12 d-flex justify-content-center">
                                                        <img src="assets/images/placeHolder.jpg" id="img" alt="placeholder">
                                                    </div>  
                                                    <div id="productInfo" class="p-2">
                                                        <div class="col-12 d-flex justify-content-center">
                                                            <p class="fw-bold">Product code:</p>&nbsp;
                                                            ${pro.productCode}
                                                        </div>
                                                        <div class="col-12 d-flex justify-content-center">
                                                            <p class="fw-bold">Name:</p>&nbsp;
                                                            ${pro.productName}
                                                        </div>
                                                        <div class="col-12 d-flex justify-content-center">
                                                            <p class="fw-bold">Price:</p>&nbsp;
                                                            <fmt:formatNumber value="${pro.price}" type="number" groupingUsed="true" /> VND
                                                        </div>
                                                        <div class="col-12 d-flex justify-content-center">
                                                            <p class="fw-bold">Stock:</p>&nbsp;
                                                            ${pro.quantity}
                                                        </div>
                                                    </div>
                                                </div>
                                            </button>
                                        </form>
                                    </div>
                                </c:forEach>
                            </div>
                        </div>
                    </c:otherwise>

                </c:choose>
            </c:otherwise>
        </c:choose>
        <%@include file="WEB-INF/include/pagination.jsp" %>
        <%@include file="/WEB-INF/include/footer.jsp" %>
    </body>
</html>
