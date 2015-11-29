<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="language"
       value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}"
       scope="session"/>
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="i18n.text" />

<html>
    <jsp:include page="partials/meta.jsp" />

    <body class="mdl-color--grey-100 mdl-color-text--grey-700 mdl-base">
        <div class="mdl-layout mdl-js-layout mdl-layout--fixed-header">
            <jsp:include page="partials/header.jsp" />

            <main class="mdl-layout__content">
                <div class="mdl-grid">
                    <div class="shop-inp__form-card mdl-card mdl-shadow--2dp mdl-cell mdl-cell--5-col">
                        <div class="shop-inp__form">
                            <form action="home" method="post">
                                <input type="hidden" name="command" value="register">

                                <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
                                    <label for="inp-fir-name" class="mdl-textfield__label">
                                        <fmt:message key="form.name.first" />
                                    </label>
                                    <input type="text" class="mdl-textfield__input" id="inp-fir-name"
                                           name="firstName" required>
                                </div>
                                <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
                                    <label for="inp-las-name" class="mdl-textfield__label">
                                        <fmt:message key="form.name.last" />
                                    </label>
                                    <input type="text" class="mdl-textfield__input" id="inp-las-name"
                                           name="lastName" required>
                                </div>
                                <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
                                    <label for="reg-inp-email" class="mdl-textfield__label">
                                        <fmt:message key="form.email" />
                                    </label>
                                    <input type="email" class="mdl-textfield__input" id="reg-inp-email"
                                           name="email" required>
                                </div>
                                <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
                                    <label for="reg-inp-pas" class="mdl-textfield__label">
                                        <fmt:message key="form.password" />
                                    </label>
                                    <input type="password" class="mdl-textfield__input" id="reg-inp-pas"
                                           name="password" required>
                                </div>
                                <br>

                                <button type="submit"
                                        class="shop-form-btn mdl-button mdl-js-button mdl-button--raised
                                               mdl-button--colored">
                                    <fmt:message key="form.button.register" />
                                </button>
                            </form>
                        </div>
                    </div>
                </div>
            </main>

            <jsp:include page="partials/footer.jsp" />
        </div>
    </body>
</html>