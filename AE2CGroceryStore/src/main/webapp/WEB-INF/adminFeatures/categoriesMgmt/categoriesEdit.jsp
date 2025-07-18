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
<script>
    function previewImage(event) {
        const reader = new FileReader();
        const imagePreview = document.getElementById('imagePreview');
        const file = event.target.files[0];

        if (file) {
            reader.onload = function (e) {
                imagePreview.src = e.target.result;
                imagePreview.style.display = "block";
            };
            reader.readAsDataURL(file);
        }
    }
</script>

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
                        <p>Category with ID = ${param.categoryID} was not found</p>
                    </c:when>
                    <c:otherwise>
                        <strong>${errorMessage}</strong>
                        <form id="form-edit" class="form" method="post" action="<c:url value="/admin/categories/edit"/>" enctype="multipart/form-data">
                            <p>
                                <label class="form-label" for="categoryID">ID</label>
                                <input class="form-control" type="text" id="categoryID" name="categoryID" value="${category.categoryID}" readonly>
                            </p>
                            <p>
                                <label class="form-label" for="categoryName">Name</label>
                                <input class="form-control" type="text" id="categoryName" name="categoryName" value="${category.categoryName}">
                            </p>
                            <div class="mb-3">
                                <label> Album cover</label>
                                <div class="mb-2">
                                    <img id="imagePreview" src="<c:url value="/get-image/${category.coverImg}"/>" alt="${category.categoryName}" style="max-width: 200px; max-height: 200px; display:block;" class="img-thumbnail"/>
                                </div>
                                <input type="file"
                                       name="coverImg"
                                       accept="image/*"
                                       class="form-control"
                                       id="coverImg"
                                       onchange="previewImage(event)"
                                       />
                            </div>
                            <div class="form-check form-switch">
                                <input type="checkbox" name="isHidden" id="isHidden" role="switch" value="true" class="form-check-input"  <c:if test="${category.checkIsHidden()}">checked</c:if> />
                                    <label class="form-check-label" for="isHidden">Hide Category</label>
                                </div>
                                <p>
                                    <button class="btn btn-primary" type="apply" name="action" value="apply">Apply</button>
                                    <button class="btn btn-secondary" type="reset">Reset</button>
                                </p>
                            </form>
                    </c:otherwise>
                </c:choose>
            </div>
        </main>

        <%@include file="/WEB-INF/include/footer.jsp" %>
        <script>
            $("#form-edit").validate({
                rules: {
                    categoryID: {
                        digits: true,
                        min: $("#categoryID").val(),
                        max: $("#categoryID").val()
                    },
                    categoryName: {
                        required: true
                    }
                }, 
                messages: {
                    categoryName: {
                        required: "Please enter category name"
                    }
                }
            });
        </script>
    </body>
</html>