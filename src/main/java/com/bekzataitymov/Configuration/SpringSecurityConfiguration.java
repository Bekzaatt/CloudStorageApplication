package com.bekzataitymov.Configuration;


import com.bekzataitymov.Filter.SessionFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfiguration {
    @Autowired
    private SessionFilter sessionFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(customizer ->
                        customizer.disable())
                .authorizeHttpRequests(req -> req
                        .requestMatchers("/api/auth/sign-up", "/api/auth/sign-in", "/api/auth/sign-out", "/",
                                "/user/me",
                                "/v3/api-docs/**",
                                "/swagger-resources/**",
                                "/swagger-ui/**",
                                "/webjars/**",
                                "/swagger-ui.html").permitAll()
                        .anyRequest().authenticated())
                .sessionManagement(http -> http.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
                .addFilterBefore(sessionFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(s -> s.maximumSessions(3).maxSessionsPreventsLogin(false));

        return httpSecurity.build();
    }

}