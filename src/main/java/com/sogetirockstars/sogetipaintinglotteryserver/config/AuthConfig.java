package com.sogetirockstars.sogetipaintinglotteryserver.config;

import com.sogetirockstars.sogetipaintinglotteryserver.model.UserAccount;
import com.sogetirockstars.sogetipaintinglotteryserver.repository.AuthenticationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.cors.CorsUtils;

@Configuration
public class AuthConfig extends WebSecurityConfigurerAdapter {
    private final HttpConfig httpConfig;
    private AuthenticationRepository authRepo;

    @Value("${auth.admin.defaultPassword}")
    private String defaultAdminPass;

    public AuthConfig(HttpConfig httpConfig, AuthenticationRepository authRepo){
        this.httpConfig=httpConfig;
        this.authRepo=authRepo;
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
            .antMatchers(HttpMethod.GET, "/api/*/contestant/").permitAll()
            .antMatchers(HttpMethod.GET, "/api/*/info/**").permitAll()
            .antMatchers("/**").hasRole("ADMIN").anyRequest().authenticated()
        ;
    }

    @Autowired
    public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
        UserAccount admin = authRepo.existsById("admin") ? authRepo.findById("admin").get() : new UserAccount("admin",defaultAdminPass);

        if (admin.getPass().length() < 8 || !admin.getPass().substring(0, 8).equals("{bcrypt}")) {
            System.out.println("hashing password!");
            String encPass = BCrypt.hashpw(admin.getPass(), BCrypt.gensalt());
            admin.setPass(encPass);
        }
        auth.inMemoryAuthentication().withUser("admin").password(admin.getPass()).roles("USER", "ADMIN", "READER", "WRITER");
    }
}
