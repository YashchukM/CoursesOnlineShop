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
 * Class that encapsulates business logic.  Provides functionality of adding new product by administrator..
 */
public class AddProductCommand implements Command {
    /**
     * Manager to work with products in persistent context.
     */
    private ProductManager productManager = new ProductManager();

    /**
     * Names of parameters in request.
     */
    private static final String PARAM_PRODUCT_NAME = "name";
    private static final String PARAM_PRODUCT_DESCRIPTION = "description";
    private static final String PARAM_PRODUCT_PRICE = "price";


    @Override
    public Page execute(HttpServletRequest request, HttpServletResponse response) {
        String page = "products.jsp";
        boolean redirect = true;

        String name = request.getParameter(PARAM_PRODUCT_NAME);
        String description = request.getParameter(PARAM_PRODUCT_DESCRIPTION);
        float price = Float.valueOf(request.getParameter(PARAM_PRODUCT_PRICE));

        try {
            productManager.create(name, description, false, price);
        } catch (Exception e) {
            Logger.getLogger(getClass()).error(e);
            page = "index.jsp";
            redirect = false;
            request.setAttribute("message", "Error while adding product. Try again later..");
        }

        return new Page(page, redirect);
    }
}
