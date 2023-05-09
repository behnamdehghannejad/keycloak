package com.example.keycloak.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.session.NullAuthenticatedSessionStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.stereotype.Component;

@EnableMethodSecurity(jsr250Enabled = true)
@EnableWebSecurity
@Component
class SecurityConfig  {

    @Value("${keycloak.client.scope}")
    private String clientScope;

    @Bean
    protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
        return new NullAuthenticatedSessionStrategy();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        grantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");
        grantedAuthoritiesConverter.setAuthoritiesClaimName(clientScope);
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
        return jwtAuthenticationConverter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.
                csrf().disable().
                authorizeHttpRequests()
                .requestMatchers("/public/**", "/swagger-ui/**", "/v3/api-docs/**", "/hook/**", "/**/swagger.json").permitAll()
                /**
                 * can use
                 * @EnableMethodSecurity to control rest endpoints like commented example below
                 * or can use
                 * @EnableMethodSecurity(jsr250Enabled = true) and introduce access role to the endpoint by @RolesAllowed("admin")
                 */
//                .requestMatchers("/admin/**").hasRole( "admin")
//                .requestMatchers("/maker/**").hasRole("maker")
//                .requestMatchers("/taker/**").hasRole("taker")
//                .requestMatchers("/login/**").authenticated()
                .anyRequest().permitAll()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .oauth2ResourceServer()
                .jwt();
        return http.build();
    }


}
