<%-- 
    Document   : statisticMonth
    Created on : Jul 17, 2025, 4:41:00 PM
    Author     : Vu Minh Khang - CE191371
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <%@include file="../../include/head.jsp" %>
    <body>
        <%@include file="../../include/header.jsp" %>

        <div class="container-fluid my-1">

            <h1>Product sales statistics recently.</h1>

            <div class="d-flex gap-2">
                <div class="d-flex justify-content-start align-items-center">
                    <a href="<c:url value="/admin"/>"><button class="btn btn-primary">Back to dashboard.</button></a>
                </div>

                <div class="d-flex justify-content-end align-items-center">
                    <form action="<c:url value="/admin/statistic"/>" method="post">
                        <button type="submit" class="btn btn-success" ${empty requestScope.orderList ? "disabled" : ""}>Export</button>
                    </form>   
                </div>
            </div>
            <c:choose>

                <c:when test="${empty requestScope.orderList}">
                    <p class="mx-5 my-1">There are curently no order to show.</p>
                </c:when>

                <c:otherwise>
                    <div class="mx-5 my-1">
                        <table class="table table-striped table-hover table-bordered">
                            <thead>
                                <tr>
                                    <th scope="col" class="col-1">No</th>
                                    <th scope="col" class="col-1">OrderID</th>
                                    <th scope="col" class="col-1">Order Date</th>
                                    <th scope="col">Product Name</th>
                                    <th scope="col">Product Code</th>
                                    <th scope="col">Quantity</th>
                                    <th scope="col">Price</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="order" items="${requestScope.orderList}">

                                    <tr>
                                        <th scope="row">1</th>
                                        <th>OrderID</th>
                                        <th>Order Date</th>
                                        <th>Product Name</th>
                                        <th>Product Code</th>
                                        <th>Quantity</th>
                                        <th>Price</th>
                                    </tr>

                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>

        <%@include file="../../include/footer.jsp" %>
    </body>
</html>
