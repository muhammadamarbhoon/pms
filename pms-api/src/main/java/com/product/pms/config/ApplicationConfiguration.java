package com.product.pms.config;

import com.product.pms.security.NonePasswordEncoder;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class ApplicationConfiguration extends WebSecurityConfigurerAdapter {

    @Value("${spring.security.ignored.urls:/h2-console/**}")
    private String[] ignoring;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable()
        .authorizeRequests()
        .antMatchers("*/api/**").authenticated()
        .and()
        .httpBasic();

    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(ignoring);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new NonePasswordEncoder();
    }

}
