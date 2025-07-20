<%-- 
    Document   : pagination
    Created on : Jul 7, 2025, 12:42:22 PM
    Author     : Le Thien Tri - CE191249
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:choose>
    <c:when test="${requestScope.totalPages gt 3}">

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

        <c:choose>
            <c:when test="${currentPage == 1}">
                <c:set var="previousPage" value="2"/>
            </c:when>
            <c:when test="${currentPage == 2}">
                <c:set var="previousPage" value="2"/>
            </c:when>
            <c:when test="${currentPage == 3}">
                <c:set var="previousPage" value="2"/>
            </c:when>
            <c:otherwise>
                <c:set var="previousPage" value="${currentPage - 1}"/>
            </c:otherwise>
        </c:choose>

        <c:choose>
            <c:when test="${currentPage == requestScope.totalPages}">
                <c:set var="nextPage" value="${requestScope.totalPages - 1}"/>
            </c:when>
            <c:when test="${currentPage == requestScope.totalPages - 1}">
                <c:set var="nextPage" value="${requestScope.totalPages - 1}"/>
            </c:when>
            <c:when test="${currentPage == requestScope.totalPages - 2}">
                <c:set var="nextPage" value="${requestScope.totalPages - 1}"/>
            </c:when>
            <c:otherwise>
                <c:set var="nextPage" value="${currentPage + 1}"/>
            </c:otherwise>
        </c:choose>

        <nav aria-label="Page navigation example" class="ms-auto me-auto">
            <ul class="pagination">


                <c:choose>
                    <c:when test="${currentPage > 1}">
                        <c:url var="pageUrlPrev" value="/home">
                            <c:param name="view" value=""/>
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

                <li class="page-item ${pageZone == 1 ? 'active' : ''}">
                    <a class="page-link" href="<c:url value="/home">
                           <c:param name="view" value="list"/>
                           <c:param name="page" value="1"/>
                       </c:url>">   
                        1
                    </a>
                </li>

                <c:if test="${previousPage != 2}">
                    <li class="page-item disabled">
                        <a class="page-link" href="#">   
                            ...
                        </a>
                    </li>
                </c:if>

                <c:forEach begin="${previousPage}" end="${nextPage}" var="i">
                    <li class="page-item ${pageZone == i ? 'active' : ''}">
                        <a class="page-link" href="<c:url value="/home">
                               <c:param name="view" value="list"/>
                               <c:param name="page" value="${i}"/>
                           </c:url>">   
                            ${i}
                        </a>
                    </li>
                </c:forEach>

                <c:if test="${nextPage != requestScope.totalPages - 1}">
                    <li class="page-item disabled">
                        <a class="page-link" href="#">   
                            ...
                        </a>
                    </li>
                </c:if>

                <li class="page-item ${pageZone == requestScope.totalPages ? 'active' : ''}">
                    <a class="page-link" href="<c:url value="/home">
                           <c:param name="view" value="list"/>
                           <c:param name="page" value="${requestScope.totalPages}"/>
                       </c:url>">   
                        ${requestScope.totalPages}
                    </a>
                </li>


                <c:choose>
                    <c:when test="${currentPage < requestScope.totalPages}">
                        <c:url var="pageUrlNext" value="/home">
                            <c:param name="view" value=""/>
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
    </c:when>
    <c:otherwise>


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

        <c:choose>
            <c:when test="${currentPage == 1}">
                <c:set var="previousPage" value="2"/>
            </c:when>
            <c:when test="${currentPage == 2}">
                <c:set var="previousPage" value="2"/>
            </c:when>
            <c:when test="${currentPage == 3}">
                <c:set var="previousPage" value="2"/>
            </c:when>
            <c:otherwise>
                <c:set var="previousPage" value="${currentPage - 1}"/>
            </c:otherwise>
        </c:choose>

        <c:choose>
            <c:when test="${currentPage == requestScope.totalPages}">
                <c:set var="nextPage" value="${requestScope.totalPages - 1}"/>
            </c:when>
            <c:when test="${currentPage == requestScope.totalPages - 1}">
                <c:set var="nextPage" value="${requestScope.totalPages - 1}"/>
            </c:when>
            <c:when test="${currentPage == requestScope.totalPages - 2}">
                <c:set var="nextPage" value="${requestScope.totalPages - 1}"/>
            </c:when>
            <c:otherwise>
                <c:set var="nextPage" value="${currentPage + 1}"/>
            </c:otherwise>
        </c:choose>

        <nav aria-label="Page navigation example" class="ms-auto me-auto">
            <ul class="pagination">


                <c:choose>
                    <c:when test="${currentPage > 1}">
                        <c:url var="pageUrlPrev" value="/home">
                            <c:param name="view" value=""/>
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

                <c:choose>
                    <c:when test="${currentPage < requestScope.totalPages}">
                        <c:url var="pageUrlNext" value="/home">
                            <c:param name="view" value=""/>
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



    </c:otherwise>
</c:choose>