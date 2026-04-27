package com.ihl95.nuclear.security.config;

import com.ihl95.nuclear.security.JwtRequestFilter;
import com.ihl95.nuclear.user.CustomUserDetailsService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.core.env.Environment;

@Configuration
@EnableWebSecurity
public class SecurityConfigurer {

    private final JwtRequestFilter jwtRequestFilter;
    private final CustomUserDetailsService customUserDetailsService;
    private final Environment environment;

    public SecurityConfigurer(JwtRequestFilter jwtRequestFilter, CustomUserDetailsService customUserDetailsService, Environment environment) {
        this.jwtRequestFilter = jwtRequestFilter;
        this.customUserDetailsService = customUserDetailsService;
        this.environment = environment;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Para cifrar las contraseñas
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(customUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // En ambiente de test, permitir acceso a todos los endpoints sin autenticación
        boolean isTestProfile = isTestProfile();

        // CSRF Protection Strategy:
        // - Test Profile: CSRF disabled (test endpoints don't require it)
        // - Production Profile: CSRF enabled via default Spring Security (recommended for stateful operations)
        // - JWT is stateless, so CSRF is less critical, but enabled by default for defense-in-depth
        if (isTestProfile) {
            // CSRF is safe to disable in test profile because:
            // 1. Application uses JWT authentication (stateless, not prone to CSRF)
            // 2. Tests are not user-initiated HTTP requests from browsers
            // 3. Test profile has relaxed security constraints
            // 4. Production profile keeps CSRF enabled by default for defense-in-depth
            http.csrf().disable(); // NOSONAR: Justified per conditions above
        }
        // In production, CSRF is enabled by default (do not disable)

        if (isTestProfile) {
            // Test mode: allow all requests without authentication
            http.authorizeRequests()
                    .anyRequest().permitAll();
        } else {
            // Production mode: require JWT authentication
            http.authorizeRequests()
                    .antMatchers("/api/auth/authenticate").permitAll()
                    .antMatchers("/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**", "/webjars/**").permitAll()
                    .anyRequest().authenticated()
                    .and()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

            http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        }

        return http.build();
    }

    private boolean isTestProfile() {
        String[] activeProfiles = environment.getActiveProfiles();
        for (String profile : activeProfiles) {
            if ("test".equals(profile)) {
                return true;
            }
        }
        return false;
    }
}
