<%-- 
    Document   : view
    Created on : Jun 22, 2025, 7:21:48 PM
    Author     : Nguyen Ho Phuoc An - CE190747
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <%@include file="/WEB-INF/include/head.jsp" %>
    <body>
        <%@include file="/WEB-INF/include/header.jsp" %>
        <main>
            <div class="container">
                <div class="row">
                    <h1>Discount Code Management</h1>
                </div>
            </div>
            <div class="container">
                <a class="btn btn-success float-end mb-2" href="<c:url value="/admin/discount-code/create"/>">Create</a>
                <table class="table table-striped mb-5">
                    <thead>
                    <th>Code</th>
                    <th>Description</th>
                    <th>Quantity</th>
                    <th>Expiry Date</th>
                    <th>Operations</th>
                    </thead>
                    <div class="fs-3">Free Shipping Codes</div>
                    <c:if test="${empty listShippingCode}">
                        <div class="row">There is no Free Shipping codes.</div>
                    </c:if>
                    <c:forEach items="${listShippingCode}" var="shippingCode">
                        <tr>
                            <td>${shippingCode.code}</td>
                            <td>Free Shipping 100%</td>
                            <td>${shippingCode.quantity}</td>
                            <td>${shippingCode.expiryDate}</td>
                            <td>
                                <a class="btn btn-primary" href="<c:url value="/admin/discount-code/edit">
                                       <c:param name="id" value="${shippingCode.id}"></c:param>
                                   </c:url>">Edit
                                </a>

                                <!-- Button trigger modal -->
                                <c:choose>
                                    <c:when test="${shippingCode.isHidden == 0}">
                                        <button type="button" class="btn btn-secondary delete-btn" data-bs-toggle="modal" data-bs-target="#visibilityModal" data-discountCodeId="${shippingCode.id}" data-discountCodeName="${shippingCode.code}" data-discountCodeHidden="false">Hide</button>
                                    </c:when>
                                    <c:otherwise>
                                        <button type="button" class="btn btn-secondary delete-btn" data-bs-toggle="modal" data-bs-target="#visibilityModal" data-discountCodeId="${shippingCode.id}" data-discountCodeName="${shippingCode.code}" data-discountCodeHidden="true">Unhide</button>                                        
                                    </c:otherwise>
                                </c:choose>
                                <button type="button" class="btn btn-danger delete-btn" data-bs-toggle="modal" data-bs-target="#deleteModal" data-discountCodeId="${shippingCode.id}" data-discountCodeName="${shippingCode.code}" >Delete</button>
                            </td>
                        </tr>
                    </c:forEach>
                </table>

                <table class="table table-striped mb-5">
                    <thead>
                    <th>Code</th>
                    <th>Description</th>
                    <th>Quantity</th>
                    <th>Expiry Date</th>
                    <th>Operations</th>
                    </thead>
                    <div class="fs-3">Discount Codes</div>
                    <c:if test="${empty listPriceCode}">
                        <div class="row">There is no Discount codes.</div>
                    </c:if>
                    <c:forEach items="${listPriceCode}" var="priceCode">
                        <tr>
                            <td>${priceCode.code}</td>
                            <td>
                                Discount: ${priceCode.value}
                                <c:choose>
                                    <c:when test="${priceCode.type == 0}">
                                        %
                                    </c:when>
                                    <c:otherwise>
                                        VND
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>${priceCode.quantity}</td>
                            <td>${priceCode.expiryDate}</td>
                            <td>
                                <a class="btn btn-primary" href="<c:url value="/admin/discount-code/edit">
                                       <c:param name="id" value="${priceCode.id}"></c:param>
                                   </c:url>">Edit
                                </a>

                                <!-- Button trigger modal -->
                                <c:choose>
                                    <c:when test="${priceCode.isHidden == 0}">
                                        <button type="button" class="btn btn-secondary delete-btn" data-bs-toggle="modal" data-bs-target="#visibilityModal" data-discountCodeId="${priceCode.id}" data-discountCodeName="${priceCode.code}" data-discountCodeHidden="false">Hide</button>
                                    </c:when>
                                    <c:otherwise>
                                        <button type="button" class="btn btn-secondary delete-btn" data-bs-toggle="modal" data-bs-target="#visibilityModal" data-discountCodeId="${priceCode.id}" data-discountCodeName="${priceCode.code}" data-discountCodeHidden="true">Unhide</button>                                        
                                    </c:otherwise>
                                </c:choose>

                                <button type="button" class="btn btn-danger delete-btn" data-bs-toggle="modal" data-bs-target="#deleteModal" data-discountCodeId="${priceCode.id}" data-discountCodeName="${priceCode.code}" >Delete</button>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
            <!-- Hide Modal -->
            <div class="modal fade" id="visibilityModal" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="visibilityModalLabel" aria-hidden="true">
                <div class="modal-dialog modal-dialog-centered">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h1 class="modal-title fs-5" id="visibilityModalLabel">Hide Discount Code</h1>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <p>ID: <strong id="discountCodeIdDisplay"></strong></p>
                            <p>Code: <strong id="discountCodeCodeDisplay"></strong></p>
                            <p class="modal-content text-danger" id="visibilityModalContent">Are you sure you want to hide this Discount Code?</p>
                        </div>
                        <div class="modal-footer">
                            <a type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</a>
                            <a id="confirmVisibilityBtn" href="" type="button" class="btn btn-primary">Hide</a>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Delete Modal -->
            <div class="modal fade" id="deleteModal" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="deleteModalLabel" aria-hidden="true">
                <div class="modal-dialog modal-dialog-centered">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h1 class="modal-title fs-5" id="deleteModalLabel">Delete Discount Code</h1>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <p>ID: <strong id="discountCodeIdDisplay"></strong></p>
                            <p>Code: <strong id="discountCodeCodeDisplay"></strong></p>
                            <p class="modal-content text-danger">Are you sure you want to delete this Discount Code?</p>
                            <p class="modal-content text-danger">This action can't be undone</p>
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
                const visibilityModal = document.getElementById('visibilityModal');

                visibilityModal.addEventListener('show.bs.modal', function (event) {
                    // Get the button that triggered the modal
                    const button = event.relatedTarget;

                    // Extract info from data-* attributes
                    const discountCodeId = button.getAttribute('data-discountCodeId');
                    const discountCodeName = button.getAttribute('data-discountCodeName');
                    const discountCodeHidden = button.getAttribute('data-discountCodeHidden');

                    // Update the modal's content
                    const modalTitle = visibilityModal.querySelector('.modal-title');
                    const modalContent = visibilityModal.querySelector('#visibilityModalContent');
                    const discountCodeIdDisplay = visibilityModal.querySelector('#discountCodeIdDisplay');
                    const discountCodeCodeDisplay = visibilityModal.querySelector('#discountCodeCodeDisplay');
                    const confirmVisibilityBtn = visibilityModal.querySelector('#confirmVisibilityBtn');

                    // Update the modal content based on visibility status
                    if (discountCodeHidden === "true") {
                        modalTitle.textContent = "Unhide Discount Code";
                        modalContent.textContent = "Are you sure you want to unhide this discount code?";
                        confirmVisibilityBtn.textContent = "Unhide";
                        confirmVisibilityBtn.href = "${pageContext.request.contextPath}/admin/discount-code/visibility?id=" + discountCodeId + "&hidden=0";
                    } else {
                        modalTitle.textContent = "Hide Discount Code";
                        modalContent.textContent = "Are you sure you want to hide this discount code?";
                        confirmVisibilityBtn.textContent = "Hide";
                        confirmVisibilityBtn.href = "${pageContext.request.contextPath}/admin/discount-code/visibility?id=" + discountCodeId + "&hidden=1";
                    }

                    discountCodeIdDisplay.textContent = discountCodeId;
                    discountCodeCodeDisplay.textContent = discountCodeName;

                });
            });

            document.addEventListener('DOMContentLoaded', function () {
                const deleteModal = document.getElementById('deleteModal');

                deleteModal.addEventListener('show.bs.modal', function (event) {
                    // Get the button that triggered the modal
                    const button = event.relatedTarget;

                    // Extract info from data-* attributes
                    const discountCodeId = button.getAttribute('data-discountCodeId');
                    const discountCodeName = button.getAttribute('data-discountCodeName');

                    // Update the modal's content
                    const modalTitle = deleteModal.querySelector('.modal-title');
                    const discountCodeIdDisplay = deleteModal.querySelector('#discountCodeIdDisplay');
                    const discountCodeCodeDisplay = deleteModal.querySelector('#discountCodeCodeDisplay');
                    const confirmDeleteBtn = deleteModal.querySelector('#confirmDeleteBtn');

                    discountCodeIdDisplay.textContent = discountCodeId;
                    discountCodeCodeDisplay.textContent = discountCodeName;
                    confirmDeleteBtn.href = "${pageContext.request.contextPath}/admin/discount-code/delete?id=" + discountCodeId;
                });
            });

        </script>
    </body>
</html>
