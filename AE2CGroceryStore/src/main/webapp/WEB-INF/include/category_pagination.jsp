<%-- 
    Document   : pagination
    Created on : Jul 7, 2025, 12:42:22 PM
    Author     : Le Thien Tri - CE191249
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<c:set var="currentPage" value="${requestScope.currentPage}" />

<c:if test="${totalPages > 0}">

    <c:set var="previousPage" value="${currentPage - 1}"/>
    <c:set var="nextPage" value="${currentPage + 1}"/>

    <nav aria-label="Page navigation example" class="d-flex align-content-center justify-content-center my-3">
        <ul class="pagination">
            <!-- Icon previous page -->
            <c:choose>
                <c:when test="${currentPage > 1}">

                    <li class="page-item">
                        <a class="page-link" href="<c:url value="/user-category">
                               <c:param name="view" value="category"/>
                               <c:param name="categoryID" value="${param.categoryID}"/>
                               <c:param name="page" value="${currentPage - 1}"/>
                           </c:url>" aria-label="Previous">
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

            <!-- Page 1 (First page) -->     
            <c:if test="${previousPage > 1}">
                <li class="page-item">
                    <a class="page-link" href="<c:url value="/user-category">
                           <c:param name="view" value="category"/>
                           <c:param name="categoryID" value="${param.categoryID}"/>
                           <c:param name="page" value="1"/>
                       </c:url>">
                        <span aria-hidden="true">1</span>
                    </a>
                </li>
            </c:if>

            <!-- Icon ... --> 
            <c:if test="${previousPage > 2}">
                <li class="page-item disabled">
                    <a class="page-link" href="#">   
                        ...
                    </a>
                </li>
            </c:if>

            <!-- Previous page -->
            <c:if test="${previousPage > 0}">
                <li class="page-item">
                    <a class="page-link" href="<c:url value="/user-category">
                           <c:param name="view" value="category"/>
                           <c:param name="categoryID" value="${param.categoryID}"/>
                           <c:param name="page" value="${previousPage}"/>
                       </c:url>">
                        <span aria-hidden="true">${previousPage}</span>
                    </a>
                </li>
            </c:if>

            <!-- Current page -->
            <li class="page-item active">
                <a class="page-link" href="<c:url value="/user-category">
                       <c:param name="view" value="category"/>
                       <c:param name="categoryID" value="${param.categoryID}"/>
                       <c:param name="page" value="${currentPage}"/>
                   </c:url>">
                    <span aria-hidden="true">${currentPage}</span>
                </a>
            </li>

            <!-- Next page -->
            <c:if test="${nextPage <= totalPages}">
                <li class="page-item">
                    <a class="page-link" href="<c:url value="/user-category">
                           <c:param name="view" value="category"/>
                           <c:param name="categoryID" value="${param.categoryID}"/>
                           <c:param name="page" value="${nextPage}"/>
                       </c:url>">
                        <span aria-hidden="true">${nextPage}</span>
                    </a>
                </li>
            </c:if>

            <!-- Icon ... --> 
            <c:if test="${nextPage < totalPages-1}">
                <li class="page-item disabled">
                    <a class="page-link" href="#">   
                        ...
                    </a>
                </li>
            </c:if>

            <!-- Last page -->     
            <c:if test="${nextPage < totalPages}">
                <li class="page-item">
                    <a class="page-link" href="<c:url value="/user-category">
                           <c:param name="view" value="category"/>
                           <c:param name="categoryID" value="${param.categoryID}"/>
                           <c:param name="page" value="${totalPages}"/>
                       </c:url>">
                        <span aria-hidden="true">${totalPages}</span>
                    </a>
                </li>
            </c:if>

            <!-- Icon next page -->  

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
</c:if>