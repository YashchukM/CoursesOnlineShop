package org.myas.filter;


import org.myas.entity.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Filter that prevents user that is no logged in from opening pages, designed for logged in users.
 */
@WebFilter(
        urlPatterns = {"/basket.jsp", "/profile.jsp", "/partials/*"},
        filterName = "AnonymFilter"
)
public class AnonymFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        User user = (User) req.getSession().getAttribute("currentUser");
        if (user == null) {
            HttpServletResponse resp = (HttpServletResponse) response;

            // Requested page is in the same folder with home.jsp
            if (req.getRequestURL().indexOf("/partials/") == -1) {
                resp.sendRedirect("/home.jsp");
            } else { // Requested page is in partials/ folder
                resp.sendRedirect("../home.jsp");
            }
        } else {
            RequestDispatcher dispatcher = request.getRequestDispatcher(req.getRequestURI());
            dispatcher.forward(request, response);
        }
    }

    @Override
    public void destroy() {}
}
