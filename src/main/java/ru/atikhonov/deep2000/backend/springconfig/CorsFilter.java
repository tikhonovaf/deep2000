package ru.atikhonov.deep2000.backend.springconfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.atikhonov.deep2000.backend.model.Demo2000User;
import ru.atikhonov.deep2000.backend.service.UserService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CorsFilter extends OncePerRequestFilter {
    private static final Logger log = LoggerFactory.getLogger(CorsFilter.class);

    private UserService userService;

    public CorsFilter(
            UserService userService
    ) {
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "*");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "*");
        response.addHeader("Access-Control-Expose-Headers", "*");

        response.addHeader("Access-Control-Allow-Credentials", "true");

        if ("OPTIONS".equals(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else if (checkAccess(request)) {
            filterChain.doFilter(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied");
        }

    }

    private boolean checkAccess(HttpServletRequest request) {
        String service = request.getRequestURI();

        if (
                service.startsWith("/api/login") ||
                        service.startsWith("/api/logout") ||
                        service.startsWith("/api/session") ||

                        //service.startsWith("/swagger-ui.html") ||
                        //service.startsWith("/swagger-resources")
                        //service.startsWith("/webjars") ||
                        service.contains("swagger-ui") ||
                        service.contains("swagger-resources") ||
                        service.contains("swagger-config") ||
                        service.contains("webjars") ||
                        service.contains("v3/api-docs") ||
                        service.startsWith("/ws")
        ) {
            return true;
        }

        String role = null;
        Demo2000User user = userService.getCurrentUser();
        if (user != null) {
            role = user.getRole().getName();
        }

        return true;
    }
}
