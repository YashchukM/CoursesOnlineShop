<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
                    <div class="shop-inp__form-card mdl-card mdl-shadow--2dp mdl-cell mdl-cell--9-col">
                        <c:if test="${currentOrder.orderParts ne null && currentOrder.numberOfPositions ne 0}">
                            <table class="mdl-data-table mdl-js-data-table">
                                <thead>
                                <tr class="table-head-row">
                                    <th class="mdl-data-table__cell--non-numeric">
                                        <fmt:message key="basket.table.product" />
                                    </th>
                                    <th class="mdl-data-table__cell--non-numeric">
                                        <fmt:message key="basket.table.description" />
                                    </th>
                                    <th><fmt:message key="basket.table.number" /></th>
                                    <th><fmt:message key="basket.table.price" /></th>
                                    <th><fmt:message key="basket.table.action" /></th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:set var="cur" value="0" scope="page" />
                                <c:forEach items="${currentOrder.orderParts}" var="orderPart">
                                    <tr>
                                        <td class="mdl-data-table__cell--non-numeric">${orderPart.product.name}</td>
                                        <td class="mdl-data-table__cell--non-numeric">${orderPart.product.description}</td>
                                        <td>${orderPart.number}</td>
                                        <td>${orderPart.product.price} hrn.</td>
                                        <td>
                                            <shop:button_for action="home" command="deleteOrderPart" model="${cur}"
                                                             styleClass="mdl-button mdl-js-button mdl-js-ripple-effect
                                                                     mdl-button--colored" >
                                                <i class="material-icons">highlight_off</i>
                                            </shop:button_for>
                                        </td>
                                    </tr>
                                    <c:set var="cur" value="${cur + 1}" />
                                </c:forEach>
                                </tbody>
                            </table>

                            <form action="home" method="post">
                                <input type="hidden" name="command" value="addOrder">
                                <button class="mdl-button mdl-js-button mdl-button--raised mdl-button--colored">
                                    <fmt:message key="basket.button.make" />
                                </button>
                                <span class="basket-total" >
                                    <fmt:message key="basket.total" /> <c:out value="${currentOrder.price}" />
                                </span>
                            </form>
                        </c:if>

                        <c:if test="${currentOrder.orderParts eq null || currentOrder.numberOfPositions eq 0}">
                            <h3><fmt:message key="profile.empty"/></h3>
                        </c:if>
                    </div>
                </div>
            </main>

            <jsp:include page="partials/footer.jsp" />
        </div>
    </body>
</html>