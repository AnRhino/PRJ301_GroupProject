<%-- 
    Document   : discountCodeEdit
    Created on : Jul 15, 2025, 9:20:38 PM
    Author     : Dinh Cong Phuc - CE190770
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <%@include file="/WEB-INF/include/head.jsp" %>
    <body>
        <%@include file="/WEB-INF/include/header.jsp" %>
        <main>
            <div class="container">
                <div class="container g-3">
                    <h1>Edit Discount Codes</h1>
                </div>
                <c:if test = "${not empty errorMessage}">
                    <strong class="text-danger">${errorMessage}</strong>
                </c:if>

                <c:choose>
                    <c:when test="${requestScope.discountCode == null}">
                        <strong>Discount Code with id ${param.id} does not exists</strong>
                    </c:when>
                    <c:otherwise>
                        <form id="form-update" class="form" method="post" action="<c:url value='/admin/discount-code/edit'/>">

                            <label class="form-label" for="id">ID</label>
                            <div class="mb-3">
                                <input class="form-control" type="text" id="id" name="id" value="${discountCode.id}" readonly/>
                            </div>

                            <label class="form-label" for="type">Discount Type</label>
                            <div class="mb-3">
                                <select class="form-select" name="type" id="type">
                                    <c:forEach items="${discountType}" var="discountType">
                                        <option <c:if test="${discountType.typeId eq discountCode.type}">selected</c:if> value="${discountType.typeId}">
                                            ${discountType.typeName}
                                        </option>
                                    </c:forEach>
                                </select>
                                <c:if test = "${not empty typeError}">
                                    <strong class="text-danger">${typeError}</strong>
                                </c:if>
                            </div>

                            <label class="form-label" for="code">Code (Must be 5 to 15 character, only Latin character A-Z and number 0-9)</label>
                            <div class="mb-3">
                                <input class="form-control" type="text" id="code" name="code" value="${discountCode.code}"/>
                                <c:if test = "${not empty codeError}">
                                    <strong class="text-danger">${codeError}</strong>
                                </c:if>
                            </div>

                            <label class="form-label" for="value">Value</label>
                            <div class="input-group mb-3">
                                <div class="input-group-prepend">
                                    <span class="input-group-text">VND | %</span>
                                </div>
                                <input class="form-control" type="number" id="value" name="value" value="${discountCode.value}" />
                                <c:if test = "${not empty valueError}">
                                    <strong class="text-danger">${valueError}</strong>
                                </c:if>
                            </div>

                            <label class="form-label" for="quantity">Quantity</label>
                            <div class="mb-3">
                                <input class="form-control" type="number" id="quantity" name="quantity" value="${discountCode.quantity}" />
                                <c:if test = "${not empty quantityError}">
                                    <strong class="text-danger">${quantityError}</strong>
                                </c:if>
                            </div>

                            <label class="form-label" for="expiryDate">Expiry Date</label>
                            <div class="mb-3">
                                <input class="form-control" type="date" id="expiryDate" name="expiryDate" value="${discountCode.expiryDate}" />
                                <c:if test = "${not empty expiryDateError}">
                                    <strong class="text-danger">${expiryDateError}</strong>
                                </c:if>
                            </div>

                            <label class="form-label" for="minOrderValue">Minimum Order Value</label>
                            <div class="mb-3">
                                <input class="form-control" type="number" id="minOrderValue" name="minOrderValue" value="${discountCode.minOrderValue}" />
                                <c:if test = "${not empty minOrderValueError}">
                                    <strong class="text-danger">${minOrderValueError}</strong>
                                </c:if>
                            </div>

                            <label class="form-check-label" for="isHidden">Hide Discount Code</label>
                            <div class="form-check form-switch">
                                <input type="checkbox" name="isHidden" id="isHidden" value="true" class="form-check-input" ${discountCode.isHidden == 1 ? "checked" : ""}/>
                                <c:if test = "${not empty isHiddenError}">
                                    <strong class="text-danger">${isHiddenError}</strong>
                                </c:if>
                            </div>

                            <p>
                                <button type="submit" name="action" value="update" class="btn btn-success">Apply</button>
                                <button type="reset" class="btn btn-secondary">Clear</button>
                            </p>
                        </form>
                    </c:otherwise>
                </c:choose>

            </div>
        </main>
        <%@include file="/WEB-INF/include/footer.jsp" %>
        <script>
            $("#form-update").validate({
                rules: {
                    id: {
                        required: true,
                        number: true,
                        range: [$("#id").val(), $("#id").val()]
                    },
                    type: {
                        required: true,
                        digits: true,
                        range: [0, 2]
                    },
                    code: {
                        required: true,
                        rangelength: [5, 15]
                    },
                    value: {
                        required: true,
                        digits: true,
                        min: 0
                    },
                    quantity: {
                        required: true,
                        digits: true,
                        min: 0
                    },
                    expiryDate: {
                        required: true,
                        dateISO: true
                    },
                    minOrderValue: {
                        required: true,
                        digits: true,
                        range: [0, 50000000]
                    }
                },
                messages: {
                    code: {
                        required: "Please enter code"
                    },
                    value: {
                        required: "Please enter value"
                    },
                    quantity: {
                        required: "Please enter quantity"
                    },
                    expiryDate: {
                        required: "Please choose expire date"
                    },
                    minOrderValue: {
                        required: "Please enter minimun order value"
                    }
                }
            });
        </script>
    </body>
</html>
