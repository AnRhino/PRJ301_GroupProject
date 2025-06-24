<%-- 
    Document   : login
    Created on : Jun 20, 2025, 1:41:29 PM
    Author     : Dinh Cong Phuc - CE190770

    To-Do
    Add Eye function: reveal password
    ErrorMessage persist even after page reload
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <%@include file="../include/head.jsp" %>
    <body>
        <%@include file="../include/header.jsp" %>

        <div class="container-sm">
        <%-- Show error msg if they exists --%>
        <c:if test="${true}"><p class="text-danger">Test msg</p></c:if>
        <c:if test="${not empty usernameMsg}"><p class="text-danger">${usernameMsg.getMessage()}</p></c:if>
        <c:if test="${not empty passwordMsg}"><p class="text-danger">${passwordMsg.getMessage()}</p></c:if>
        <c:if test="${not empty loginError}"><p class="text-danger">${loginError}</p></c:if>
            <main class="form-signin">
                <form method="post" action="<c:url value="/login"/>">
                    <h1 class="h3 mb-3 fw-normal">Log In</h1>

                    <div class="form-floating">
                        <input type="text" class="form-control" name="username" id="floatingInput" placeholder="Username" value="${param.username}">
                        <label for="floatingInput" value="${param.username}">Username</label>
                    </div>
                    <div class="form-floating">
                        <input type="password" class="form-control" name="password" id="floatingPassword" placeholder="Password">
                        <label for="floatingPassword"">Password</label>
                    </div>

                    <button class="w-100 btn btn-lg btn-primary" type="submit">Log In</button>
                </form>
                    <a href="register">Register</a>
            </main>
        </div>

        <%@include file="../include/footer.jsp" %>
    </body>
</html>
