package org.myas.command;

import org.myas.entity.Order;
import org.myas.entity.OrderPart;
import org.myas.helper.Page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Class that encapsulates business logic. Provides functionality of deleting order part from order.
 */
public class DeleteOrderPartCommand implements Command {
    /**
     * Name of parameter in request. Identifier of order part.
     */
    private static final String PARAM_ORDER_PART = "id";

    @Override
    public Page execute(HttpServletRequest request, HttpServletResponse response) {
        String page = "basket.jsp";
        boolean redirect = true;

        int orderPartPos = Integer.valueOf(request.getParameter(PARAM_ORDER_PART));

        Order order = (Order) request.getSession().getAttribute("currentOrder");
        OrderPart op = order.getOrderParts().get(orderPartPos);

        /*
         * Reduce total price of order.
         */
        order.setPrice(order.getPrice() -  op.getNumber() * op.getProduct().getPrice());
        order.getOrderParts().remove(orderPartPos);

        return new Page(page, redirect);
    }
}
