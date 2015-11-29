package org.myas.command;

import org.myas.helper.Page;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Interface of business logic implementing classes.
 */
public interface Command {
    /**
     * Executes piece of business logic.
     * @param request request from servlet.
     * @param response response from servlet.
     * @return next opened in browser view page and method of showing it.
     * @throws ServletException
     * @throws IOException
     */
    public Page execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException;
}
