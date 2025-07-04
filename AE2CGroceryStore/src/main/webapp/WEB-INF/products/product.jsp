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

        <%
            // Lấy danh sách danh mục và sản phầm từ db.
            List<Product> productList = (List) request.getAttribute("productList");
            List<Review> reviewList = (List) request.getAttribute("reviewList");
            Product product = (Product) request.getAttribute("product");
            ErrorMessage msgErrorCart = (ErrorMessage) request.getAttribute("errorCartMsg");
            String msgCartSuccess = (String) request.getAttribute("successMsg");
            ErrorMessage msgErrorComment = (ErrorMessage) request.getAttribute("errorComment");

            // Kiểm tra null và coi có rỗng không.
            // Nếu có thì hiện thông báo.
            if (productList == null || productList.isEmpty()) {
        %>

        <div class="container-fluid">
            <div>
                <h1 class="fw-bold">Error</h1>
                <div class="row">
                    <div>There are no product to display.</div>
                </div>
            </div>
        </div>

        <%
            // Nếu có sản phẩm thì hiện ra.
        } else {
        %>

        <div class="container-fluid">
            <div class="ms-5 me-5"> 
                <div class="row border border-dark rounded-3 my-3" style="background: #91bbe5;">
                    <div class="col-3">
                        <div class="m-2 p-2 text-center border border-dark rounded-3" style="background: #0c2333;">
                            <form action="<%= request.getContextPath()%>/user-product" method="get">
                                <button class="btn btn-transparent">
                                    <h1 class="fw-bold text-uppercase d-flex justify-content-center align-items-center text-white">Other product</h1>
                                </button>
                            </form>
                        </div>
                        <div class="container my-3">
                            <div class="row border border-dark rounded-3" style="background: #0c2333;">
                                <%
                                    for (Product prod : productList) {
                                %>   
                                <div class="col-10 m-3 p-2 mx-auto" style="background: #3376bc;">
                                    <form action="<%= request.getContextPath()%>/user-product" method="get">
                                        <input type="hidden" name="view" value="product">
                                        <input type="hidden" name="id" value="<%= prod.getProductID()%>">
                                        <button class="btn border-0 text-white w-100 text-center">
                                            <%= prod.getProductName()%>
                                        </button>
                                    </form>
                                </div>
                                <%}%>
                            </div>
                        </div>
                    </div>
                    <div class="col-9 my-3" style="background: #91bbe5;">
                        <div class="ms-5 me-5">
                            <div class="my-2 p-2 text-center border border-dark rounded-3" style="background: #0c2333;">
                                <h1 class="fw-bold text-white"><%= product.getProductName()%></h1>
                            </div>
                            <div class="my-2 p-2">
                                <form action="<%= request.getContextPath()%>/user-product" method="post">
                                    <div class="">
                                        <div class="row border border-secondary bg-white border-dark">   
                                            <div class="col-6 d-flex justify-content-start p-0">
                                                <img src="assets/images/placeHolder.jpg" alt="placeholder">
                                            </div>
                                            <div class="col-6 py-3">
                                                <h3>Information:</h3>
                                                <div class="row my-5 mx-3 gy-3">
                                                    <div class="col-12">
                                                        <div class="fw-bold d-inline">Status:</div>
                                                        <% if (product.getQuantity() != 0) { %>

                                                        <div class="text-success d-inline">Available.</div>

                                                        <% } else {%>

                                                        <div class="text-danger d-inline">Unstock.</div>

                                                        <% }%>
                                                    </div>
                                                    <div class="col-12">
                                                        <div class="fw-bold d-inline">Stock:</div>
                                                        <div class="text-dark d-inline"><%= product.getQuantity()%> items left.</div>
                                                    </div>
                                                    <div class="col-12">
                                                        <div class="fw-bold d-inline">Price:</div>
                                                        <div class="text-dark d-inline"><%= product.getPrice()%> VND</div>
                                                    </div>
                                                    <div class="col-12">
                                                        <div class="fw-bold d-inline">Rating:</div>
                                                        <div class="text-dark d-inline"><%= request.getAttribute("rateScore")%></div>
                                                        <i class="bi bi-star-fill text-warning"></i>
                                                    </div>
                                                    <div class="col-12">
                                                        <div class="d-flex align-items-center gap-2">
                                                            <input type="hidden" name="id" value="<%= product.getProductID()%>">
                                                            <div class="fw-bold d-inline">Add to cart:</div>
                                                            <input type="number" class="text-end border-dark w-100 d-inline" name="quantity" placeholder="1" min="1" max="<%= product.getQuantity()%>">
                                                        </div>
                                                        <div class="d-flex align-items-center gap-2">
                                                            <% if (msgErrorCart != null && msgCartSuccess == null) {%>
                                                            <p class="text-danger"><%= msgErrorCart.getMessage()%></p>
                                                            <%} else if (msgErrorCart == null && msgCartSuccess != null) {%>
                                                            <p class="text-success"><%= msgCartSuccess%></p>
                                                            <%}%>
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
                            <% if (reviewList == null || reviewList.isEmpty()) {%>

                            <div class="bg-secondary border border-dark rounded-3 py-3">
                                <p class="d-flex flex-column px-5 gap-2 border-dark p-2 text-light">There are currently no review about this product.</p>
                                <img class="px-5" src="assets/images/placeHolder.jpg" alt="placeholder">
                                <div class="px-5 gap-3 p-2 text-light">
                                    <form action="<%= request.getContextPath()%>/user-product" method="post"class="d-flex gap-2">
                                        <input type="hidden" name="view" value="comment">
                                        <input type="hidden" name="id" value="<%= ((Product) (request.getAttribute("product"))).getProductID()%>">
                                        <input type="text" class="form-control" name="comment" placeholder="Enter your comment here.">
                                        <select name="rating" id="rating" required>-- Select rating --
                                            <option value="1">1 ⭐</option>
                                            <option value="2">2 ⭐⭐</option>
                                            <option value="3">3 ⭐⭐⭐</option>
                                            <option value="4">4 ⭐⭐⭐⭐</option>
                                            <option value="5">5 ⭐⭐⭐⭐⭐</option>
                                        </select>
                                        <button type="submit" class="btn btn-primary">Enter</button>
                                    </form>
                                    <div class="d-flex align-items-center gap-2">
                                        <% if (msgErrorComment != null) {%>
                                        <p class="text-danger"><%= msgErrorComment.getMessage()%></p>
                                        <%}%>
                                    </div>
                                </div>
                            </div>
                            <% } else { %>

                            <div class="bg-secondary border border-dark rounded-3 py-3">
                                <% for (Review rv : reviewList) {%>
                                <div class="row px-5 gap-3 p-2 text-light">
                                    <div class="col-12">
                                        <p class="d-inline fw-bold text-dark bg-primary px-2 py-1 rounded-2"><%= rv.getUser().getUsername()%></p>
                                    </div>
                                    <div class="col-12 text-dark bg-white  px-2 py-1 rounded-2">    
                                        <p class="d-inline fw-bold text-wrap"><%= rv.getComment()%></p>
                                    </div>
                                    <div class="col-12">
                                        <div class="d-flex justify-content-between align-items-center">
                                            <div>
                                                <p class="d-inline fw-bold text-white">Rate:</p>
                                                <% for (int idx = 0; idx < rv.getRating(); idx++) { %>
                                                ⭐
                                                <%}%>
                                            </div>
                                            <div class="d-inline text-end">
                                                <%= rv.getDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))%>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <% }%>
                                <div class="px-5 gap-3 p-2 text-light">
                                    <form action="<%= request.getContextPath()%>/user-product" method="post"class="d-flex gap-2">
                                        <input type="hidden" name="view" value="comment">
                                        <input type="hidden" name="id" value="<%= ((Product) (request.getAttribute("product"))).getProductID()%>">
                                        <input type="text" class="form-control" name="comment" placeholder="Enter your comment here.">
                                        <select name="rating" id="rating" required>-- Select rating --
                                            <option value="1">1 ⭐</option>
                                            <option value="2">2 ⭐⭐</option>
                                            <option value="3">3 ⭐⭐⭐</option>
                                            <option value="4">4 ⭐⭐⭐⭐</option>
                                            <option value="5">5 ⭐⭐⭐⭐⭐</option>
                                        </select>
                                        <button type="submit" class="btn btn-primary">Enter</button>
                                    </form>
                                    <div class="d-flex align-items-center gap-2">
                                        <% if (msgErrorComment != null) {%>
                                        <p class="text-danger"><%= msgErrorComment.getMessage()%></p>
                                        <%}%>
                                    </div>
                                </div>
                            </div>
                            <% } %>
                        </div>
                    </div>
                </div>
            </div>
            <%
                }
            %>

            <jsp:include page="../include/footer.jsp" />
    </body>
</html>
