<%-- 
    Document   : pagination
    Created on : Jul 7, 2025, 12:42:22 PM
    Author     : Le Thien Tri - CE191249
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<c:if test="${sessionScope.emailError != null}">
    <div class="ms-4 text-danger">${sessionScope.emailError.message}</div>
    <c:remove var="emailError" scope="session"/>
</c:if>
<nav aria-label="Page navigation example" class="ms-auto me-auto">
    <ul class="pagination">
        <li class="page-item ${param.page == 1 ? 'disabled' : ''}">
            <a class="page-link" href="<c:url value="/home">
                   <c:param name="view" value="list"/>
                   <c:param name="page" value="1"/>
               </c:url>" aria-label="Previous">  
                <span aria-hidden="true">&laquo;</span>
            </a>              
        </li>
        <c:set var="pageZone">
            <c:choose>
                <c:when test="${empty param.page}">
                    1
                </c:when>
                <c:when test="${param.page lt 1}">
                    1
                </c:when>
                <c:when test="${param.page gt requestScope.totalPages}">
                    ${requestScope.totalPages}
                </c:when>
                <c:otherwise>
                    ${param.page}
                </c:otherwise>
            </c:choose>
        </c:set>
        <c:forEach begin="1" end="${requestScope.totalPages}" var="i">
            <li class="page-item ${pageZone == i ? 'active' : ''}">
                <a class="page-link" href="<c:url value="/home">
                       <c:param name="view" value="list"/>
                       <c:param name="page" value="${i}"/>
                   </c:url>">   
                    ${i}
                </a>
            </li>
        </c:forEach>
        <li class="page-item ${param.page == requestScope.totalPages ? 'disabled' : ''}">      
            <a class="page-link" href="<c:url value="/home">
                   <c:param name="view" value="list"/>
                   <c:param name="page" value="${requestScope.totalPages}"/>
               </c:url>" aria-label="Next">   
                <span aria-hidden="true">&raquo;</span>
            </a>       
        </li>
    </ul>
</nav>