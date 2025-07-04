<%-- 
    Document   : categoriesEdit
    Created on : Jul 4, 2025, 1:40:20 PM
    Author     : Dinh Cong Phuc - CE190770
--%>

<%--
Not done
Apply - line 42
Delete not working -- line 43
List all sp - line 50
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <%@include file="/WEB-INF/include/head.jsp" %>
    <body>
        <%@include file="/WEB-INF/include/header.jsp" %>

        <main>
            <!-- thay doi cac params cua category -->
            <div class="container">
                <div class="container g-3">
                    <h1>Categories Edit</h1>
                </div>
                <c:choose>
                    <c:when test="${requestScope.category == null}">
                        <p>Category with ID = ${param.id} was not found</p>
                    </c:when>
                    <c:otherwise>
                        <form class="form" method="post" action="#">
                            <p>
                                <label class="form-label" for="categoryID">ID</label>
                                <input class="form-control" type="text" id="categoryID" name="categoryID" value="${param.id}">
                            </p>
                            <p>
                                <label class="form-label" for="categoryName">Name</label>
                                <input class="form-control" type="text" id="categoryName" name="categoryName" value="${category.categoryName}">
                            </p>
                            <p>
                                <button class="btn btn-primary" type="apply" name="action" value="apply">Apply</button>
                                <button class="btn btn-secondary" type="reset">Clear</button> // not working
                            </p>
                        </form>
                    </c:otherwise>
                </c:choose>
            </div>
            
            <!-- list all sp trong category -->
        </main>

        <%@include file="/WEB-INF/include/footer.jsp" %>
    </body>
</html>