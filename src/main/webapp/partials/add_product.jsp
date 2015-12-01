<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="language"
       value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}"
       scope="session"/>
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="i18n.text" />

<div class="add-product-card mdl-card mdl-shadow--2dp mdl-cell mdl-cell--3-col">
    <div class="shop-inp__form">
        <form action="home" method="post">
            <input type="hidden" name="command" value="addProduct">

            <div class="mdl-textfield mdl-js-textfield">
                <label for="prod_name" class="mdl-textfield__label"><fmt:message key="products.name" /></label>
                <input type="text" class="mdl-textfield__input" id="prod_name" name="name" required>
            </div>

            <div class="mdl-textfield mdl-js-textfield">
                <label for="prod_desc" class="mdl-textfield__label"><fmt:message key="products.description" /></label>
                <textarea type="text" class="mdl-textfield__input" id="prod_desc" name="description" required ></textarea>
            </div>

            <div class="mdl-textfield mdl-js-textfield">
                <label for="prod_price" class="mdl-textfield__label"><fmt:message key="products.price" /></label>
                <input type="text" class="mdl-textfield__input" id="prod_price" name="price" required>
            </div>

            <div class="mdl-textfield mdl-js-textfield">
                <label for="prod_image" class="mdl-textfield__label"><fmt:message key="products.image" /></label>
                <input type="text" class="mdl-textfield__input" id="prod_image" name="image" required>
            </div>

            <br>

            <button type="submit"
                    class="shop-form-btn mdl-button mdl-js-button mdl-button--raised mdl-button--colored">
                Add
            </button>
        </form>
    </div>
</div>