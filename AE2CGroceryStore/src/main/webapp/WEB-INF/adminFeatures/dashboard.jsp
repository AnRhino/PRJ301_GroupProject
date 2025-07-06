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
                <<h1>Admin Management Dashboard</h1>
            </div>
            <div class="container">
                <a href="<c:url value="/admin/categories"/>" class="btn btn-primary">Categories</a>
            </div>
        </main>

        <%@include file="../include/footer.jsp" %>
    </body>
</html>
