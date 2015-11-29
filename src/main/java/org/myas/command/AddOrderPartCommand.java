package org.myas.command;

import org.apache.log4j.Logger;
import org.myas.entity.Order;
import org.myas.entity.OrderPart;
import org.myas.entity.Product;
import org.myas.entity.User;
import org.myas.helper.Page;
import org.myas.manager.ProductManager;
import org.myas.manager.UserManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that encapsulates business logic. Provides functionality of adding new order part by customer.
 */
public class AddOrderPartCommand implements Command {
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

        Order order = (Order) request.getSession().getAttribute("currentOrder");
        User user = (User) request.getSession().getAttribute("currentUser");

        if (order == null) {
            order = new Order();
            order.setUser(user);
            order.setPaid(false);
        }

        /*
         * If product already exists in order, increment it's number.
         */
        boolean productFound = false;
        for (OrderPart op : order.getOrderParts()) {
            if (op.getProduct().getId().equals(productId)) {
                productFound = true;
                op.setNumber(op.getNumber() + 1);
                order.setPrice(order.getPrice() + op.getProduct().getPrice());
            }
        }

        /*
         * If product is new, create new order part and add it to order.
         */
        if (!productFound) {
            try {
                OrderPart newPart = new OrderPart();
                newPart.setNumber(1);
                newPart.setProduct(productManager.get(productId));
                newPart.setOrder(order);

                order.addOrderPart(newPart);
                request.getSession().setAttribute("currentOrder", order);
            } catch (Exception e) {
                Logger.getLogger(this.getClass()).error(e.getMessage());
                String error = "Error while adding product to order. Try again later. ";
                request.setAttribute("message", error);
                page = "index.jsp";
            }

        }

        return new Page(page, redirect);
    }
}
