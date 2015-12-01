package org.myas.command;

import org.apache.log4j.Logger;
import org.myas.dao.PersistException;
import org.myas.entity.Order;
import org.myas.entity.OrderPart;
import org.myas.entity.User;
import org.myas.helper.Page;
import org.myas.manager.OrderManager;
import org.myas.manager.ProductManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Class that encapsulates business logic. Provides functionality of making new order by customer.
 */
public class AddOrderCommand implements Command {
    /**
     * Manager to work with orders in persistent context.
     */
    private OrderManager orderManager = new OrderManager();

    @Override
    public Page execute(HttpServletRequest request, HttpServletResponse response) {
        String page = "profile.jsp";
        boolean redirect = true;

        Order order = (Order) request.getSession().getAttribute("currentOrder");
        try {
            User currentUser = (User) request.getSession().getAttribute("currentUser");
            order = orderManager.create(order);
            order.setUser(currentUser);
            currentUser.getOrders().add(order);

            /*
             * Invalidate current order in session.
             */
            request.getSession().setAttribute("currentOrder", null);
        } catch (PersistException e) {
            Logger.getLogger(getClass()).error(e);
            page = "index.jsp";
            redirect = false;
            request.setAttribute("message", "Error while making order. Try again later..");
        }

        return new Page(page, redirect);
    }
}
