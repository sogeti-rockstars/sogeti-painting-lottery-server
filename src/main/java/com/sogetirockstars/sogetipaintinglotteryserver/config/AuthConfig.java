package com.sogetirockstars.sogetipaintinglotteryserver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsUtils;

@Configuration
public class AuthConfig extends WebSecurityConfigurerAdapter {
    private final HttpConfig httpConfig;
    public AuthConfig(HttpConfig httpConfig){
        this.httpConfig=httpConfig;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .cors().configurationSource(httpConfig.corsConfig()).and()
            .csrf().disable() 
            .httpBasic().and()
            .authorizeRequests()
            .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
            .antMatchers("/user/*").permitAll()
            .antMatchers(HttpMethod.GET, "/api/*/item/**").permitAll()
            .antMatchers(HttpMethod.GET, "/api/*/winner/**").permitAll()
            .antMatchers(HttpMethod.GET, "/api/*/lottery/**").permitAll()
            .antMatchers("/**").hasRole("ADMIN").anyRequest().authenticated()
        ;
    }

    @Autowired
    public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("admin").password("{noop}admin").roles("USER", "ADMIN", "READER", "WRITER");
    }

}
