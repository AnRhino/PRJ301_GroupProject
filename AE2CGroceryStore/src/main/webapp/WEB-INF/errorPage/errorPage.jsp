
<%-- 
    Document   : errorPage
    Created on : Jun 16, 2025, 4:04:35 PM
    Author     : Phan Duc Tho - CE191246
--%>




<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<%@include file="../include/head.jsp" %>

<body class="bg-light">
    <%@include file="/WEB-INF/include/header.jsp" %>

    <div class="d-flex justify-content-center align-items-center min-vh-100">
        <div class="card text-center shadow p-4" style="max-width: 600px; width: 100%;">
            <h1 class="text-dark">404 - Not found</h1>
            <p class="text-muted">
             Please check the link or you can top up to continue using the service.
            </p>
            <a href="${pageContext.request.contextPath}/home" class="btn btn-primary btn-sm mt-3">
                Home
            </a>
        </div>
    </div>

    <%@include file="../include/footer.jsp" %>
</body>

</html>
