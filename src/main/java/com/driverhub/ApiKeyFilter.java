
package com.example.Private_CAR_Booking;

import org.springframework.stereotype.Component;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
@Component
public class ApiKeyFilter extends OncePerRequestFilter {

    @Value("${passenger.api.key}")
    private String apiKey;

    /**
     * This method tells Spring to SKIP this filter for specific URLs.
     * We skip it for HTML files so the browser can actually load the page.
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getServletPath();
        
        // Skip filtering for static pages and registration/login
        return path.endsWith(".html") || 
               path.startsWith("/api/passengers/register") || 
               path.startsWith("/api/passengers/login") ||
               path.startsWith("/favicon.ico");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, 
                                    FilterChain filterChain) throws ServletException, IOException {
        
        String requestKey = request.getHeader("X-API-KEY");

        if (apiKey != null && apiKey.equals(requestKey)) {
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                    "ApiKeyUser", null, Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
            
            SecurityContextHolder.getContext().setAuthentication(auth);
            filterChain.doFilter(request, response);
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            
            String jsonResponse = String.format(
                "{\"timestamp\": \"%s\", \"message\": \"Invalid or Missing API Key\", \"details\": \"uri=%s\"}",
                LocalDateTime.now(),
                request.getRequestURI()
            );
            
            response.getWriter().write(jsonResponse);
        }
    }
}