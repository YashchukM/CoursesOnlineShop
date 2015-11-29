package org.myas.controller;

import org.apache.log4j.Logger;
import org.myas.command.Command;
import org.myas.helper.Page;
import org.myas.helper.RequestHelper;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Main servlet, that controls and manages all actions in webapp.
 */
@WebServlet(value = "/home",
            name  = "ShopController")
public class ShopController extends HttpServlet {
    private RequestHelper requestHelper = RequestHelper.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    /**
     * Helper method, determines next actions from request data.
     * @param request request from a view.
     * @param response response from servlet.
     */
    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        Page nextPage = new Page("home.jsp", false);

        /*
         * Determine what piece of business logic need to be executed.
         */
        try {
            Command command = requestHelper.getCommand(request);
            nextPage = command.execute(request, response);
        } catch (ServletException e) {
            Logger.getLogger(this.getClass()).error(e.getMessage());
        } catch (IOException e) {
            Logger.getLogger(this.getClass()).error(e.getMessage());
        }


        if (nextPage.isRedirected()) {                                                          /* redirect */
            response.sendRedirect(nextPage.getPage());
        } else {                                                                                /* forward */
            RequestDispatcher dispatcher = request.getRequestDispatcher(nextPage.getPage());
            dispatcher.forward(request, response);
        }
    }
}
