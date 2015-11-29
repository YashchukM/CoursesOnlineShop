<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="language"
    value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}"
    scope="session"/>
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="i18n.text" />

<footer class="mdl-mini-footer">
    <div class="mdl-mini-footer--bottom-section">
        <div class="mdl-logo">
            MyShop
        </div>
        <ul class="mdl-mini-footer__link-list">
            <li>
                <form>
                    <label class="mdl-radio mdl-js-radio mdl-js-ripple-effect" for="option-1">
                        <input type="radio" id="option-1" class="mdl-radio__button" onchange="submit()"
                               name="language" value="en" ${language == 'en' ? 'checked' : ''}>
                        <span class="mdl-radio__label">EN</span>
                    </label>
                    <label class="mdl-radio mdl-js-radio mdl-js-ripple-effect" for="option-2">
                        <input type="radio" id="option-2" class="mdl-radio__button" onchange="submit()"
                               name="language" value="ua" ${language == 'ua' ? 'checked' : ''}>
                        <span class="mdl-radio__label">UA</span>
                    </label>
                </form>
            </li>
            <li>
                <a href="https://www.facebook.com/profile.php?id=100010481281269">
                    <fmt:message key="footer.created" /> <fmt:message key="footer.creator" />
                </a>
            </li>
        </ul>
    </div>
</footer>
