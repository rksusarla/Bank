package com.artilekt.bank.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
            .authorizeRequests()
            	.antMatchers("/clients/**").hasRole("ADMIN")
            	.antMatchers("/accounts/**").hasAnyRole("USER", "ADMIN")
//            	.antMatchers("/echo").authenticated()
                .anyRequest().permitAll()
                .and()
            .httpBasic()
                .and()
                .csrf().disable()
            .httpBasic()
                .and()
                .headers().frameOptions().disable();


    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                .withUser("john").password("smith").roles("USER")
                .and()
                .withUser("admin").password("admin").roles("USER", "ADMIN");
    }
}
