package org.myas.command;

import org.myas.helper.Page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Class that encapsulates business logic. Is chosen, when some wrong command passed to servlet.
 */
public class NoCommand implements Command {
    @Override
    public Page execute(HttpServletRequest request, HttpServletResponse response) {
        /*
         * Invalidate session.
         */
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        /*
         * Redirect to login page.
         */
        String page = "login.jsp";
        boolean redirect = true;

        return new Page(page, redirect);
    }
}
