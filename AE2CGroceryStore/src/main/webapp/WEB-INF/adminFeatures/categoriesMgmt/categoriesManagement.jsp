<%-- 
    Document   : categoriesManagement
    Created on : Jun 29, 2025, 1:35:55 PM
    Author     : Dinh Cong Phuc - CE190770
--%>

<%--
not done
Listing count - line 36
Delete - line 43
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <%@include file="/WEB-INF/include/head.jsp" %>
    <body>
        <%@include file="/WEB-INF/include/header.jsp" %>

        <main>
            <div class="container g-3">
                <h1>Categories Management</h1>
            </div>
            <div class="container">
                <a href="<c:url value="/admin/categories/create"/>" class="btn btn-primary">Create</a>
            </div>
            <div id="categories" class="container pb-4">
                <div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 row-cols-lg-4 g-4">
                    <c:forEach var="category" items="${categories}">
                        <div class="col">
                            <div class="card h-100 d-flex flex-column">
                                <img src="<c:url value="/get-image/${category.coverImg}"/>" class="card-img-top" alt="${category.categoryName}">
                                <div class="card-body mt-auto">
                                    <h5 class="card-title">${category.categoryName}</h5>
                                    <p class="card-text">Listings: ${productCounts[category.categoryID]}</p>
                                    <p class="card-text">Hidden: ${category.checkIsHidden()}</p>

                                    <a href="<c:url value="/admin/categories/edit">
                                           <c:param name="categoryID" value="${category.categoryID}"/>
                                       </c:url>"
                                       class="btn btn-primary">Edit
                                    </a>

                                    <!-- Button trigger modal -->
                                    <button type="button" class="btn btn-danger delete-btn" data-bs-toggle="modal" data-bs-target="#deleteModal" data-category-id="${category.categoryID}" data-category-name="${category.categoryName}">
                                        Delete
                                    </button>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
            <!-- Modal -->
            <div class="modal fade" id="deleteModal" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="deleteModalLabel" aria-hidden="true">
                <div class="modal-dialog modal-dialog-centered">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h1 class="modal-title fs-5" id="deleteModalLabel">Delete Category</h1>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <p>Category ID: <strong id="categoryIdDisplay"></strong></p>
                            <p>Category Name: <strong id="categoryNameDisplay"></strong></p>
                            <p class="text-danger">Are you sure you want to delete this category?</p>
                        </div>
                        <div class="modal-footer">
                            <a type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</a>
                            <a id="confirmDeleteBtn" href="" type="button" class="btn btn-primary">Delete</a>
                        </div>
                    </div>
                </div>
            </div>
        </main>

        <%@include file="/WEB-INF/include/footer.jsp" %>
        <script>
            document.addEventListener('DOMContentLoaded', function () {
                const deleteModal = document.getElementById('deleteModal');

                deleteModal.addEventListener('show.bs.modal', function (event) {
                    // Get the button that triggered the modal
                    const button = event.relatedTarget;

                    // Extract info from data-* attributes
                    const categoryId = button.getAttribute('data-category-id');
                    const categoryName = button.getAttribute('data-category-name');

                    // Update the modal's content
                    const modalTitle = deleteModal.querySelector('.modal-title');
                    const categoryIdDisplay = deleteModal.querySelector('#categoryIdDisplay');
                    const categoryNameDisplay = deleteModal.querySelector('#categoryNameDisplay');
                    const confirmDeleteBtn = deleteModal.querySelector('#confirmDeleteBtn');

                    categoryIdDisplay.textContent = categoryId;
                    categoryNameDisplay.textContent = categoryName;
                    confirmDeleteBtn.href = "${pageContext.request.contextPath}/admin/categories/delete?id=" + categoryId;
                            });
                        });
        </script>
    </body>
</html>
