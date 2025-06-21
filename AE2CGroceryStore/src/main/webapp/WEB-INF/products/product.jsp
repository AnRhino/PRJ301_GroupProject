<%-- 
    Document   : product
    Created on : Jun 21, 2025, 9:30:37 AM
    Author     : Vu Minh Khang - CE191371
--%>

<%@page import="model.Product"%>
<%@page import="java.util.List"%>
<%@page import="model.Category"%>
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
            Product product = (Product) request.getAttribute("product");

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
                <div class="row">
                    <div class="col-3">
                        <h1 class="fw-bold">Other product</h1>
                        <%
                            for (Product prod : productList) {
                        %>   

                        <div class="col-12 d-flex justify-content-center my-3 border bg-dark py-2">
                            <form action="<%= request.getContextPath()%>/userProduct" method="get">
                                <input type="hidden" name="view" value="product">
                                <input type="hidden" name="id" value="<%= prod.getProductID()%>">
                                <button class="btn p-0 border-0 text-white">
                                    <div class="row">
                                        <div class="col-12 d-flex justify-content-center">
                                            <%= prod.getProductName()%>
                                        </div>
                                    </div>
                                </button>
                            </form>
                        </div>

                        <%}%>
                    </div>
                    <div class="col-9">
                        <div class="ms-5 me-5">
                            <h1 class="fw-bold"><%= product.getProductName()%></h1>
                            <form class="gap-3 d-flex" action="<%= request.getContextPath()%>/userProduct" method="post">
                                <div class="row border border-secondary bg-secondary border-dark">   
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
                                                <%= product.getQuantity()%> items left.
                                            </div>
                                            <div class="col-12">
                                                <div class="fw-bold d-inline">Price:</div>
                                                <%= product.getPrice()%>$
                                            </div>
                                            <div class="col-12">
                                                <div class="fw-bold d-inline">Rating:</div>
                                                <%= request.getAttribute("rateScore")%>
                                                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-star-fill" viewBox="0 0 16 16">
                                                <path d="M3.612 15.443c-.386.198-.824-.149-.746-.592l.83-4.73L.173 6.765c-.329-.314-.158-.888.283-.95l4.898-.696L7.538.792c.197-.39.73-.39.927 0l2.184 4.327 4.898.696c.441.062.612.636.282.95l-3.522 3.356.83 4.73c.078.443-.36.79-.746.592L8 13.187l-4.389 2.256z"/>
                                                </svg>
                                            </div>
                                            <div class="col-12">
                                                <div class="d-inline">
                                                    <input type="hidden" name="id" value="<%= product.getProductID()%>">
                                                    <div class="fw-bold">Add to cart:</div>
                                                    <input type="number" class="text-end border-dark" name="quantity" placeholder="0">
                                                </div>
                                            </div>
                                            <div class="col-12 d-flex gap-3">
                                                <button class="btn px-5 py-3 bg-success border-dark d-flex justify-content-end" name="status" value="buy">
                                                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-cart-fill" viewBox="0 0 16 16">
                                                    <path d="M0 1.5A.5.5 0 0 1 .5 1H2a.5.5 0 0 1 .485.379L2.89 3H14.5a.5.5 0 0 1 .491.592l-1.5 8A.5.5 0 0 1 13 12H4a.5.5 0 0 1-.491-.408L2.01 3.607 1.61 2H.5a.5.5 0 0 1-.5-.5M5 12a2 2 0 1 0 0 4 2 2 0 0 0 0-4m7 0a2 2 0 1 0 0 4 2 2 0 0 0 0-4m-7 1a1 1 0 1 1 0 2 1 1 0 0 1 0-2m7 0a1 1 0 1 1 0 2 1 1 0 0 1 0-2"/>
                                                    </svg>
                                                </button>
                                                <button class="btn px-5 py-3 bg-danger border-dark d-flex justify-content-end" name="status" value="cancel">
                                                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-cart-fill" viewBox="0 0 16 16">
                                                    <path d="M0 1.5A.5.5 0 0 1 .5 1H2a.5.5 0 0 1 .485.379L2.89 3H14.5a.5.5 0 0 1 .491.592l-1.5 8A.5.5 0 0 1 13 12H4a.5.5 0 0 1-.491-.408L2.01 3.607 1.61 2H.5a.5.5 0 0 1-.5-.5M5 12a2 2 0 1 0 0 4 2 2 0 0 0 0-4m7 0a2 2 0 1 0 0 4 2 2 0 0 0 0-4m-7 1a1 1 0 1 1 0 2 1 1 0 0 1 0-2m7 0a1 1 0 1 1 0 2 1 1 0 0 1 0-2"/>
                                                    </svg>
                                                </button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div>
                            
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
