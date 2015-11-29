<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="shop" uri="/shopTags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:set var="language"
    value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}"
    scope="session"/>
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="i18n.text" />

<html lang="${language}">
    <jsp:include page="partials/meta.jsp" />

    <body class="mdl-demo mdl-color--grey-100 mdl-color-text--grey-700 mdl-base">
        <div class="mdl-layout mdl-js-layout mdl-layout--fixed-header">
            <jsp:include page="partials/header.jsp" />

            <main class="mdl-layout__content">
                <div class="mdl-grid">
                    <div class="shop-inp__form-card mdl-card mdl-shadow--2dp mdl-cell mdl-cell--6-col">
                        <h3><fmt:message key="admin.heading" /></h3>
                        <table class="mdl-data-table mdl-js-data-table">
                            <thead>
                                <tr class="table-head-row">
                                    <th class="mdl-data-table__cell--non-numeric">
                                        <fmt:message key="admin.table.name"/>
                                    </th>
                                    <th class="mdl-data-table__cell--non-numeric">
                                        <fmt:message key="admin.table.email" />
                                    </th>
                                    <th><fmt:message key="admin.table.paid" /></th>
                                    <th><fmt:message key="admin.table.pending" /></th>
                                    <th><fmt:message key="admin.table.action" /></th>
                                </tr>
                            </thead>

                            <tbody>
                            <c:forEach items="${users}" var="user">
                                <tr>
                                    <td class="mdl-data-table__cell--non-numeric">
                                            ${user.firstName} ${user.secondName}
                                    </td>
                                    <td class="mdl-data-table__cell--non-numeric">${user.email}</td>
                                    <td>${user.numOfPaidOrders}</td>
                                    <td>${user.numOfPendingOrders}</td>
                                    <td >
                                        <shop:button_for action="home" command="blacklistUser" model="${user.id}"
                                                         styleClass="mdl-button mdl-js-button mdl-js-ripple-effect
                                                        mdl-button--colored" >
                                            <fmt:message key="admin.button" />
                                        </shop:button_for>
                                    </td>
                                </tr>
                            </c:forEach>

                            <c:if test="${fn:length(users) eq 0}">
                                <tr>
                                    <td><fmt:message key="profile.empty" /></td><td><td><td><td>
                                </tr>
                            </c:if>
                            </tbody>
                        </table>
                    </div>

                    <div class="shop-inp__form-card mdl-card mdl-shadow--2dp mdl-cell mdl-cell--5-col">
                        <h3><fmt:message key="admin.blacklist" /></h3>
                        <table class="mdl-data-table mdl-js-data-table">
                            <thead>
                            <tr class="table-head-row">
                                <th class="mdl-data-table__cell--non-numeric">
                                    <fmt:message key="admin.table.name"/>
                                </th>
                                <th class="mdl-data-table__cell--non-numeric">
                                    <fmt:message key="admin.table.email" />
                                </th>
                                <th><fmt:message key="admin.table.paid" /></th>
                                <th><fmt:message key="admin.table.pending" /></th>
                            </tr>
                            </thead>

                            <tbody>
                            <c:forEach items="${blacklist}" var="user">
                                <tr>
                                    <td class="mdl-data-table__cell--non-numeric">
                                            ${user.firstName} ${user.secondName}
                                    </td>
                                    <td class="mdl-data-table__cell--non-numeric">${user.email}</td>
                                    <td>${user.numOfPaidOrders}</td>
                                    <td>${user.numOfPendingOrders}</td>
                                </tr>
                            </c:forEach>

                            <c:if test="${fn:length(blacklist) eq 0}">
                                <tr>
                                    <td><fmt:message key="profile.empty" /></td><td><td><td>
                                </tr>
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