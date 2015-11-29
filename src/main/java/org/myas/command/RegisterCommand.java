package org.myas.command;

import org.apache.log4j.Logger;
import org.myas.entity.User;
import org.myas.helper.Page;
import org.myas.manager.UserManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Class that encapsulates business logic. Provides functionality of registering user in web system.
 */
public class RegisterCommand implements Command {
    /**
     * Names of parameter in request.
     */
    private static final String PARAM_EMAIL = "email";
    private static final String PARAM_PASSWORD = "password";
    private static final String PARAM_FIRST_NAME = "firstName";
    private static final String PARAM_LAST_NAME = "lastName";

    /**
     * Manager to work with users in persistent context.
     */
    private UserManager userManager = new UserManager();

    @Override
    public Page execute(HttpServletRequest request, HttpServletResponse response) {
        String page;
        boolean redirect = false;

        String email = request.getParameter(PARAM_EMAIL);
        String password = request.getParameter(PARAM_PASSWORD);
        String firstName = request.getParameter(PARAM_FIRST_NAME);
        String secondName = request.getParameter(PARAM_LAST_NAME);

        String error = "Registration error. ";
        try {
            User user = userManager.get(email);
            if (user == null) {                                 /* Email is unique */
                User newUser = userManager.create(firstName, secondName, email, password);
                if (newUser.getId() != null) {                  /* Successfully saved to database */
                    request.setAttribute("message", "Registration successful!");
                    page = "index.jsp";
                    return new Page(page, redirect);
                } else {                                        /* Not saved to database */
                    error += "Internal problems, try again later. ";
                }
            } else {                                            /* User with such email already exists */
                error += "User with such login already exists. ";
            }
        } catch (Exception e) {
            Logger.getLogger(this.getClass()).error(e.getMessage());
            error += "Internal problems, try again later. ";
        }

        request.setAttribute("message", error);
        page = "index.jsp";
        return new Page(page, redirect);
    }
}
