<%-- 
    Document   : categoriesCreate
    Created on : Jul 4, 2025, 9:54:05 PM
    Author     : Dinh Cong Phuc - CE190770
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
            <div class="container">
                <div class="container g-3">
                    <h1>Create New Category</h1>
                </div>
                <strong>${errorMessage}</strong>
                <form id="form-create" class="form" method="post" action="<c:url value='/admin/categories/create'/>" enctype="multipart/form-data">
                    <p>
                        <label class="form-label" for="categoryName">Name</label>
                        <input class="form-control" type="text" id="categoryName" name="categoryName" />
                    </p>
                    <div class="mb-3">
                        <label> Album cover</label>
                        <div class="mb-2">
                            <img id="imagePreview" src="" alt="Preview" style="max-width: 200px; max-height: 200px; display:none;" class="img-thumbnail"/>
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
                        <input type="checkbox" name="isHidden" id="isHidden" value="true" class="form-check-input"/>
                        <label class="form-check-label" for="isHidden">Hide Category On Creation</label>
                    </div>
                    <p>
                        <button type="submit" name="action" value="create" class="btn btn-success">Add</button>
                        <button type="reset" class="btn btn-secondary">Clear</button>
                    </p>
                </form>
            </div>
        </main>

        <%@include file="/WEB-INF/include/footer.jsp" %>
        <script>
            $("#form-create").validate({
                rules: {
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

