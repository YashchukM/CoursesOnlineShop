package org.myas.command;

import org.apache.log4j.Logger;
import org.myas.entity.Product;
import org.myas.helper.Page;
import org.myas.manager.ProductManager;
import org.myas.manager.UserManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Class that encapsulates business logic. Provides functionality of adding user to blacklist.
 */
public class BlacklistUserCommand implements Command {
    /**
     * Manager to work with products in persistent context.
     */
    private UserManager userManager = new UserManager();

    /**
     * Name of parameter in request. Identifier of user.
     */
    private static final String PARAM_PRODUCT_ID = "id";

    @Override
    public Page execute(HttpServletRequest request, HttpServletResponse response) {
        String page = "admin.jsp";
        boolean redirect = true;

        Integer userId = Integer.valueOf(request.getParameter(PARAM_PRODUCT_ID));
        try {
            userManager.setBlacklisted(userId, true);
        } catch (Exception e) {
            Logger.getLogger(getClass()).error(e);
            page = "index.jsp";
            redirect = false;
            request.setAttribute("message", "Error while adding user to blacklist..");
        }

        return new Page(page, redirect);
    }
}
