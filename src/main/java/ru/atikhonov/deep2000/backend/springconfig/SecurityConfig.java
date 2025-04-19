package ru.atikhonov.deep2000.backend.springconfig;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.atikhonov.deep2000.backend.security.*;
import ru.atikhonov.deep2000.backend.service.UserService;
import ru.atikhonov.deep2000.backend.security.*;
//import ru.openschool.aop.backend.security.*;

import javax.inject.Inject;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final Logger log = LoggerFactory.getLogger(CorsFilter.class);

    private final UserService userService;

    private static final String PASSWORD_PARAMETER = "password";
    private static final String USERNAME_PARAMETER = "username";

    private static final String LOGIN_URL = "/api/login";

    private final DaoUserDetailsService daoUserDetailsService;

    @Bean
    public AuthenticationFilter authenticationFilter() throws Exception {
        AuthenticationFilter filter = new AuthenticationFilter();

        filter.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher(LOGIN_URL, RequestMethod.POST.name()));
        filter.setPasswordParameter(PASSWORD_PARAMETER);
        filter.setUsernameParameter(USERNAME_PARAMETER);
        filter.setAuthenticationManager(authenticationManagerBean());
        filter.setAuthenticationSuccessHandler(new SecurityAuthenticationSuccessHandler());
        filter.setAuthenticationFailureHandler(new SecurityAuthenticationFailureHandler());

        return filter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .addFilterBefore(new CorsFilter(userService), ChannelProcessingFilter.class)
                .addFilter(authenticationFilter())
                .csrf().disable()
                .authorizeRequests()
                    .antMatchers("/**").permitAll()
                     //env.getRequiredProperty("access.control.allow.origin")
                .and()
                .logout().permitAll().logoutUrl("/api/logout").deleteCookies("JSESSIONID")
                   .logoutSuccessHandler(new SecurityLogoutSuccessHandler())
                   .invalidateHttpSession(true)
                .and()
                .headers().frameOptions().disable();

    }

    @Inject
    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(daoUserDetailsService);
    }
}
