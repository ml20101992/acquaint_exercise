package com.mateo.exercise.configuration;

import com.mateo.exercise.http.filters.JwtAuthenticationFilter;
import com.mateo.exercise.http.filters.JwtAuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@Order(1)
@EnableWebSecurity
public class AppConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Override
    protected void configure(HttpSecurity security) throws Exception {
        security.httpBasic().disable();

        security.cors()
                .and()
                .csrf()
                .disable()
                .authorizeRequests()
                    .antMatchers(HttpMethod.POST, "/hotels/**")
                        .hasAuthority("SCOPE_MODIFY")
                    .antMatchers(HttpMethod.PUT, "/hotels/**")
                        .hasAuthority("SCOPE_MODIFY")
                    .antMatchers(HttpMethod.DELETE, "/hotels/**")
                        .hasAuthority("SCOPE_MODIFY")
                    .anyRequest()
                        .authenticated()
                .and()
                .addFilter(new JwtAuthenticationFilter(authenticationManager(), getApplicationContext()))
                .addFilter(new JwtAuthorizationFilter(authenticationManager(), getApplicationContext()))
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    }

    @Override
    public void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery(
                        "select username, password_hash, enabled from users where username=?")
                .authoritiesByUsernameQuery(
                        "select username, role from users where username=?"
                );
        /*
                .withUser(User.withUsername("BaseUser")
                        .password(passwordEncoder().encode("password123"))
                        .roles("1"))
                .withUser(User.withUsername("AdvancedUser")
                        .password(passwordEncoder().encode("password321"))
                        .roles("2"));*/
    }

}
