<%-- 
    Document   : pagination
    Created on : Jul 7, 2025, 12:42:22 PM
    Author     : Le Thien Tri - CE191249
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

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
        <c:forEach begin="1" end="${requestScope.totalPages}" var="i">
            <li class="page-item ${param.page == i ? 'active' : ''}">
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