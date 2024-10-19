package com.service.documentation.util;

import com.service.documentation.exception.ApiException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;

public class CurrentUser {

    public static Long getCurrentUser() {
        Long id = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (id == null) {
            throw new ApiException(HttpServletResponse.SC_UNAUTHORIZED, "Not authorized");
        }
        return id;
    }
}