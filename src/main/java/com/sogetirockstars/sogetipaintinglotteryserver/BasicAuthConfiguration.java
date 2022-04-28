package com.sogetirockstars.sogetipaintinglotteryserver;

import java.util.Arrays;
import java.util.List;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

@Configuration
@EnableWebSecurity
public class BasicAuthConfiguration extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
            .withUser("user")
            .password("{noop}password")
            .roles("USER");

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .cors()
            .and()
            .authorizeRequests()
                .antMatchers("/", "/home", "/login", "/resource")
                .permitAll()
                .anyRequest()
                .authenticated()
            .and()
                .httpBasic()
            .and()
                .logout()
                .logoutUrl("/logout")
                .invalidateHttpSession(true)
                .addLogoutHandler(new ProperCookieClearingLogoutHandler("JSESSIONID"))
                .logoutSuccessUrl("/afterlogout.html")
                .deleteCookies("JSESSIONID")

            // .and()
            //     .sessionManagement()
            //     .maximumSessions(1)
            //     .expiredUrl("/accessDenied")
            //     .maxSessionsPreventsLogin(true)
                // .sessionRegistry(sessionRegistry);

            ;

        // .deleteCookies("JSESSIONID")
    }

    private final class ProperCookieClearingLogoutHandler implements LogoutHandler {
        private final List<String> cookiesToClear;

        public ProperCookieClearingLogoutHandler(String... cookiesToClear) {
            Assert.notNull(cookiesToClear, "List of cookies cannot be null");
            this.cookiesToClear = Arrays.asList(cookiesToClear);
        }

        public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
            // request.getSession().invalidate();
            System.out.println("Performing logout");

            for (String cookieName : cookiesToClear) {
                String cookiePath = request.getContextPath() + "/";
                if (!StringUtils.hasLength(cookiePath)) {
                    cookiePath = "/";
                }
                Cookie cookie = new Cookie(cookieName, null);
                cookie.setPath(cookiePath);
                cookie.setMaxAge(0);
                response.addCookie(cookie);
                Cookie cookie2 = new Cookie(cookieName+"2", null);
                cookie2.setPath(cookiePath);
                cookie2.setMaxAge(0);

                cookie2.setValue(cookie.getValue());
                response.addCookie(cookie2);
            }
        }
    }
}
