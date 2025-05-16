package filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.User;

import java.io.IOException;

@WebFilter("/admin/*")
public class AdminAuthFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);

        boolean isLoggedIn = (session != null && session.getAttribute("user") != null);
        boolean isAdmin = false;

        if (isLoggedIn) {
            User user = (User) session.getAttribute("user");
            isAdmin = "admin".equalsIgnoreCase(user.getRole());
        }

        if (!isLoggedIn || !isAdmin) {
            // Redirect to login page if not logged in or not admin
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login.jsp");
            return;
        }

        // User is logged in and is admin, continue with the request
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
} 