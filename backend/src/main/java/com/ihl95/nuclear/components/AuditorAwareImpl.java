package com.ihl95.nuclear.components;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        // Retorna el usuario actual, por ejemplo, del contexto de seguridad de Spring
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication().getName());
    }
}
