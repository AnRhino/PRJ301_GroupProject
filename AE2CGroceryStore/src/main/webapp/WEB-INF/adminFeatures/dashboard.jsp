<%-- 
    Document   : dashboard
    Created on : Jun 29, 2025, 2:42:18 PM
    Author     : Dinh Cong Phuc - CE190770
--%>

<%--
Not done
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <%@include file="../include/head.jsp" %>
    <body>
        <%@include file="../include/header.jsp" %>

        <main>
            <div class="container g-3">
                <h1>Admin Management Dashboard</h1>
            </div>
            <div class="container d-flex justify-content-evenly flex-wrap gap-3">
                <a href="<c:url value="/admin/categories"/>" class="btn btn-primary">Categories</a>
                <a href="<c:url value="/admin/product"/>" class="btn btn-primary">Products</a>
                <a href="<c:url value="/admin/discount-code"/>" class="btn btn-primary">Discount Codes</a>
                <a href="<c:url value="/admin/order"/>" class="btn btn-primary">Order</a>
                <a href="<c:url value="/admin/statistic"/>" class="btn btn-primary">Statistics</a>
        </main>

        <%@include file="../include/footer.jsp" %>
    </body>
</html>
