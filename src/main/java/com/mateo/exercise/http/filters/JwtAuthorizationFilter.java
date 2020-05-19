package com.mateo.exercise.http.filters;

import com.mateo.exercise.services.TokenService;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private TokenService tokenService;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager,
                                  ApplicationContext context) {
        super(authenticationManager);

        tokenService = context.getBean(TokenService.class);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = request.getHeader("Authorization");
        if(token == null) {
            chain.doFilter(request, response);
            return;
        } else {
            token = token.replace("Bearer ", "");
        }

        UsernamePasswordAuthenticationToken authentication = tokenService.validateToken(token);
        if (authentication == null) {
            chain.doFilter(request, response);
            return;
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }
}
