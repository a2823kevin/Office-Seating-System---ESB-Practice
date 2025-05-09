package com.a2823kevin.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(requests->requests
                .anyRequest().permitAll()
            )
            // xss protection
            .headers(headers -> headers
                .contentSecurityPolicy(csp -> csp
                    .policyDirectives(
                        // All resources can only be loaded from this site.
                        "default-src 'self';" +
                        // Prohibit any external JS (including CDNs), only allow JS from this site.
                        "script-src 'self';" +
                        // Prohibit the use of <object>, Flash, and Java applets.
                        "object-src 'none'" +
                        // Prevent the <base> tag from changing relative path resolution behavior, ensuring all relative links resolve to this site.
                        "base-uri 'self'" +
                        // Prevent form submissions to third-party websites to guard against cross-site request forgery (CSRF) attacks.
                        "form-action 'self'"
                    )
                )
            );

        return http.build();
    }
}