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
            <div class="row-1 bg-light w-25 shadow">
                <div>
                    <h1 class="fw-bold ms-2 mt-2">Profile</h1>
                </div>
                <c:if test="${not empty emailMsg}"><p class="text-danger">${emailMsg.getMessage()}</p></c:if>
                <c:if test="${not empty fullNameMsg}"><p class="text-danger">${fullNameMsg.getMessage()}</p></c:if>
                    <div class="container-fluid">
                        <p class="ms-4" id="profile">
                            <strong>Username: </strong> ${loggedUser.username}
                    </p>                  

                    <!-- Fullname --> 
                    <p class="ms-4" id="profile">
                        <strong>Full Name: </strong> ${sessionScope.loggedUser.fullName} <a href="${pageContext.request.contextPath}/user-profile?view=editFullName"  data-bs-toggle="modal" data-bs-target="#editFullnameModal"><i class="bi bi-pencil-square"></i></a>
                    </p>            
                    <!-- Modal --> 
                    <div class="modal fade" id="editFullnameModal" tabindex="-1" aria-labelledby="editFullnameModalLabel" aria-hidden="true">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <form method="post" action="${pageContext.request.contextPath}/user-profile">
                                    <div class="modal-header">
                                        <h5 class="modal-title" id="editFullnameModalLabel">Change FullName</h5>
                                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                    </div>

                                    <div class="modal-body">
                                        <div class="mb-3">
                                            <label class="text-nowrap" for="fullname">
                                                <strong>Full Name: </strong>                                                                                                    
                                            </label>
                                            <input class="form-control" type="text" id="fullname" name="fullname" value="${loggedUser.fullName}" required>
                                        </div>
                                    </div>

                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                                        <button type="submit" class="btn btn-success">Save</button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                    <c:if test="${sessionScope.fullNameError != null}">
                        <div class="ms-4 mb-4 text-danger">${sessionScope.fullNameError.message}</div>
                        <c:remove var="fullNameError" scope="session"/>
                    </c:if>   

                    <!-- image --> 
                    <p class="ms-4" id="profile">
                        <strong>Image: </strong> <img src="<c:url value="/get-image/${loggedUser.coverImg}"/>" alt="User image" width="100" height="128">
                        <a href="${pageContext.request.contextPath}/user-profile?view=editEmail" data-bs-toggle="modal" data-bs-target="#editProfilePicModal"><i class="bi bi-pencil-square"></i></a>
                    </p>

                    <div class="modal fade" id="editProfilePicModal" tabindex="-1" aria-labelledby="editProfilePicModal" aria-hidden="true">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <!-- Modal email form --> 
                                <form method="post" action="${pageContext.request.contextPath}/user-profile">
                                    <!-- Modal email header --> 
                                    <div class="modal-header">
                                        <h5 class="modal-title" id="editEmailModalLabel">Change profile picture</h5>
                                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                    </div>
                                    <!-- Modal email body --> 
                                    <div class="modal-body">
                                        <div class="mb-3">
                                            <label class="form-label" for="email"><strong>Image:</strong></label>
                                            <img src="<c:url value="/get-image/${loggedUser.coverImg}"/>" alt="User image" width="100" height="128">
                                            <input type="file"
                                                   name="coverImg"
                                                   accept="image/*"
                                                   class="form-control"
                                                   />
                                        </div>
                                    </div>
                                    <!-- Modal button --> 
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                                        <button type="submit" class="btn btn-success">Save</button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>

                    <!-- Email --> 
                    <p class="ms-4 mb-3" id="profile">
                        <strong>Email: </strong> ${sessionScope.loggedUser.email} 
                        <!-- Modal button open --> 
                        <a href="${pageContext.request.contextPath}/user-profile?view=editEmail" data-bs-toggle="modal" data-bs-target="#editEmailModal"><i class="bi bi-pencil-square"></i></a>
                    </p>

                    <!-- Modal email --> 
                    <div class="modal fade" id="editEmailModal" tabindex="-1" aria-labelledby="editEmailModalLabel" aria-hidden="true">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <!-- Modal email form --> 
                                <form method="post" action="${pageContext.request.contextPath}/user-profile">
                                    <!-- Modal email header --> 
                                    <div class="modal-header">
                                        <h5 class="modal-title" id="editEmailModalLabel">Change Email</h5>
                                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                    </div>
                                    <!-- Modal email body --> 
                                    <div class="modal-body">
                                        <div class="mb-3">
                                            <label class="form-label" for="email"><strong>Email:</strong></label>
                                            <input class="form-control" type="text" id="email" name="email" value="${loggedUser.email}" required>
                                        </div>
                                    </div>
                                    <!-- Modal button --> 
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                                        <button type="submit" class="btn btn-success">Save</button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                    <!-- Modal Emaill Error --> 
                    <c:if test="${sessionScope.emailError != null}">
                        <div class="ms-4 text-danger">${sessionScope.emailError.message}</div>
                        <c:remove var="emailError" scope="session"/>
                    </c:if>



                    <p class="ms-4 mb-3" id="profile">
                        <strong>Password: </strong> *************  
                        <!-- Modal button -->  
                        <a href="${pageContext.request.contextPath}/user-profile?view=editPassword" data-bs-toggle="modal" data-bs-target="#editPasswordModal"><i class="bi bi-pencil-square"></i></a>
                    </p>

                    <!-- Modal -->
                    <div class="modal fade" id="editPasswordModal" tabindex="-1" aria-labelledby="editPasswordModalLabel" aria-hidden="true">
                        <!-- Modal dialog -->  
                        <div class="modal-dialog">
                            <!-- Modal content -->  
                            <div class="modal-content">
                                <!-- Form in modal -->  
                                <form method="post" action="${pageContext.request.contextPath}/user-profile">

                                    <!-- Header -->  
                                    <div class="modal-header">
                                        <h5 class="modal-title" id="editPasswordModalLabel">Change Password</h5>
                                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                    </div>

                                    <!-- Body -->  
                                    <div class="modal-body">
                                        <!-- old password enter field -->  
                                        <div class="mb-3">
                                            <label for="oldPassword" class="form-label"><strong>Old Password:</strong></label>
                                            <input type="password" class="form-control" id="oldPassword" name="oldPassword" required>
                                        </div>
                                        <!-- new password enter field -->  
                                        <div class="mb-3">
                                            <label for="password" class="form-label"><strong>New Password:</strong></label>
                                            <input type="password" class="form-control" id="password" name="password" required>
                                        </div>
                                    </div>
                                    <!-- footer button cancel and save -->  
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                                        <button type="submit" class="btn btn-success">Save</button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>

                    <!-- password error mess -->  
                    <c:if test="${sessionScope.passwordError != null}">
                        <div class="ms-4 text-danger">${sessionScope.passwordError.message}</div>
                        <c:remove var="passwordError" scope="session"/>
                    </c:if>
                </div>
            </div>
        </div>
        <%@include file="../include/footer.jsp" %>
    </body>
</html>
