package org.myas.filter;

import org.apache.log4j.Logger;
import org.myas.entity.User;
import org.myas.helper.Page;
import org.myas.manager.UserManager;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Filter, that only allows users with administrator role to enter admin.jsp page.
 */
@WebFilter(
        value = "/admin.jsp",
        filterName = "AdminFilter"
)
public class AdminFilter implements Filter {
    private UserManager userManager;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        userManager = new UserManager();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        Page nextPage = new Page("admin.jsp", false);

        HttpServletRequest req = (HttpServletRequest) request;
        User user = (User) req.getSession().getAttribute("currentUser");
        if (user == null || !user.isAdmin()) {
            nextPage.setPage("home.jsp");
            nextPage.setRedirected(true);
        } else {
            try {
                List<User> users = userManager.getDefaulters();
                List<User> blacklisted = userManager.getBlacklisted();

                request.setAttribute("users", users);
                request.setAttribute("blacklist", blacklisted);
            } catch (Exception e) {
                Logger.getLogger(getClass()).error(e.getMessage());
                request.setAttribute("message", "Oops..Something went wrong..Please, try again later");
                nextPage.setPage("index.jsp");
            }
        }

        if (nextPage.isRedirected()) {
            HttpServletResponse resp = (HttpServletResponse) response;
            resp.sendRedirect(nextPage.getPage());
        } else {
            RequestDispatcher dispatcher = request.getRequestDispatcher(nextPage.getPage());
            dispatcher.forward(request, response);
        }
    }

    @Override
    public void destroy() {

    }
}
