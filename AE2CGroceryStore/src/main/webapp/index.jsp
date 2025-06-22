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
            </div>
            <div class="carousel-inner">
                <div class="carousel-item active">
                    <img src="assets/images/placeHolder.jpg" class="d-block w-100" alt="1" id="img">
                </div>
                <div class="carousel-item">
                    <img src="assets/images/gojo.png" class="d-block w-100" alt="2" id="img">
                </div>
                <div class="carousel-item">
                    <img src="assets/images/whoknow.png" class="d-block w-100" alt="3" id="img">
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


        <%
            // Lấy danh sách danh mục và sản phầm từ db.
            CategoryDAO categoryDao = new CategoryDAO();
            ProductDAO productDao = new ProductDAO();

            request.setAttribute("categoryList", categoryDao.getAll());
            request.setAttribute("productList", productDao.getAll());

//            System.out.println(categoryList.size());       
            // Kiểm tra null và coi có rỗng không.
            // Nếu có thì hiện thông báo.

        %>
        <c:if test="${empty productList || empty categoryList}">
            <div class="container-fluid">
                <div>
                    <h1 class="fw-bold">Category</h1>
                    <div class="row">
                        <div>There are no category and product to display.</div>
                    </div>
                </div>
            </div>
        </c:if>

        <div class="container-fluid">
            <div class="ms-5 me-5">
                <h1 class="fw-bold">Category</h1>
                <div class="row">
                    <c:forEach var="cate" items="${categoryList}">

                        <div class="col-3 d-flex justify-content-center border border-primary">
                            <form action="<%= request.getContextPath()%>/userProduct" method="get">
                                <input type="hidden" name="view" value="category">
                                <input type="hidden" name="id" value="${cate.categoryID}">
                                <button class="btn p-0 border-0 bg-transparent">
                                    <div class="row">
                                        <div class="col-12 d-flex justify-content-center">
                                            <img src="assets/images/categories/${cate.categoryID}.png" alt="placeholder" width="225" height="225">
                                        </div>
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

            <div class="ms-5 me-5">
                <h1 class="fw-bold">Product</h1>
                <div class="row">
                    <c:forEach var="pro" items="${productList}">
                        <div class="col-2 d-flex justify-content-center border border-secondary">
                            <form action="<%= request.getContextPath()%>/userProduct" method="get">
                                <input type="hidden" name="view" value="product">
                                <input type="hidden" name="id" value="${pro.productID}">
                                <button class="btn p-0 border-0 bg-transparent">
                                    <div class="row">
                                        <div class="col-12 d-flex justify-content-center">
                                            <img src="assets/images/placeHolder.jpg" alt="placeholder">
                                        </div>
                                        <div class="col-12 d-flex justify-content-center">
                                            ${pro.productName}
                                        </div>
                                        <div class="col-12 d-flex justify-content-center">
                                            Price: 
                                            ${pro.price}
                                        </div>
                                    </div>
                                </button>
                            </form>
                        </div>
                    </c:forEach>
                </div>
            </div>

        </div>
        <%@include file="/WEB-INF/include/footer.jsp" %>
    </body>
</html>
