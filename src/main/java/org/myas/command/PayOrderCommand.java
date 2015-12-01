package org.myas.command;

import org.apache.log4j.Logger;
import org.myas.dao.PersistException;
import org.myas.entity.Order;
import org.myas.entity.User;
import org.myas.helper.Page;
import org.myas.manager.OrderManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Class that encapsulates business logic. Provides functionality of paying order by customer.
 */
public class PayOrderCommand implements Command {
    /**
     * Name of parameter in request. Identifier of order.
     */
    private static final String PARAM_ORDER_ID = "id";

    /**
     * Manager to work with orders in persistent context.
     */
    private OrderManager orderManager = new OrderManager();

    @Override
    public Page execute(HttpServletRequest request, HttpServletResponse response) {
        String page = "profile.jsp";
        boolean redirect = true;

        int orderId = Integer.valueOf(request.getParameter(PARAM_ORDER_ID));

        User user = (User) request.getSession().getAttribute("currentUser");
        try {
            for (int i = 0; i < user.getOrders().size(); i++) {
                Order cur = user.getOrders().get(i);
                if (cur.getId() == orderId) {
                    cur.setPaid(true);
                    orderManager.update(cur);
                }
            }
        } catch (PersistException e) {
            Logger.getLogger(getClass()).error(e);
            page = "index.jsp";
            redirect = false;
            request.setAttribute("message", "Error while paying. Try again later..");
        }

        return new Page(page, redirect);
    }
}
