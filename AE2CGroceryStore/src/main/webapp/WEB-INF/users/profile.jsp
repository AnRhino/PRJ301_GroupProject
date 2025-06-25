<%-- 
    Document   : profile
    Created on : Jun 25, 2025, 3:26:40 PM
    Author     : Le Thien Tri - CE191249
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <%@include file="../include/head.jsp" %>
    <body>
        <%@include file="../include/header.jsp" %>
        <form class="d-flex mt-5 bg-light w-50" style="margin-left: 25%; padding-left: 10%;">
            <div style="position: absolute; top: -35px; right: 30px; width: 150px;">
            </div>
            <table>
                <tbody><tr style="border-bottom: 0 none">
                        <td>
                            <div>

                                <h2>User detail</h2>
                                <table><tbody>
                                        <tr><td><b class="ms-5">Login</b></td><td><span class="ms-5">${loggedUser.username}</span></td></tr>
                                        <tr><td><b class="ms-5">Full name</b></td><td><span class="ms-5">${loggedUser.fullName}</span></td></tr>
                                        <tr><td><b class="ms-5">Image</b></td><td><img class="ms-5" src="assets/images/placeHolder.jpg" style="height:150px;width:120px;border-width:0px;"></td></tr>
                                        <tr><td><b class="ms-5">Email</b></td><td><span class="ms-5">${loggedUser.email}</span></td></tr>
                                    </tbody></table>
                                <input name="ctl00$mainContent$txtError" type="hidden" id="ctl00_mainContent_txtError">

                            </div>
                        </td>
                    </tr>
                    <tr style="border-bottom: 0 none">
                        <td>
                            <br>

                            <table width="100%" style="border: 1px solid transparent;" id="cssTable">
                </tbody></table>

        </td>
    </tr>
</tbody></table>

</form>
<%@include file="../include/footer.jsp" %>
</body>
</html>
