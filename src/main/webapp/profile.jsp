<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="shop" uri="/shopTags" %>

<c:set var="language"
       value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}"
       scope="session"/>
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="i18n.text" />

<html lang="${language}">
    <jsp:include page="partials/meta.jsp" />

    <body class="mdl-color--grey-100 mdl-color-text--grey-700 mdl-base">
        <div class="mdl-layout mdl-js-layout mdl-layout--fixed-header">
            <jsp:include page="partials/header.jsp" />

            <main class="mdl-layout__content">
                <div class="mdl-grid">
                    <div class="shop-inp__form-card mdl-card mdl-shadow--2dp mdl-cell mdl-cell--6-col">
                        <h3><fmt:message key="profile.orders.paid" /></h3>
                        <table class="mdl-data-table mdl-js-data-table">
                            <thead>
                                <tr class="table-head-row">
                                    <th><fmt:message key="profile.table.positions" /></th>
                                    <th class="mdl-data-table__cell--non-numeric">
                                        <fmt:message key="profile.table.made" />
                                    </th>
                                    <th><fmt:message key="profile.table.price" /></th>
                                </tr>
                            </thead>

                            <tbody>
                            <c:set var="emptyList" value="true" scope="page" />
                            <c:forEach items="${currentUser.orders}" var="order">
                            <c:if test="${order.paid}" >
                                <c:set var="emptyList" value="false" />
                                <tr>
                                    <td>${order.numberOfPositions}</td>
                                    <td class="mdl-data-table__cell--non-numeric">${order.createdAt}</td>
                                    <td>${order.price}</td>
                                </tr>
                            </c:if>
                            </c:forEach>
                            <c:if test="${emptyList}">
                                <tr><td><fmt:message key="profile.empty" /><td/><td/></tr>
                            </c:if>
                            </tbody>
                        </table>

                        <br>

                        <h3><fmt:message key="profile.orders.unpaid" /></h3>
                        <table class="mdl-data-table mdl-js-data-table">
                            <thead>
                                <tr class="table-head-row">
                                    <th><fmt:message key="profile.table.positions" /></th>
                                    <th class="mdl-data-table__cell--non-numeric">
                                        <fmt:message key="profile.table.made" />
                                    </th>
                                    <th><fmt:message key="profile.table.price" /></th>
                                    <th><fmt:message key="profile.table.action" /></th>
                                </tr>
                            </thead>

                            <tbody>
                            <c:set var="emptyList" value="true" scope="page" />
                            <c:forEach items="${currentUser.orders}" var="order">
                            <c:if test="${not order.paid}">
                                <c:set var="emptyList" value="false" />
                                <tr>
                                    <td>${order.numberOfPositions}</td>
                                    <td class="mdl-data-table__cell--non-numeric">${order.createdAt}</td>
                                    <td>${order.price}</td>
                                    <td >
                                        <shop:button_for action="home" command="payOrder" model="${order.id}"
                                                         styleClass="mdl-button mdl-js-button mdl-js-ripple-effect
                                                                     mdl-button--colored" >
                                            <i class="material-icons">attach_money</i>
                                        </shop:button_for>
                                    </td>
                                </tr>
                            </c:if>
                            </c:forEach>
                            <c:if test="${emptyList}">
                                <tr><td><fmt:message key="profile.empty" /></td><td/><td/><td/></tr>
                            </c:if>
                            </tbody>
                        </table>
                    </div>
                </div>
            </main>

            <jsp:include page="partials/footer.jsp" />
        </div>
    </body>
</html>