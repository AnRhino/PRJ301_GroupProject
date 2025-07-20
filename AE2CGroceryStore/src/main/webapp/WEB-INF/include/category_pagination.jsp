<%-- 
    Document   : pagination
    Created on : Jul 7, 2025, 12:42:22 PM
    Author     : Le Thien Tri - CE191249
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<c:choose>
    <c:when test="${empty param.page}">
        <c:set var="currentPage" value="1"/>
    </c:when>
    <c:when test="${param.page lt 1}">
        <c:set var="currentPage" value="1"/>
    </c:when>
    <c:when test="${param.page gt requestScope.totalPages}">
        <c:set var="currentPage" value="${requestScope.totalPages}"/>
    </c:when>
    <c:otherwise>
        <c:set var="currentPage" value="${param.page}"/>
    </c:otherwise>
</c:choose>

<nav aria-label="Page navigation example" class="d-flex align-content-center justify-content-center my-3">
    <ul class="pagination">

        <c:choose>
            <c:when test="${currentPage > 1}">
                <c:url var="pageUrlPrev" value="/user-category">
                    <c:param name="view" value="category"/>
                    <c:param name="categoryID" value="${param.categoryID}"/>
                    <c:param name="page" value="${currentPage - 1}"/>
                </c:url>
                <li class="page-item">
                    <a class="page-link" href="${pageUrlPrev}" aria-label="Previous">
                        <span aria-hidden="true">&laquo;</span>
                    </a>
                </li>
            </c:when>
            <c:otherwise>
                <li class="page-item disabled">
                    <a class="page-link" href="#" aria-label="Previous">
                        <span aria-hidden="true">&laquo;</span>
                    </a>
                </li>
            </c:otherwise>
        </c:choose>

        <c:forEach begin="1" end="${requestScope.totalPages}" var="i">
            <c:url var="pageUrl" value="/user-category">
                <c:param name="view" value="category"/>
                <c:param name="categoryID" value="${param.categoryID}"/>
                <c:param name="page" value="${i}"/>
            </c:url>
            <li class="page-item ${i == currentPage ? 'active' : ''}">
                <a class="page-link" href="${pageUrl}">${i}</a>
            </li>
        </c:forEach>


        <c:choose>
            <c:when test="${currentPage < requestScope.totalPages}">
                <c:url var="pageUrlNext" value="/user-category">
                    <c:param name="view" value="category"/>
                    <c:param name="categoryID" value="${param.categoryID}"/>
                    <c:param name="page" value="${currentPage + 1}"/>
                </c:url>
                <li class="page-item">
                    <a class="page-link" href="${pageUrlNext}" aria-label="Next">
                        <span aria-hidden="true">&raquo;</span>
                    </a>
                </li>
            </c:when>
            <c:otherwise>
                <li class="page-item disabled">
                    <a class="page-link" href="#" aria-label="Next">
                        <span aria-hidden="true">&raquo;</span>
                    </a>
                </li>
            </c:otherwise>
        </c:choose>
    </ul>
</nav>