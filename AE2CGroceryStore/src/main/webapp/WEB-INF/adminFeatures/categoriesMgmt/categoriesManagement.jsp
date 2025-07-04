<%-- 
    Document   : categoriesManagement
    Created on : Jun 29, 2025, 1:35:55 PM
    Author     : Dinh Cong Phuc - CE190770
--%>

<%--
not done
Listing count - line 36
Delete - line 43
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <%@include file="/WEB-INF/include/head.jsp" %>
    <body>
        <%@include file="/WEB-INF/include/header.jsp" %>

        <main>
            <div class="container g-3">
                <h1>Categories Management</h1>
            </div>
            <div class="container">
                <a href="#" class="btn btn-primary">Create</a>
            </div>
            <div id="categories" class="container pb-4">
                <div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 row-cols-lg-4 g-4">
                    <c:forEach var="category" items="${categories}">
                        <div class="col">
                            <div class="card h-100 d-flex flex-column">
                                <img src="${pageContext.request.contextPath}/assets/images/category/${category.categoryID}.png" class="card-img-top" alt="${category.categoryName}">
                                <div class="card-body mt-auto">
                                    <h5 class="card-title">${category.categoryName}</h5>
                                    <p class="card-text">Listings: </p>

                                    <a href="<c:url value="/admin/categories/edit">
                                            <c:param name="id" value="${category.categoryID}"/>
                                            </c:url>"
                                       class="btn btn-primary">Edit</a>

                                    <a href="" class="btn btn-danger">Delete</a>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </main>

        <%@include file="/WEB-INF/include/footer.jsp" %>
    </body>
</html>
