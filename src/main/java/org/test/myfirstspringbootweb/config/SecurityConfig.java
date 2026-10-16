
package org.test.myfirstspringbootweb.config;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Primary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    @Primary
    public Converter<Map<String, Object>,
            Collection<? extends GrantedAuthority>> customAuthoritiesConverter(
            @Value("${app.security.admin-email}") String adminEmail) {

        return claims -> {
            Object emailClaim = claims.get("email");
            Object emailVerifiedClaim = claims.get("email_verified");

            if (!(emailClaim instanceof String email)
                    || !Boolean.TRUE.equals(emailVerifiedClaim)) {
                return List.of();
            }

            if (email.equalsIgnoreCase(adminEmail.trim())) {
                return List.of(
                        new SimpleGrantedAuthority("ROLE_ADMIN"));
            }

            return List.of(
                    new SimpleGrantedAuthority("ROLE_USER"));
        };
    }
}