<%-- 
    Document   : login
    Created on : Jun 20, 2025, 1:41:29 PM
    Author     : Dinh Cong Phuc - CE190770
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <%@include file="../include/head.jsp" %>
    <body>
        <%@include file="../include/header.jsp" %>

        <div class="container-sm">
            <main class="form-signin">
                <form method="post" action="<c:url value="/register"/>"></form>
                      <h1 class="h3 mb-3 fw-normal">Sign Up</h1>

                    <div class="form-floating">
                        <input type="email" class="form-control" name="email" id="floatingInput" placeholder="Email">
                        <label for="floatingInput">Email</label>
                    </div>
                    <div class="form-floating">
                        <input type="text" class="form-control" name="fullName" id="floatingPassword" placeholder="Full Name">
                        <label for="floatingPassword">Full Name</label>
                    </div>
                    <div class="form-floating">
                        <input type="text" class="form-control" name="username" id="floatingInput" placeholder="Username">
                        <label for="floatingInput">Username</label>
                    </div>
                    <div class="form-floating">
                        <input type="password" class="form-control" name="password" id="floatingPassword" placeholder="Password">
                        <label for="floatingPassword">Password</label>
                    </div>
                      

                    <button class="w-100 btn btn-lg btn-primary" type="submit">Sign Up</button>
                </form>
            </main>
        </div>

        <%@include file="../include/footer.jsp" %>
    </body>
</html>
