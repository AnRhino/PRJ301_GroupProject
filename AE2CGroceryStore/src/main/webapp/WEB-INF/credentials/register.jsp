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
        <c:if test="${not empty emailMsg}"><p class="text-danger">${emailMsg.getMessage()}</p></c:if>
        <c:if test="${not empty fullNameMsg}"><p class="text-danger">${fullNameMsg.getMessage()}</p></c:if>
        <c:if test="${not empty usernameMsg}"><p class="text-danger">${usernameMsg.getMessage()}</p></c:if>
        <c:if test="${not empty passwordMsg}"><p class="text-danger">${passwordMsg.getMessage()}</p></c:if>
        <c:if test="${not empty registerError}"><p class="text-danger">${registerError}</p></c:if>

            <main class="form-signin">
                <form method="post" action="<c:url value="/register"/>">
                      <h1 class="h3 mb-3 fw-normal">Register</h1>

                    <div class="form-floating">
                        <input type="email" class="form-control" name="email" id="floatingInput" placeholder="Email" value="${param.email}">
                        <label for="floatingInput">Email</label>
                    </div>
                    <div class="form-floating">
                        <input type="text" class="form-control" name="fullName" id="floatingPassword" placeholder="Full Name" value="${param.fullName}">
                        <label for="floatingPassword">Name</label>
                    </div>
                    <div class="form-floating">
                        <input type="text" class="form-control" name="username" id="floatingInput" placeholder="Username" value="${param.username}">
                        <label for="floatingInput">Username</label>
                    </div>
                    <div class="form-floating">
                        <input type="password" class="form-control" name="password" id="floatingPassword" placeholder="Password" value="${param.password}">
                        <label for="floatingPassword">Password</label>
                    </div>
                      

                    <button class="w-100 btn btn-lg btn-primary" type="submit">Register</button>
                </form>
                      <a href="login">Login</a>
            </main>
        </div>

        <%@include file="../include/footer.jsp" %>
    </body>
</html>
