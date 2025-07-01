<%-- 
    Document   : cart
    Created on : Jun 28, 2025, 10:19:44 PM
    Author     : Vu Minh Khang - CE191371
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <%@include file="../include/head.jsp" %>
    <body>
        <%@include file="../include/header.jsp" %>

        <div class="container-fluid p-5 gap-3">

                        <table class="table">
                            <thead>
                                <tr>
                                    <th scope="col">#</th>
                                    <th scope="col">First</th>
                                    <th scope="col">Last</th>
                                    <th scope="col">Handle</th>
                                </tr>
                            </thead>

                            <tbody>

            <c:forEach var="cart" items="${requestScope.cartList}">
            <div>
                            <form>
                                <tr>
                                    <th scope="row">1</th>
                                    <td>${cart.}</td>
                                    <td>Otto</td>
                                    <td>@mdo</td>
                                </tr>
                            </form>

                            </tbody>
                        </table>
            </div>
            </c:forEach>


        </div>

        <jsp:include page="../include/footer.jsp" />
    </body>
</html>
