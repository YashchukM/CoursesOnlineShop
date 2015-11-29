<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="language"
    value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}"
    scope="session"/>
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="i18n.text" />

<header class="mdl-layout__header mdl-color--transparent">
    <div class="mdl-layout__header-row">
        <!-- Title -->
        <a class="mdl-navigation__link" href="../home.jsp">MyShop</a>
        <!-- Add spacer, to align navigation to the right -->
        <div class="mdl-layout-spacer"></div>

        <!-- Navigation -->
        <nav class="mdl-navigation">
            <a class="mdl-navigation__link" href="../home.jsp">
                <fmt:message key="navigation.home" />
            </a>

            <c:if test="${currentUser eq null}">
                <a class="mdl-navigation__link" href="../login.jsp">
                    <fmt:message key="navigation.login" />
                </a>
                <a class="mdl-navigation__link" href="../register.jsp">
                    <fmt:message key="navigation.register" />
                </a>
            </c:if>

            <c:if test="${currentUser ne null && currentUser.admin}">
                <a class="mdl-navigation__link" href="../admin.jsp"><fmt:message key="navigation.manage" /></a>
            </c:if>

            <a class="mdl-navigation__link" href="../products.jsp"><fmt:message key="navigation.products" /></a>

            <c:if test="${currentUser ne null}">
                <c:if test="${currentOrder ne null}">
                    <a class="mdl-navigation__link" href="../basket.jsp">
                        <span class="mdl-badge" data-badge="${currentOrder.numberOfPositions}">
                            <fmt:message key="navigation.basket" />
                        </span>
                    </a>
                </c:if>

                <c:if test="${currentOrder eq null}">
                    <a class="mdl-navigation__link" href="../basket.jsp" ">
                        <span class="mdl-badge" data-badge="0">
                            <fmt:message key="navigation.basket" />
                        </span>
                    </a>
                </c:if>
            </c:if>
        </nav>

        <!-- Right aligned menu below button -->
        <c:if test="${currentUser ne null}">
            <button id="demo-menu-lower-right"
                    class="mdl-button mdl-js-button mdl-button--icon">
                <i class="material-icons">more_vert</i>
            </button>

            <ul class="mdl-menu mdl-menu--bottom-right mdl-js-menu mdl-js-ripple-effect"
                for="demo-menu-lower-right">
                <a class="mdl-menu__item" href="../profile.jsp"><fmt:message key="navigation.profile" /></a>

                <form style="padding: 0; margin: 0;" action="home">
                    <input type="hidden" name="command" value="logout">
                    <input style="width: 100%;" class="mdl-menu__item"
                           value="<fmt:message key="navigation.logout" />"
                           type="submit" />
                </form>
            </ul>
        </c:if>
    </div>
</header>