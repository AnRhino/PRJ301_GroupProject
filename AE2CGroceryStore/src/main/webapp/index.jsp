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
                        <div class="col-3 d-flex justify-content-center my-3 border border-primary">
                            <div class="row">
                                <div class="col-12 d-flex justify-content-center">
                                    <img src="assets/images/placeHolder.jpg" alt="placeholder">
                                </div>
                                <div class="col-12 d-flex justify-content-center">
                                    <a class="text-decoration-none text-dark" href="login">
                                        ${cate.categoryName}
                                    </a>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>

            <div class="ms-5 me-5">
                <h1 class="fw-bold">Product</h1>
                <div class="row">
                    <c:forEach var="pro" items="${productList}">
                        <div class="col-3 d-flex justify-content-center my-3 border border-secondary">
                            <div class="row">
                                <div class="col-12 d-flex justify-content-center">
                                    <img src="assets/images/placeHolder.jpg" alt="placeholder">
                                </div>
                                <div class="col-12 d-flex justify-content-center">
                                    <a class="text-decoration-none text-dark" href="login"> ${pro.productName} </a>
                                </div>
                                <div class="col-12 d-flex justify-content-center">
                                    Price: ${pro.price}
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>

        </div>
        <%@include file="/WEB-INF/include/footer.jsp" %>
    </body>
</html>
