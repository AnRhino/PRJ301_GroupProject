<%-- 
    Document   : profile
    Created on : Jun 25, 2025, 3:26:40 PM
    Author     : Le Thien Tri - CE191249
--%>

<%@page import="model.ErrorMessage"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <%@include file="../include/head.jsp" %>
    <body>
        <%@include file="../include/header.jsp" %>
        <div class="d-flex justify-content-center align-content-center mt-5">
            <div class="row-1 bg-light w-25">
                <div>
                    <h1 class="fw-bold ms-2 mt-2">Profile</h1>
                </div>
                <c:if test="${not empty emailMsg}"><p class="text-danger">${emailMsg.getMessage()}</p></c:if>
                <c:if test="${not empty fullNameMsg}"><p class="text-danger">${fullNameMsg.getMessage()}</p></c:if>
                    <p class="ms-4" id="profile">
                        <strong>Username: </strong> ${profileUser.username}
                </p>                  
                <p class="ms-4" id="profile">
                    <c:if test="${editFullName != true}">
                        <strong>Full Name: </strong> ${sessionScope.profileUser.fullName} <a href="${pageContext.request.contextPath}/user-profile?view=editFullName"><i class="bi bi-pencil-square"></i></a>
                    </p>
                    <c:if test="${sessionScope.fullNameError != null}">
                        <div class="ms-4 mb-4 text-danger">${sessionScope.fullNameError.message}</div>
                        <c:remove var="fullNameError" scope="session"/>
                    </c:if>
                </c:if>                              
                <c:if test="${editFullName == true}">
                    <form class="form d-flex align-items-center ms-4 gap-1 mb-3" method="post" action="${pageContext.request.contextPath}/user-profile">                            
                        <label class="text-nowrap" for="fullname">
                            <strong>Full Name: </strong>                                                                                                    
                        </label>
                        <input class="form-control" type="text" id="fullname" name="fullname" value="${profileUser.fullName}">
                        <button class="btn btn-success" type="submit">Save</button>   
                    </form>
                </c:if>       




                <p class="ms-4" id="profile">
                    <strong>Image: </strong> <img src="assets/images/placeHolder.jpg" alt="User image" width="100" height="128">
                </p>
                <c:if test="${editEmail != true}">
                    <p class="ms-4 mb-3" id="profile">
                        <strong>Email: </strong> ${sessionScope.profileUser.email}  <a href="${pageContext.request.contextPath}/user-profile?view=editEmail""><i class="bi bi-pencil-square"></i></a>
                    </p>
                    <c:if test="${sessionScope.emailError != null}">
                        <div class="ms-4 text-danger">${sessionScope.emailError.message}</div>
                        <c:remove var="emailError" scope="session"/>
                    </c:if>
                </c:if>
                <c:if test="${editEmail == true}">
                    <form class="form d-flex align-items-center ms-4 gap-1 mb-3" method="post" action="${pageContext.request.contextPath}/user-profile">
                        <label class="text-nowrap" for="email">
                            <strong>Email: </strong>                                                                                                    
                        </label>
                        <input class="form-control" type="text" id="email" name="email" value="${profileUser.email}">
                        <button class="btn btn-success" type="submit">Save</button>   
                    </form>
                </c:if>

            </div>
        </div>
        <div>

        </div>
        <%@include file="../include/footer.jsp" %>
    </body>
</html>
