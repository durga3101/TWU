<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" scope="request" value="Admin Profile"/>
<%@ include file="header.jsp" %>

<div class="page-action">Admin</div>

<div class="manage-items">
    <a href="item" id="manageItems">Manage items</a>
</div>

<div class="page-action">All orders</div>

<table class="table">
    <tbody>
    <c:forEach var="item" items="${purchasedItems}" varStatus="row">
        <tr>
            <form:form action="admin" method="post">
                <td>
                    <a href="/userProfile/${item.account.email_address}">
                        <c:out value="${item.account.account_name}"/>
                    </a>
                </td>
                <td><c:out value="${item.item.name}"/></td>
                <td><c:out value="${item.reserve_time}"/></td>
                <td>
                    <select name="state">
                        <c:forEach var="statusoption" items="${item.statusOptions}" varStatus="row">
                            <option ${item.status == statusoption ? 'selected="selected"' : ""}>${statusoption}</option>
                        </c:forEach>
                    </select>
                </td>
                <td><textarea rows="3" name="note">${item.note}</textarea></td>
                <td>
                    <input type="hidden" value="${item.orderId}" name="orderId" />
                    <button type="submit" name="save" value="Save Changes">Save Changes</button>
                </td>

                <%--Order Id is not proper, It is created but not come to admin page, Ella Working On That So We can Add Order Id In Admin Page Later--%>
                <td class="order-id">
                    5
                </td>
            </form:form>
        </tr>
    </c:forEach>
    </tbody>
    <thead>
    <tr>
        <th>User</th>
        <th>Order</th>
        <th>Order Time</th>
        <th>Status</th>
        <th>Note</th>
        <th></th>
    </tr>
    </thead>
</table>


<%@ include file="footer.jsp" %>