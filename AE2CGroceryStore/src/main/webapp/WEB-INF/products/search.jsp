<%-- 
    Document   : search
    Created on : Jul 9, 2025, 6:45:53 PM
    Author     : Vu Minh Khang - CE191371
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <%@include file="WEB-INF/include/head.jsp" %>
    <body>
        <%@include file="/WEB-INF/include/header.jsp" %>

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
                                                    <div id="productInfo">
                                                        <div class="col-12 d-flex justify-content-center">
                                                            ${pro.productName}
                                                        </div>
                                                        <div class="col-12 d-flex justify-content-center">
                                                            Price: 
                                                            ${pro.price}
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

