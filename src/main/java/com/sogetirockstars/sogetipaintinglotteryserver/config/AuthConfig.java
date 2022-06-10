package com.sogetirockstars.sogetipaintinglotteryserver.config;

import com.sogetirockstars.sogetipaintinglotteryserver.repository.AuthenticationRepository;
import com.sogetirockstars.sogetipaintinglotteryserver.service.AuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsUtils;

@Configuration
public class AuthConfig extends WebSecurityConfigurerAdapter {
    private final HttpConfig httpConfig;

    @Value("${auth.admin.defaultPassword}")
    private String defaultAdminPass;

    public AuthConfig(HttpConfig httpConfig) {
        this.httpConfig = httpConfig;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .cors().configurationSource(httpConfig.corsConfig()).and()
            .csrf().disable()
            .httpBasic().and()
            .authorizeRequests()
            .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
            .antMatchers(HttpMethod.GET, "/api/*/users/current").permitAll()
            .antMatchers(HttpMethod.GET, "/api/*/item/**").permitAll()
            .antMatchers(HttpMethod.GET, "/api/*/winner/**").permitAll()
            .antMatchers(HttpMethod.GET, "/api/*/lottery/**").permitAll()
            .antMatchers(HttpMethod.GET, "/api/*/contestant/").permitAll()
            .antMatchers(HttpMethod.GET, "/api/*/info/**").permitAll()
            .antMatchers("/**").hasRole("ADMIN").anyRequest().authenticated()
        ;
    }


    @Autowired
    public void globalUserDetails(AuthenticationManagerBuilder auth, AuthenticationRepository authRepo, AuthService authService) throws Exception {
        String password = authService.getCurrentSavedPass();
        if (password==null)
            password=authService.setSavedPassword(defaultAdminPass);

        var configurer = auth.inMemoryAuthentication();
        configurer.withUser("admin").password(password).roles("USER", "ADMIN", "READER", "WRITER");
        authService.setManager(configurer.getUserDetailsService());
    }
}
