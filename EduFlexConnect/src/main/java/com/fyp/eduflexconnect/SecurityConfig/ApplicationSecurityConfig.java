package com.fyp.eduflexconnect.SecurityConfig;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import java.util.Arrays; import java.util.Collections;
@Configuration @EnableWebSecurity
public class ApplicationSecurityConfig {
    @Bean public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        (
                (HttpSecurity)
                        ((HttpSecurity)((HttpSecurity)
                                ((HttpSecurity)
                                        ((AuthorizeHttpRequestsConfigurer.AuthorizedUrl)((AuthorizeHttpRequestsConfigurer.AuthorizedUrl)((AuthorizeHttpRequestsConfigurer.AuthorizedUrl)((HttpSecurity)http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()).authorizeHttpRequests().requestMatchers(new String[]{"/api/student/**"})).permitAll().requestMatchers(new String[]{"/api/teacher/**", "/api/admin/**","/api/classroom/**","/api/electiveclassroom/**","/api/semester/**","/api/course/**","/api/electivesection/**","/api/offeredcourses/**"})).permitAll().anyRequest()).authenticated().and()).addFilterBefore(new JwtTokenValidator(), BasicAuthenticationFilter.class).csrf().disable()).cors().configurationSource(this.corsConfigurationSource()).and()).httpBasic().and()).formLogin(); return (SecurityFilterChain)http.build(); } private CorsConfigurationSource corsConfigurationSource() { return new CorsConfigurationSource() { @Override public CorsConfiguration getCorsConfiguration(HttpServletRequest request) { CorsConfiguration cfg = new CorsConfiguration();
                            cfg.setAllowedOrigins(Arrays.asList("http://localhost:3000")); cfg.setAllowedMethods(Collections.singletonList("*")); cfg.setAllowCredentials(true); cfg.setAllowedHeaders(Collections.singletonList("*")); cfg.setExposedHeaders(Arrays.asList("Authorization")); cfg.setMaxAge(3600L); return cfg; } }; } }
