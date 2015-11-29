package org.myas.command;

import org.apache.log4j.Logger;
import org.myas.entity.Order;
import org.myas.entity.Product;
import org.myas.entity.User;
import org.myas.helper.Page;
import org.myas.manager.OrderManager;
import org.myas.manager.ProductManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Class that encapsulates business logic. Makes specified product available to customers.
 */
public class ActivateProductCommand implements Command {
    /**
     * Manager to work with products in persistent context.
     */
    private ProductManager productManager = new ProductManager();

    /**
     * Name of parameter in request. Identifier of product.
     */
    private static final String PARAM_PRODUCT_ID = "id";

    @Override
    public Page execute(HttpServletRequest request, HttpServletResponse response) {
        String page = "products.jsp";
        boolean redirect = true;

        Integer productId = Integer.valueOf(request.getParameter(PARAM_PRODUCT_ID));
        try {
            Product product = productManager.get(productId);
            product.setAvailable(true);
            productManager.update(product);
        } catch (Exception e) {
            Logger.getLogger(getClass()).error(e);
            page = "index.jsp";
            redirect = false;
            request.setAttribute("message", "Error while making order. Try again later..");
        }

        return new Page(page, redirect);
    }
}
