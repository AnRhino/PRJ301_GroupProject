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

    <%
        User loggedUser = (User) session.getAttribute("loggedUser");
        boolean loggedIn = loggedUser != null;
    %>

    <!-- Fixed navbar -->
    <nav class="navbar navbar-expand-md navbar-dark fixed-top bg-dark">
        <div class="container-fluid">
            <a href="${pageContext.request.contextPath}"><img class="navbar-brand" src="assets/images/logo.png" alt="logo store" width="35" height="45"/> </a>
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
                                            <li><a class="dropdown-item" href="<%= request.getContextPath()%>/user-product?view=category&id=1">Beverages</a></li>
                                            <li><a class="dropdown-item" href="<%= request.getContextPath()%>/user-product?view=category&id=2">Snacks</a></li>
                                            <li><a class="dropdown-item" href="<%= request.getContextPath()%>/user-product?view=category&id=3">Fruits</a></li>
                                        </div>
                                        <div class="col-3">
                                            <li><a class="dropdown-item" href="<%= request.getContextPath()%>/user-product?view=category&id=4">Vegetables</a></li>
                                            <li><a class="dropdown-item" href="<%= request.getContextPath()%>/user-product?view=category&id=5">Diary</a></li>
                                            <li><a class="dropdown-item" href="<%= request.getContextPath()%>/user-product?view=category&id=6">Bakery</a></li>
                                        </div>
                                        <div class="col-3">
                                            <li><a class="dropdown-item" href="<%= request.getContextPath()%>/user-product?view=category&id=7">Meats</a></li>
                                            <li><a class="dropdown-item" href="<%= request.getContextPath()%>/user-product?view=category&id=8">Seafood</a></li>
                                            <li><a class="dropdown-item" href="<%= request.getContextPath()%>/user-product?view=category&id=9">Canned Goods</a></li>
                                        </div>
                                        <div class="col-3">
                                            <li><a class="dropdown-item" href="<%= request.getContextPath()%>/user-product?view=category&id=10">Frozen Foods</a></li>
                                        </div>
                                    </div>
                                </div>
                            </ul>
                        </li>

                        <c:if test="${loggedUser.roleId == 1}">
                            <li class="nav-item">
                                <a class="nav-link active" aria-current="page" href="product">Manager</a>
                            </li>
                        </c:if>
                        <c:if test="${loggedUser.roleId == 0}">

                        </c:if>
                    </ul>
                </div>
                <div class="ms-auto">
                    <input class="search border-2" id="Search-bar" type="search" placeholder="Search" aria-label="Search">
                    <button class="btn btn-outline-success m-2"  type="submit">Search</button>
                </div>
                <div class="flex align-content-end ms-auto">
                    <form class="d-flex">   
                        <% if (loggedIn) { %>
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle  text-center text-light fs-4" href="#" id="navbarDropdown" role="button"
                               data-bs-toggle="dropdown" aria-expanded="false" style="position: relative; bottom: 6px;">
                                <i class="bi bi-person-circle"></i>                              
                            </a>
                            <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                                <a href="#" class="text-dark text-decoration-none m-4">Hi, ${loggedUser.username}</a>
                                <a href="#" class="text-dark text-decoration-none m-4">Account details</a>
                                <a href="#" class="text-dark text-decoration-none m-4">Settings</a>
                                <a href="logout" class="text-danger text-decoration-none m-4">Logout</a>
                            </ul>
                        </li>

                        <% } else { %>
                        <a href="login" class="btn btn-outline-success m-2" style="width: 73px; height: 38px; position: relative; top: 20px;">Login</a>
                        <% }%>

                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle  text-center text-light" href="#" id="navbarDropdown" role="button"
                               data-bs-toggle="dropdown" aria-expanded="false" style="position: relative; bottom: 12px;">  
                                <button class="btn btn-outline-light m-2" type="submit" style="height: 36px;"><i class="bi bi-cart2"> Cart</i></button>
                            </a>
                            <ul class="dropdown-menu" aria-labelledby="navbarDropdown">

                            </ul>
                        </li>

                    </form>
                </div>
            </div>
        </div>
    </nav>
</header>
