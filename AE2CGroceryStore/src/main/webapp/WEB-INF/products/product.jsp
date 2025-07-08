<%-- 
    Document   : product
    Created on : Jun 21, 2025, 9:30:37 AM
    Author     : Vu Minh Khang - CE191371
--%>

<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="model.ErrorMessage"%>
<%@page import="model.Review"%>
<%@page import="model.Product"%>
<%@page import="java.util.List"%>
<%@page import="model.Category"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <%@include file="../include/head.jsp" %>
    <body>
        <%@include file="../include/header.jsp" %>
        <c:choose>
            <c:when test="${empty requestScope.productList or empty requestScope.product}">
                <div class="container-fluid">
                    <div>
                        <h1 class="fw-bold">Error</h1>
                        <div class="row">
                            <div>There are no product to display.</div>
                        </div>
                    </div>
                </div>
            </c:when>
            <c:otherwise>
                <div class="container-fluid">
                    <div class="ms-5 me-5"> 
                        <div class="row border border-dark rounded-3 my-3" style="background: #91bbe5;">
                            <div class="col-3">
                                <div class="m-2 p-2 text-center border border-dark rounded-3" style="background: #0c2333;">
                                    <form action="${pageContext.request.contextPath}/user-category" method="get">
                                        <h1 class="fw-bold text-uppercase d-flex justify-content-center align-items-center text-white">
                                            <input type="hidden" name="view" value="category">
                                            <input type="hidden" name="categoryID" value="${requestScope.product.category.categoryID}">
                                            <button type="submit" class="btn btn-transparent">
                                                <h3 class="text-white ">Other product</h3>
                                            </button>
                                        </h1>
                                    </form>
                                </div>
                                <div class="container my-3">
                                    <div class="row border border-dark rounded-3" style="background: #0c2333;"> 
                                        <c:forEach var="prod" items="${requestScope.productList}">
                                            <div class="col-10 m-3 p-2 mx-auto" style="background: #3376bc;">
                                                <form action="${pageContext.request.contextPath}/user-product" method="get">
                                                    <input type="hidden" name="view" value="product">
                                                    <input type="hidden" name="productID" value="${prod.productID}">
                                                    <button class="btn border-0 text-white w-100 text-center">
                                                        ${prod.productName}
                                                    </button>
                                                </form>
                                            </div>
                                        </c:forEach>
                                    </div>
                                </div>
                            </div>
                            <div class="col-9 my-3" style="background: #91bbe5;">
                                <div class="ms-5 me-5">
                                    <div class="my-2 p-2 text-center border border-dark rounded-3" style="background: #0c2333;">
                                        <h1 class="fw-bold text-white">${requestScope.product.productName}</h1>
                                    </div>
                                    <div class="my-2 p-2">
                                        <form action="${pageContext.request.contextPath}/user-product" method="post">
                                            <div class="">
                                                <div class="row border border-secondary bg-white border-dark">   
                                                    <div class="col-6 d-flex justify-content-start p-0">
                                                        <img src="assets/images/placeHolder.jpg" alt="placeholder" style=" height: 500px; object-fit: cover; width: 100%;">
                                                    </div>
                                                    <div class="col-6 py-3">
                                                        <h3>Information:</h3>
                                                        <div class="row my-5 mx-3 gy-3">
                                                            <div class="col-12">
                                                                <div class="fw-bold d-inline">Status:</div>
                                                                <c:choose>
                                                                    <c:when test="${requestScope.product.quantity != 0}">
                                                                        <div class="text-success d-inline">Available.</div>
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <div class="text-danger d-inline">Unstock.</div>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                            </div>
                                                            <div class="col-12">
                                                                <div class="fw-bold d-inline">Stock:</div>
                                                                <div class="text-dark d-inline">${requestScope.product.quantity} items left.</div>
                                                            </div>
                                                            <div class="col-12">
                                                                <div class="fw-bold d-inline">Price:</div>
                                                                <div class="text-dark d-inline">${requestScope.product.price} VND</div>
                                                            </div>
                                                            <div class="col-12">
                                                                <div class="fw-bold d-inline">Rating:</div>
                                                                <div class="text-dark d-inline">${requestScope.rateScore}⭐</div>
                                                            </div>
                                                            <div class="col-12">
                                                                <div class="d-flex align-items-center gap-2">
                                                                    <input type="hidden" name="productID" value="${requestScope.product.productID}">
                                                                    <div class="fw-bold d-inline">Add to cart:</div>
                                                                    <input type="number" class="text-end border-dark w-100 d-inline" name="quantity" placeholder="1" min="1" max="${requestScope.product.quantity}">
                                                                </div>
                                                                <div class="d-flex align-items-center gap-2">
                                                                    <c:choose>
                                                                        <c:when test="${not empty requestScope.errorCartMsg and empty requestScope.successCartMsg}">
                                                                            <p class="text-danger">${requestScope.errorCartMsg.message}</p>
                                                                        </c:when>
                                                                        <c:when test="${empty requestScope.errorCartMsg and not empty requestScope.successCartMsg}">
                                                                            <p class="text-success">${requestScope.successCartMsg}</p>
                                                                        </c:when>
                                                                    </c:choose>
                                                                </div>
                                                            </div>
                                                            <div class="col-12 d-flex gap-3">
                                                                <button type="submit" class="btn px-5 py-3 bg-success border-dark d-flex justify-content-end" name="view" value="cart">
                                                                    <i class="bi bi-basket text-white"></i>
                                                                </button>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </form>
                                    </div>
                                </div>

                                <div class="ms-5 me-5">
                                    <div class="">
                                        <h3 class="fw-bold">Review</h3>
                                    </div>
                                    <div class="bg-secondary border border-dark rounded-3 py-3">
                                        <c:choose>
                                            <c:when test="${empty requestScope.reviewList}">
                                                <p class="d-flex flex-column px-5 gap-2 border-dark p-2 text-light">There are currently no review about this product.</p>
                                                <img class="px-5" src="assets/images/placeHolder.jpg" alt="placeholder">
                                            </c:when>
                                            <c:otherwise>
                                                <c:forEach var="rv" items="${requestScope.reviewList}">
                                                    <div class="row px-5 gap-3 p-2 text-light">

                                                        <div class="col-12">
                                                            <p class="d-inline fw-bold text-dark bg-primary px-2 py-1 rounded-2">${rv.user.username}</p>
                                                        </div>

                                                        <div class="col-12 text-dark bg-white px-2 py-1 rounded-2">    
                                                            <p class="d-inline fw-bold text-wrap">${rv.comment}</p>
                                                        </div>

                                                        <div class="col-12">
                                                            <div class="d-flex justify-content-between align-items-center">
                                                                <div>
                                                                    <p class="d-inline fw-bold text-white">Rate:</p>
                                                                    <c:forEach begin="0" end="${rv.rating}" step="1">⭐</c:forEach>
                                                                    </div>
                                                                    <div class="d-inline text-end">
                                                                    ${rv.date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"))}
                                                                </div>
                                                            </div>
                                                        </div>

                                                        <c:choose>
                                                            <c:when test="${sessionScope.roleId == 1}">
                                                                <div class="col-12 d-flex justify-content-end gap-1">
                                                                    <button type="button" class="btn btn-primary">Do nothing</button>
                                                                    <button type="button" class="btn btn-danger" data-bs-toggle="modal" data-bs-target="#modalDeleteComment${rv.reviewID}">Delete</button>
                                                                </div>
                                                            </c:when>
                                                            <c:when test="${sessionScope.roleId != 1 and rv.user.id == sessionScope.loggedUser.id}">
                                                                <div class="col-12 d-flex justify-content-end gap-1">
                                                                    <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#modalEditComment${rv.reviewID}">Edit</button>
                                                                    <button type="button" class="btn btn-danger" data-bs-toggle="modal" data-bs-target="#modalDeleteComment${rv.reviewID}">Delete</button>
                                                                </div>
                                                            </c:when>
                                                        </c:choose>

                                                        <div class="modal fade text-dark" id="modalDeleteComment${rv.reviewID}" tabindex="-1">
                                                            <div class="modal-dialog">
                                                                <div class="modal-content">
                                                                    <div class="modal-header">
                                                                        <h4 class="modal-title">Delete</h4>
                                                                    </div>
                                                                    <div class="modal-body">
                                                                        <p>Are you sure to delete this comment?</p>
                                                                    </div>
                                                                    <div class="modal-footer">
                                                                        <form action="${pageContext.request.contextPath}/user-product" method="post">
                                                                            <input type="hidden" name="view" value="removeComment">
                                                                            <input type="hidden" name="productID" value="${requestScope.product.productID}">
                                                                            <input type="hidden" name="reviewID" value="${rv.reviewID}">
                                                                            <button type="submit" class="btn btn-danger" data-bs-toggle="modal">Delete</button>
                                                                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                                                                        </form>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>

                                                        <div class="modal fade text-dark" id="modalEditComment${rv.reviewID}" tabindex="-1">
                                                            <div class="modal-dialog">
                                                                <form action="${pageContext.request.contextPath}/user-product" method="post">
                                                                    <div class="modal-content">
                                                                        <div class="modal-header">
                                                                            <h4 class="modal-title">Edit</h4>
                                                                        </div>
                                                                        <div class="modal-body">
                                                                            <input type="text" name="newComment" placeholder="${rv.comment}" class="w-100 border border-dark rounded-3">
                                                                        </div>
                                                                        <div class="modal-footer">
                                                                            <input type="hidden" name="view" value="editComment">
                                                                            <input type="hidden" name="productID" value="${requestScope.product.productID}">
                                                                            <input type="hidden" name="reviewID" value="${rv.reviewID}">
                                                                            <button type="submit" class="btn btn-primary" data-bs-toggle="modal">Edit</button>
                                                                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                                                                        </div>
                                                                    </div>
                                                                </form>
                                                            </div>
                                                        </div>

                                                    </div>
                                                </c:forEach>
                                            </c:otherwise>
                                        </c:choose>
                                        <div class="px-5 gap-3 p-2 text-light">
                                            <form action="${pageContext.request.contextPath}/user-product" method="post" class="d-flex gap-2">
                                                <input type="hidden" name="view" value="comment">
                                                <input type="hidden" name="productID" value="${requestScope.product.productID}">
                                                <input type="text" class="form-control" name="comment" placeholder="Enter your comment here.">
                                                <select name="rating" id="rating" required>
                                                    <option value="1">1 ⭐</option>
                                                    <option value="2">2 ⭐⭐</option>
                                                    <option value="3">3 ⭐⭐⭐</option>
                                                    <option value="4">4 ⭐⭐⭐⭐</option>
                                                    <option value="5">5 ⭐⭐⭐⭐⭐</option>
                                                </select>
                                                <button type="submit" class="btn btn-primary">Enter</button>
                                            </form>
                                            <div class="d-flex align-items-center gap-2">
                                                <div class="d-flex align-items-center gap-2">
                                                    <c:if test="${not empty errorComment}">
                                                        <p class="text-danger">${requestScope.errorComment.message}</p>
                                                    </c:if>
                                                    <c:if test="${not empty errorDeleteComment}">
                                                        <p class="text-danger">${requestScope.errorDeleteComment.message}</p>
                                                    </c:if>
                                                    <c:if test="${not empty successDeleteComment}">
                                                        <p class="text-success">${requestScope.successDeleteComment}</p>
                                                    </c:if>
                                                    <c:if test="${not empty errorEditComment}">
                                                        <p class="text-danger">${requestScope.errorEditComment.message}</p>
                                                    </c:if>
                                                    <c:if test="${not empty successEditComment}">
                                                        <p class="text-success">${requestScope.successEditComment}</p>
                                                    </c:if>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </c:otherwise>
        </c:choose>
        <jsp:include page="../include/footer.jsp" />
    </body>
</html>
