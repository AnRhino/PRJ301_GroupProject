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

        <div class="container-fluid my-1 bg-secondary">
            
            <h1>Product sales statistics recently.</h1>
            
            <a href="<c:url value="/admin" />"><button class="btn btn-primary">Back to dashboard.</button></a>
            
            <form action="<c:url value="/admin/statistic"/>" method="post">
                <button type="submit" class="btn btn-success">Export</button>
            </form>
            
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
                    <c:forEach var="order" items="${requestScope.orderList}" begin="i">
                        <tr>
                            <th scope="row">${i}</th>
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

        <%@include file="../include/footer.jsp" %>
    </body>
</html>
