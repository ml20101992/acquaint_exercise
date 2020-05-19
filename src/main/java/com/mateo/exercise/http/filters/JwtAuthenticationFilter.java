package com.mateo.exercise.http.filters;

import com.mateo.exercise.services.TokenService;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    private TokenService tokenService;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager,
                                   ApplicationContext context) {            //prob je sto ovaj filter nema veze sa springom nista, tako da ne radi autowire
                                                                            //zato mu dodavamo kontext te forceamo da loada token service
        this.authenticationManager = authenticationManager;
        this.tokenService = context.getBean(TokenService.class);

        setFilterProcessesUrl("/auth/login");
    }



    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);

        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        User user = ((User) authResult.getPrincipal());

        String token = tokenService.issueToken(user.getUsername());


        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter responseWriter = response.getWriter();
        responseWriter.print("{\"token\": \"" + token + "\"}");
        responseWriter.flush();

    }
}
