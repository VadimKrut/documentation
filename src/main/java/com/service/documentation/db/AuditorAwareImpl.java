package com.service.documentation.db;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuditorAwareImpl implements AuditorAware<Long> {

    @Override
    public Optional<Long> getCurrentAuditor() {
        try {
            return Optional.of((Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}