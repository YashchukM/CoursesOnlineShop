package org.myas.filter;


import org.apache.log4j.Logger;
import org.myas.entity.Product;
import org.myas.entity.User;
import org.myas.helper.Page;
import org.myas.manager.ProductManager;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;


/**
 * Filter, that loads and attaches products list to request before entering products page
 */
@WebFilter(
        urlPatterns = {"/products.jsp"},
        filterName = "ProductsFilter"
)
public class ProductsFilter implements Filter {
    private ProductManager productManager;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        productManager = new ProductManager();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        Page nextPage = new Page("products.jsp", false);

        HttpServletRequest req = (HttpServletRequest) request;
        User currentUser = (User) req.getSession().getAttribute("currentUser"); // Get logged in user from session

        if (currentUser != null && currentUser.isAdmin()) {
            try {
                List<Product> unavProducts = productManager.getUnavailable();
                request.setAttribute("unavailableProducts", unavProducts);
            } catch (Exception e) {
                Logger.getLogger(getClass()).error(e.getMessage());
                request.setAttribute("message", "Oops..Something went wrong..Please, try again later");
                nextPage.setPage("index.jsp");
            }
        }

        try {
            List<Product> products = productManager.getAvailable();
            request.setAttribute("products", products);
        } catch (Exception e) {
            Logger.getLogger(getClass()).error(e.getMessage());
            request.setAttribute("message", "Oops..Something went wrong..Please, try again later");
            nextPage.setPage("index.jsp");
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher(nextPage.getPage());
        dispatcher.forward(request, response);
    }

    @Override
    public void destroy() {

    }
}
