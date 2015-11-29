<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="shop" uri="/shopTags" %>

<c:set var="language"
       value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}"
       scope="session"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="i18n.text"/>

<html lang="${language}">
<jsp:include page="partials/meta.jsp"/>

<body class="mdl-color--grey-100 mdl-color-text--grey-700 mdl-base">
<div class="mdl-layout mdl-js-layout mdl-layout--fixed-header">
    <jsp:include page="partials/header.jsp"/>

    <main class="mdl-layout__content">
        <c:if test="${currentUser ne null && currentUser.admin}">

            <h2><fmt:message key="products.unavailable"/></h2>

            <div class="mdl-grid">
                <jsp:include page="partials/add_product.jsp"/>
                <c:forEach items="${unavailableProducts}" var="product">
                    <div class="product-card mdl-card mdl-shadow--2dp mdl-cell mdl-cell--3-col">
                        <div class="mdl-card__title mdl-card--expand">
                            <shop:button_for action="home" command="activateProduct" model="${product.id}"
                                             styleClass="mdl-button mdl-js-button mdl-button--fab mdl-js-ripple-effect
                                                         mdl-button--colored">
                                <i class="material-icons">update</i>
                            </shop:button_for>
                        </div>

                        <div class="mdl-card__supporting-text">
                            <span class="title">
                                <fmt:message key="products.name"/>
                            </span><c:out value="${product.name}"/>
                                <br>
                            <span class="title">
                                <fmt:message key="products.description"/>
                            </span><c:out value="${product.description}"/>
                                <br>
                            <span class="title">
                                <fmt:message key="products.price"/>
                            </span><c:out value="${product.price} hrn."/>
                        </div>
                    </div>
                </c:forEach>
            </div>
            <h2><fmt:message key="products.available"/></h2>
        </c:if>

        <div class="product-grid mdl-grid">
            <c:forEach items="${products}" var="product">
                <div class="product-card mdl-card mdl-shadow--2dp mdl-cell mdl-cell--3-col">
                    <div class="mdl-card__title mdl-card--expand">
                        <c:if test="${currentUser ne null}">
                            <shop:button_for action="home" command="addOrderPart" model="${product.id}"
                                             styleClass="mdl-button mdl-js-button mdl-button--fab mdl-js-ripple-effect
                                                         mdl-button--colored">
                                <i class="material-icons">shopping_basket</i>
                            </shop:button_for>
                        </c:if>
                    </div>

                    <div class="mdl-card__supporting-text">
                        <span class="title">
                            <fmt:message key="products.name"/>
                        </span><c:out value="${product.name}"/>
                        <br>
                        <span class="title">
                            <fmt:message key="products.description"/>
                        </span><c:out value="${product.description}"/>
                        <br>
                        <span class="title">
                            <fmt:message key="products.price"/>
                        </span><c:out value="${product.price} hrn."/>
                    </div>
                </div>
            </c:forEach>
        </div>

        <jsp:include page="partials/footer.jsp"/>
    </main>
</div>
</body>
</html>
