<%-- 
    Document   : header
    Created on : Jun 16, 2025, 1:13:17 PM
    Author     : Le Thien Tri - CE191249
--%>
<%@page import="model.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<header>

    <!-- Fixed navbar -->
    <nav class="navbar navbar-expand-md navbar-dark bg-dark fixed-top">
        <div class="container-fluid">
            <a href="${pageContext.request.contextPath}"><img class="navbar-brand" src="/ae2c/assets/images/logo.png" alt="logo store" width="35" height="45"/> </a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarCollapse" aria-controls="navbarCollapse" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarCollapse">
                <div>
                    <ul class="navbar-nav me-auto mb-2 mb-md-0">
                        <li class="nav-item">
                            <a class="nav-link active" aria-current="page" href="${pageContext.request.contextPath}">Home</a>
                        </li>
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button"
                               data-bs-toggle="dropdown" aria-expanded="false">
                                Category
                            </a>
                            <ul class="dropdown-menu" aria-labelledby="navbarDropdown" style="width: 550px;">
                                <div class="container">
                                    <div class="row">
                                        <div class="col-3">
                                            <li><a class="dropdown-item" href="<c:url value="/user-category?view=category&categoryID=1"/>">Beverages</a></li>
                                            <li><a class="dropdown-item" href="<c:url value="/user-category?view=category&categoryID=2"/>">Snacks</a></li>
                                            <li><a class="dropdown-item" href="<c:url value="/user-category?view=category&categoryID=3"/>">Fruits</a></li>
                                        </div>
                                        <div class="col-3">
                                            <li><a class="dropdown-item" href="<c:url value="/user-category?view=category&categoryID=4"/>">Vegetables</a></li>
                                            <li><a class="dropdown-item" href="<c:url value="/user-category?view=category&categoryID=5"/>">Diary</a></li>
                                            <li><a class="dropdown-item" href="<c:url value="/user-category?view=category&categoryID=6"/>">Bakery</a></li>
                                        </div>
                                        <div class="col-3">
                                            <li><a class="dropdown-item" href="<c:url value="/user-category?view=category&categoryID=7"/>">Meats</a></li>
                                            <li><a class="dropdown-item" href="<c:url value="/user-category?view=category&categoryID=8"/>">Seafood</a></li>
                                            <li><a class="dropdown-item" href="<c:url value="/user-category?view=category&categoryID=9"/>">Canned Goods</a></li>
                                        </div>
                                        <div class="col-3">
                                            <li><a class="dropdown-item" href="<c:url value="/user-category?view=category&categoryID=10"/>">Frozen Foods</a></li>
                                        </div>
                                    </div>
                                </div>
                            </ul>
                        </li>


                        <c:if test="${not empty sessionScope.loggedUser}">
                            <li class="nav-item">
                                <a class="nav-link active" aria-current="page" href="<c:url value="/order"/>">Order</a>
                            </li>
                        </c:if>
                        <c:if test="${loggedUser.roleId == 1}">
                            <li class="nav-item">
                                <a class="nav-link active" aria-current="page" href="<c:url value="/admin"/>">Manager</a>
                            </li>
                        </c:if>
                    </ul>
                </div>

                <div class="ms-auto position-relative">
                    <form action="<c:url value='/user-search'/>" method="get" class="d-flex position-relative" style="width: 400px;">
                        <input class="form-control border-2" required id="Search-bar" type="search" 
                               placeholder="Search products..." aria-label="Search" name="key"
                               style="height: 45px; font-size: 16px;"
                               onfocus="document.getElementById('history-list').style.display = 'block';"
                               onblur="setTimeout(() => document.getElementById('history-list').style.display = 'none', 300);">

                        <button class="btn btn-outline-success ms-2" type="submit" style="height: 45px;">Search</button>

                        <ul id="history-list" class="list-group position-absolute shadow" style="top: 50px; width: 100%; display:none; z-index: 10;">
                            <c:forEach var="item" items="${sessionScope.keySearchList}">
                                <li class="list-group-item">
                                    <a class="text-decoration-none text-dark" href="<c:url value='/user-search?key=${item}'/>">${item}</a>
                                </li>
                            </c:forEach>
                        </ul>
                    </form>
                </div>

                <div class="flex align-content-end ms-auto">
                    <form class="d-flex">   
                        <c:choose>
                            <c:when test="${not empty sessionScope.loggedUser}">
                                <li class="nav-item dropdown">
                                    <a class="nav-link dropdown-toggle  text-center text-light fs-4" href="#" id="navbarDropdown" role="button"
                                       data-bs-toggle="dropdown" aria-expanded="false" style="position: relative; bottom: 10px;">
                                        <i class="bi bi-person-circle"></i>                              
                                    </a>
                                    <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                                        <a href="#" class="text-dark text-decoration-none m-4">Hi, ${loggedUser.username}</a>
                                        <a href="<c:url value="/user-profile"/>" class="text-dark text-decoration-none m-4">Account details</a>
                                        <a href="<c:url value="/logout"/>" class="text-danger text-decoration-none m-4">Logout</a>
                                    </ul>
                                </li>
                                <li class="nav-item dropdown">
                                    <a href="<c:url value="/cart"/>" class="btn btn-outline-light" style="position: relative; top: 20px;"><i class="bi bi-cart2"> Cart</i></a>
                                </li>
                            </c:when>
                            <c:otherwise>
                                <a href="<c:url value="/login"/>" class="btn btn-outline-success m-2" style="width: 73px; height: 38px; position: relative; top: 0px;">Login</a>
                                <li class="nav-item dropdown">
                                    <a href="<c:url value="/cart"/>" class="btn btn-outline-light" style="position: relative; top: 8px;"><i class="bi bi-cart2"> Cart</i></a>
                                </li>
                            </c:otherwise>
                        </c:choose>
                    </form>
                </div>
            </div>
        </div>
    </nav>
</header>
