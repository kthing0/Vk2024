package utils;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import user.User;

import java.io.IOException;

@WebFilter("/api/*")
public class RoleFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        User currentUser = (User) httpRequest.getSession().getAttribute("user");
        if (currentUser == null) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login.jsp");
            return;
        }

        String[] userRoles = currentUser.getRoles();

        String requestURI = httpRequest.getRequestURI();
        if (hasRole(userRoles, "ROLE_ADMIN")) {
            chain.doFilter(request, response);
        } else if (requestURI.startsWith("/api/posts/") && hasRole(userRoles, "ROLE_POSTS_VIEWER")) {
            chain.doFilter(request, response);
        } else if (requestURI.startsWith("/api/posts/") && hasRole(userRoles, "ROLE_POSTS")) {
            chain.doFilter(request, response);
        } else if (requestURI.startsWith("/api/users/") && hasRole(userRoles, "ROLE_USERS")) {
            chain.doFilter(request, response);
        } else if (requestURI.startsWith("/api/albums/") && hasRole(userRoles, "ROLE_ALBUMS")) {
            chain.doFilter(request, response);
        } else {
            httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
    }

    private boolean hasRole(String[] userRoles, String requiredRole) {
        for (String role : userRoles) {
            if (role.equals(requiredRole)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }
}
