package org.myas.command;

import org.apache.log4j.Logger;
import org.myas.entity.User;
import org.myas.helper.Page;
import org.myas.manager.UserManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Class that encapsulates business logic. Provides functionality of logging user into system.
 */
public class LoginCommand implements Command {
    /**
     * Names of parameters in request.
     */
    private static final String PARAM_EMAIL = "email";
    private static final String PARAM_PASSWORD = "password";

    /**
     * Manager to work with users in persistent context.
     */
    private UserManager userManager = new UserManager();

    @Override
    public Page execute(HttpServletRequest request, HttpServletResponse response) {
        String page = "index.jsp";
        boolean redirect = false;

        /*
         * Get user email and password from the request.
         */
        String email = request.getParameter(PARAM_EMAIL);
        String password = request.getParameter(PARAM_PASSWORD);

        String error = "Login error. ";
        try {
            User user = userManager.login(email, password);
            if (user != null) {                                             /* Logged in */
                user.setPassword(null);                                     /* Make password null to avoid data leak */
                request.getSession().setAttribute("currentUser", user);
                if (user.isAdmin()) {                                       /* User has admin role */
                    page = "admin.jsp";
                    redirect = true;
                } else {                                                    /* User has customer role */
                    page = "products.jsp";
                    redirect = true;
                }
                Logger.getLogger(this.getClass()).info("User: " + user.getFirstName() + " " +
                    user.getSecondName() + "(id: " + user.getId() + ") has successfully logged in.");
            } else {                                                        /* Not logged in */
                error += "Check your password and email. ";
                request.setAttribute("message", error);
            }
        } catch (Exception e) {
            Logger.getLogger(this.getClass()).error(e.getMessage());
            error += "Internal problems, try again later. ";
            request.setAttribute("message", error);
        }

        return new Page(page, redirect);
    }
}
